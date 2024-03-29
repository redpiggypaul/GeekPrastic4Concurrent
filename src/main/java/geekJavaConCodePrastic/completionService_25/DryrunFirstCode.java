package geekJavaConCodePrastic.completionService_25;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DryrunFirstCode {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<Integer> f1 = executor.submit(()->getPriceByS1());
        Future<Integer> f2 = executor.submit(()->getPriceByS2());
        Future<Integer> f3 = executor.submit(()->getPriceByS3());

        executor.execute(()-> {
            try {
                save(f1.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executor.execute(()-> {
            try {
                save(f2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executor.execute(()-> {
            try {
                save(f3.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static Integer getPriceByS1() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
    private static Integer getPriceByS2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2;
    }
    private static Integer getPriceByS3() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 3;
    }
    private static void save(Integer i) {
        System.out.println("save " + i);
    }

}
