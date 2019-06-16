package geekJavaConCodePrastic.workTogether_19;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class countDown {
    // 创建 2 个线程的线程池
    Executor executor =
            Executors.newFixedThreadPool(2);
while(存在未对账订单){
        // 计数器初始化为 2
        CountDownLatch latch =
                new CountDownLatch(2);
        // 查询未对账订单
        executor.execute(()-> {
            pos = getPOrders();
            latch.countDown();
        });
        // 查询派送单
        executor.execute(()-> {
            dos = getDOrders();
            latch.countDown();
        });

        // 等待两个查询操作结束
        latch.await();

        // 执行对账操作
        diff = check(pos, dos);
        // 差异写入差异库
        save(diff);
    }

}
