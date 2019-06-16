package geekJavaConCodePrastic.future_23;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.tools.internal.xjc.reader.Ring.add;

public class ExecutorExample {

    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        FutureTask<String> st1 = new FutureTask<>(ExecutorExample::getPriceByS1);
        FutureTask<String> st2 = new FutureTask<>(ExecutorExample::getPriceByS2);
        FutureTask<String> st3 = new FutureTask<>(ExecutorExample::getPriceByS3);
        Runnable saveTask = () -> {
            List<FutureTask<String>> list = new ArrayList<FutureTask<String>>() {{
                add(st1);
                add(st2);
                add(st3);
            }};
            while (!list.isEmpty()) {
                Iterator<FutureTask<String>> it = list.iterator();
                while (it.hasNext()) {
                    FutureTask<String> ftask = it.next();
                    if (ftask.isDone()) {
                        try {
                            saveData(ftask.get());
                            it.remove();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        executor.submit(st1);
        executor.submit(st2);
        executor.submit(st3);
        executor.submit(saveTask);

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
        System.out.println("save data " + data);
    }
}
