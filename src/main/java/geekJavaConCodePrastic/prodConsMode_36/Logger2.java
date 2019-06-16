package geekJavaConCodePrastic.prodConsMode_36;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Logger2 {

    // 用于终止日志执行的“毒丸”
    final LogMsg poisonPill =
            new LogMsg(logLevel.ERROR, "");
    // 任务队列
    final ArrayBlockingQueue<LogMsg> localABQ = new ArrayBlockingQueue<LogMsg> (1024);
    // 只需要一个线程写日志
    ExecutorService es =
            Executors.newFixedThreadPool(1);

    // 启动写日志线程
    void start() throws IOException {
        File file = File.createTempFile("foo", ".log");

        final FileWriter writer = new FileWriter(file);

        this.es.execute(() -> {
            try {
                while (true) {
                    LogMsg log = localABQ.poll(
                            5, TimeUnit.SECONDS);
                    // 如果是“毒丸”，终止执行
                    if (poisonPill.equals(log)) {
                        break;
                    }
                    // 省略执行逻辑
                }
            } catch (Exception e) {
            } finally {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                }
            }
        });
    }

    // 终止写日志线程
    public void stop() {
        // 将“毒丸”对象加入阻塞队列
        localABQ.add(poisonPill);
        es.shutdown();
    }

}
