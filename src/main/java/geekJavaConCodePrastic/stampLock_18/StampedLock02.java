package geekJavaConCodePrastic.stampLock_18;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

public class StampedLock02 {
    final StampedLock lock
            = new StampedLock();
    final StampedLock sl =
            new StampedLock();

    T1.start();
    // 保证 T1 获取写锁
    Thread.sleep(100);
    Thread T1 = new Thread(() -> {
        // 获取写锁
        lock.writeLock();
        // 永远阻塞在此处，不释放写锁
        LockSupport.park();
    });
    T2.start();
    // 保证 T2 阻塞在读锁
    Thread.sleep(100);
    // 中断线程 T2
    // 会导致线程 T2 所在 CPU 飙升
    T2.interrupt();
    T2.join();


//    StampedLock 读模板：
    Thread T2 = new Thread(() ->
            // 阻塞在悲观读锁
            lock.readLock()
    );
    // 乐观读
    long stamp =
            sl.tryOptimisticRead();
// 读入方法局部变量
......
// 校验 stamp
        if(!sl.validate(stamp))
    long stamp = sl.writeLock();
// 使用方法局部变量执行业务操作
......


    //   StampedLock 写模板：

    {
        // 升级为悲观读锁
        stamp = sl.readLock();
        try {
            // 读入方法局部变量
    .....
        } finally {
            // 释放悲观读锁
            sl.unlockRead(stamp);
        }
    }
try

    {
        // 写共享变量
  ......
    } finally

    {
        sl.unlockWrite(stamp);
    }


}
