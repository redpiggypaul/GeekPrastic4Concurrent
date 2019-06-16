package geekJavaConCodePrastic.completionService_25;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class afterClassPrasticWithLowestPrise {
    // 创建线程池
    ExecutorService executor =
            Executors.newFixedThreadPool(3);
    // 创建 CompletionService
    CompletionService<Integer> cs = new
            ExecutorCompletionService<>(executor);
// 异步向电商 S1 询价
cs.submit(()->getPriceByS1());
// 异步向电商 S2 询价
cs.submit(()->getPriceByS2());
// 异步向电商 S3 询价
cs.submit(()->getPriceByS3());
    // 将询价结果异步保存到数据库
// 并计算最低报价
    AtomicReference<Integer> m =
            new AtomicReference<>(Integer.MAX_VALUE);
for (int i=0; i<3; i++) {
        executor.execute(()->{
            Integer r = null;
            try {
                r = cs.take().get();
            } catch (Exception e) {}
            save(r);
            m.set(Integer.min(m.get(), r));
        });
    }
return m;

}
