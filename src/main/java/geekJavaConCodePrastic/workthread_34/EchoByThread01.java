package geekJavaConCodePrastic.workthread_34;

import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoByThread01 {
    final ServerSocketChannel ssc =
            ServerSocketChannel.open().bind(
                    new InetSocketAddress(8080));
    ExecutorService es = Executors
            .newFixedThreadPool(500);
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
