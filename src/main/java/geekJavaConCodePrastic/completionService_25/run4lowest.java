package geekJavaConCodePrastic.completionService_25;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class run4lowest {
    public Integer run(){
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 创建 CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
        AtomicReference<Integer> m = new AtomicReference<>(Integer.MAX_VALUE);
        // 异步向电商 S1 询价
        cs.submit(()->getPriceByS1());
        // 异步向电商 S2 询价
        cs.submit(()->getPriceByS2());
        // 异步向电商 S3 询价
        cs.submit(()->getPriceByS3());
        // 将询价结果异步保存到数据库
        // 并计算最低报价
        for (int i=0; i<3; i++) {
            Integer r = logIfError(()->cs.take().get());
            executor.execute(()-> save(r));
            m.getAndUpdate(v->Integer.min(v, r));
        }
        return m.get();
    }
}
