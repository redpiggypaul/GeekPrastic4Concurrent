package geekJavaConCodePrastic.future_23;

import com.google.common.collect.Lists;

import java.util.concurrent.*;

import static com.oracle.tools.packager.RelativeFileSet.Type.data;

public class ExecutorExample {
    private static final ExecutorService executor;
    static {executor = new ThreadPoolExecutor(4, 8, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000), runnable -> null, (r, executor) -> {//根据业务降级策略});
    }
        static class S1Task implements Callable<String> {
            @Override
            public String call() throws Exception {return getPriceByS1();}}
        static class S2Task implements Callable<String> {
            @Overridepublic String call() throws Exception {return getPriceByS2();}}
        static class S3Task implements Callable<String> {@Override public String call() throws Exception {return getPriceByS3();}}
        static class SaveTask implements Callable<Boolean> {private List<FutureTask<String>> futureTasks; public SaveTask(List<FutureTask<String>> futureTasks) {this.futureTasks = futureTasks;
        }
            @Override
            public Boolean call() throws Exception {
                for (FutureTask<String> futureTask : futureTasks) {
                    String data = futureTask.get(10, TimeUnit.SECONDS);
                    saveData(data);
                }
                return Boolean.TRUE;
            }
        }
        private static String getPriceByS1() {
            return "fromDb1";
        }
        private static String getPriceByS2() {
            return "fromDb2";
        }
        private static String getPriceByS3() {
            return "fromDb3";
        }
        private static void saveData(String data) {
            //save data to db
        }
        public static void main(String[] args) {
            S1Task s1Task = new S1Task();FutureTask<String> st1 = new FutureTask<>(s1Task);S2Task s2Task = new S2Task();FutureTask<String> st2 = new FutureTask<>(s2Task);S3Task s3Task = new S3Task();FutureTask<String> st3 = new FutureTask<>(s3Task);List<FutureTask<String>> futureTasks = Lists.<FutureTask<String>>newArrayList(st1, st2, st3);FutureTask<Boolean> saveTask = new FutureTask<>(new SaveTask(futureTasks));executor.submit(st1);executor.submit(st2);executor.submit(st3);executor.submit(saveTask);}}
