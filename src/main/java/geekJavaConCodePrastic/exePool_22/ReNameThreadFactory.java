package geekJavaConCodePrastic.exePool_22;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ReNameThreadFactory implements ThreadFactory {
    /**
     * 线程池编号（static修饰）(容器里面所有线程池的数量)
     */
    private static final AtomicInteger POOLNUMBER = new AtomicInteger(1);

    /**
     * 线程编号(当前线程池线程的数量)
     */
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    /**
     * 线程组
     */
    private final ThreadGroup group;

    /**
     * 业务名称前缀
     */
    private final String namePrefix;

    BlockingQueue<Runnable> workQueue;
    // 保存内部工作线程
    List<WorkerThreadLocal> threads = new ArrayList<>();



    // 工作线程负责消费任务，并执行任务
    class WorkerThreadLocal extends Thread {
        public void run() {
            // 循环取任务并执行
            while (true) {  // ①
                Runnable task = null;
                try {
                    task = workQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.run();
            }
        }
    }

    /**
     * 重写线程名称（获取线程池编号，线程编号，线程组）
     *
     * @param prefix 你需要指定的业务名称
     */
    public ReNameThreadFactory(@NonNull String prefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        //组装线程前缀
        namePrefix = prefix + "-poolNumber:" + POOLNUMBER.getAndIncrement() + "-threadNumber:";
    }


    // 构造方法
   public ReNameThreadFactory(int poolSize, BlockingQueue<Runnable> workQueue, @NonNull String prefix) {
       SecurityManager s = System.getSecurityManager();
       group = (s != null) ? s.getThreadGroup() :
               Thread.currentThread().getThreadGroup();
       //组装线程前缀
       namePrefix = prefix + "-poolNumber:" + POOLNUMBER.getAndIncrement() + "-threadNumber:";

        this.workQueue = workQueue;
        // 创建工作线程
        for (int idx = 0; idx < poolSize; idx++) {
            WorkerThreadLocal work = new WorkerThreadLocal();
            work.start();
            threads.add(work);
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                //方便dump的时候排查（重写线程名称）
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

    // 提交任务
    void execute(Runnable command) throws InterruptedException {
        workQueue.put(command);
    }

    public static void main(String[] args) throws InterruptedException {
        /** 下面是使用示例 **/
// 创建有界阻塞队列
        BlockingQueue<Runnable> workQueue =
                new LinkedBlockingQueue<>(2);
        // 创建线程池
        ReNameThreadFactory pool = new ReNameThreadFactory(10, workQueue, "thisname");

        // 提交任务
        pool.execute(() -> {
            System.out.println("hello_1");
            System.out.println("hello_0");
            System.out.println("hello_2");
            System.out.println("hello_3");
            System.out.println("hello_4");
            System.out.println("hello_5");
            System.out.println("hello_7");
            System.out.println("hello_8");
            System.out.println("hello_9");
        });

    }
}
