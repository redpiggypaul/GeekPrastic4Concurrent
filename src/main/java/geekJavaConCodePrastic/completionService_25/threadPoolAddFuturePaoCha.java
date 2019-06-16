package geekJavaConCodePrastic.completionService_25;

import java.util.concurrent.*;

public class threadPoolAddFuturePaoCha {
    // 创建线程池
    ExecutorService executor =
            Executors.newFixedThreadPool(3);
    // 异步向电商 S1 询价
    Future<Integer> f1 =
            executor.submit(
                    ()->getPriceByS1());
    // 异步向电商 S2 询价
    Future<Integer> f2 =
            executor.submit(
                    ()->getPriceByS2());
    // 异步向电商 S3 询价
    Future<Integer> f3 =
            executor.submit(
                    ()->getPriceByS3());
// method 1
// 获取电商 S1 报价并保存
    r=f1.get();
executor.execute(()->save(r));

// 获取电商 S2 报价并保存
    r=f2.get();
executor.execute(()->save(r));

// 获取电商 S3 报价并保存
    r=f3.get();
executor.execute(()->save(r));


// method 2
    // 创建阻塞队列
    BlockingQueue<Integer> bq =
            new LinkedBlockingQueue<>();
// 电商 S1 报价异步进入阻塞队列
executor.execute(()->
        bq.put(f1.get()));
// 电商 S2 报价异步进入阻塞队列
executor.execute(()->
        bq.put(f2.get()));
// 电商 S3 报价异步进入阻塞队列
executor.execute(()->
        bq.put(f3.get()));
// 异步保存所有报价
for (int i=0; i<3; i++) {
        Integer r = bq.take();
        executor.execute(()->save(r));
    }

}
