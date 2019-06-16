package geekJavaConCodePrastic.dbConnectionPool_HiKariCP_41;

import javax.activation.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class HikariSample1 {
    // 数据库连接池配置
    HikariConfig config = new HikariConfig();
config.setMinimumIdle(1);
config.setMaximumPoolSize(2);
config.setConnectionTestQuery("SELECT 1");
config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
config.addDataSourceProperty("url", "jdbc:h2:mem:test");
    // 创建数据源
    DataSource ds = new HikariDataSource(config);
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    // 用于存储所有的数据库连接
    CopyOnWriteArrayList<T> sharedList;
    // 线程本地存储中的数据库连接
    ThreadLocal<List<Object>> threadList;
    // 等待数据库连接的线程数
    AtomicInteger waiters;
    // 分配数据库连接的工具
    SynchronousQueue<T> handoffQueue;
    // 忙碌队列
    BlockingQueue<Connection> busy;
    // 空闲队列
    BlockingQueue<Connection> idle;


try {
        // 获取数据库连接
        conn = ds.getConnection();
        // 创建 Statement
        stmt = conn.createStatement();
        // 执行 SQL
        rs = stmt.executeQuery("select * from abc");
        // 获取结果
        while (rs.next()) {
            int id = rs.getInt(1);

        }
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        // 关闭 ResultSet
        close(rs);
        // 关闭 Statement
        close(stmt);
        // 关闭 Connection
        close(conn);
    }
    // 关闭资源
    void close(AutoCloseable rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 将空闲连接添加到队列
    void add(final T bagEntry){
        // 加入共享队列
        sharedList.add(bagEntry);
        // 如果有等待连接的线程，
        // 则通过 handoffQueue 直接分配给等待的线程
        while (waiters.get() > 0
                && bagEntry.getState() == STATE_NOT_IN_USE
                && !handoffQueue.offer(bagEntry)) {
            yield();
        }
    }

    T borrow(long timeout, final TimeUnit timeUnit){
        // 先查看线程本地存储是否有空闲连接
        final List<Object> list = threadList.get();
        for (int i = list.size() - 1; i >= 0; i--) {
            final Object entry = list.remove(i);
            final T bagEntry = weakThreadLocals
                    ? ((WeakReference<T>) entry).get()
                    : (T) entry;
            // 线程本地存储中的连接也可以被窃取，
            // 所以需要用 CAS 方法防止重复分配
            if (bagEntry != null
                    && bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)) {
                return bagEntry;
            }
        }

        // 线程本地存储中无空闲连接，则从共享队列中获取
        final int waiting = waiters.incrementAndGet();
        try {
            for (T bagEntry : sharedList) {
                // 如果共享队列中有空闲连接，则返回
                if (bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)) {
                    return bagEntry;
                }
            }
            // 共享队列中没有连接，则需要等待
            timeout = timeUnit.toNanos(timeout);
            do {
                final long start = currentTime();
                final T bagEntry = handoffQueue.poll(timeout, NANOSECONDS);
                if (bagEntry == null
                        || bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)) {
                    return bagEntry;
                }
                // 重新计算等待时间
                timeout -= elapsedNanos(start);
            } while (timeout > 10_000);
            // 超时没有获取到连接，返回 null
            return null;
        } finally {
            waiters.decrementAndGet();
        }
    }

    // 释放连接
    void requite(final T bagEntry){
        // 更新连接状态
        bagEntry.setState(STATE_NOT_IN_USE);
        // 如果有等待的线程，则直接分配给线程，无需进入任何队列
        for (int i = 0; waiters.get() > 0; i++) {
            if (bagEntry.getState() != STATE_NOT_IN_USE
                    || handoffQueue.offer(bagEntry)) {
                return;
            } else if ((i & 0xff) == 0xff) {
                parkNanos(MICROSECONDS.toNanos(10));
            } else {
                yield();
            }
        }
        // 如果没有等待的线程，则进入线程本地存储
        final List<Object> threadLocalList = threadList.get();
        if (threadLocalList.size() < 50) {
            threadLocalList.add(weakThreadLocals
                    ? new WeakReference<>(bagEntry)
                    : bagEntry);
        }
    }




}
