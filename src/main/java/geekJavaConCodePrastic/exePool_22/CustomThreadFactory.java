package geekJavaConCodePrastic.exePool_22;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread("CUSTOM_NAME_PREFIX");
            return thread;
        }
    

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
            100,
            120,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new CustomThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) throws InterruptedException {

        CustomThreadFactory threadPoolTaskExecutor = new CustomThreadFactory();
        threadPoolTaskExecutor.setThreadNamePrefix("CUSTOM_NAME_PREFIX");
    }

    private void setThreadNamePrefix(String custom_name_prefix) {
    }
}
