package geekJavaConCodePrastic.workthread_34;

import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EchoByThreadLimitQueue {

    final ServerSocketChannel ssc = ServerSocketChannel.open().bind(new InetSocketAddress(8080));

    ExecutorService es = new ThreadPoolExecutor(
            50, 500,
            60L, TimeUnit.SECONDS,
            // 注意要创建有界队列
            new LinkedBlockingQueue<Runnable>(2000),
            // 建议根据业务需求实现 ThreadFactory
            r -> {
                return new Thread(r, "echo-" + r.hashCode());
            },
            // 建议根据业务需求实现 RejectedExecutionHandler
            new ThreadPoolExecutor.CallerRunsPolicy());

// 处理请求
try

    {
        while (true) {
            // 接收请求
            SocketChannel sc = ssc.accept();
            // 将请求处理任务提交给线程池
            es.execute(() -> {
                try {
                    // 读 Socket
                    ByteBuffer rb = ByteBuffer
                            .allocateDirect(1024);
                    sc.read(rb);
                    // 模拟处理请求
                    Thread.sleep(2000);
                    // 写 Socket
                    ByteBuffer wb =
                            (ByteBuffer) rb.flip();
                    sc.write(wb);
                    // 关闭 Socket
                    sc.close();
                } catch (Exception e) {
                    throw new UncheckedIOException(e);
                }
            });
        }
    } finally

    {
        ssc.close();
        es.shutdown();
    }
}
