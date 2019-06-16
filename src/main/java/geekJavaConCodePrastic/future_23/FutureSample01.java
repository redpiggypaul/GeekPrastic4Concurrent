package geekJavaConCodePrastic.future_23;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureSample01 {

  /*  JAVA use these 3 I/F and FutureTask class to sumbit task and get result
  // 提交 Runnable 任务
    Future<?>
    submit(Runnable task);
    // 提交 Callable 任务
    <T> Future<T>
    submit(Callable<T> task);
    // 提交 Runnable 任务及结果引用
    <T> Future<T>
    submit(Runnable task, T result);
*/


  /*    the 5 I/F method of Future
    // 取消任务
    boolean cancel(
            boolean mayInterruptIfRunning);
    // 判断任务是否已取消
    boolean isCancelled();
    // 判断任务是否已结束
    boolean isDone();
    // 获得任务执行结果
    get();
    // 获得任务执行结果，支持超时
    get(long timeout, TimeUnit unit);
    */

    ExecutorService executor
            = Executors.newFixedThreadPool(1);
    // 创建 Result 对象 r
    Result r = new Result();
    r.setAAA(a);
    // 提交任务
    Future<Result> future = executor.submit(new Task(r), r);
    Result fr = future.get();
    // 下面等式成立
    fr ===r;
    fr.getAAA()===a;
    fr.getXXX()===x;

    public static void main(String[] args) throws InterruptedException {
        // 创建 FutureTask
        FutureTask<Integer> futureTask
                = new FutureTask<>(() -> 1 + 2);
        // 创建线程池
        ExecutorService es =
                Executors.newCachedThreadPool();
// 提交 FutureTask
        es.submit(futureTask);
        // 获取计算结果
        Integer result = futureTask.get();


        // 创建 FutureTask
        FutureTask<Integer> futureTask2
                = new FutureTask<>(() -> 1 + 2);
// 创建并启动线程
        Thread T1 = new Thread(futureTask2);
        T1.start();
// 获取计算结果
        Integer result2 = futureTask2.get();


    }

    class Task implements Runnable {
        Result r;

        // 通过构造函数传入 result
        Task(Result r) {
            this.r = r;
        }

        void run() {
            // 可以操作 result
            a = r.getAAA();
            r.setXXX(x);
        }
    }


}
