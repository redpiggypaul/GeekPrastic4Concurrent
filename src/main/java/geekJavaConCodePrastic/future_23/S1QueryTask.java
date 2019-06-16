package geekJavaConCodePrastic.future_23;

import java.util.concurrent.*;

 class S1QueryTask implements Callable<Double> {
    public Double call() throws Exception {
        Thread.sleep(1000);//模拟查询时间
        return Double.valueOf(10f);
    }
}
 class S2QueryTask implements Callable<Double>{
    public Double call() throws Exception {
        Thread.sleep(2000);//模拟查询时间
        return Double.valueOf(20f);
    }
}
 class S3QueryTask implements Callable<Double>{
    public Double call() throws Exception {
        Thread.sleep(3000);//模拟查询时间
        return Double.valueOf(30f);
    }
}
 class SaveTask implements Callable<Double>{
    final FutureTask<Double>[] queryfts;
    private SaveTask(FutureTask<Double>[] queryfts) {
        this.queryfts = queryfts;
    }
    private Double save(Double combRst) throws InterruptedException{
        Thread.sleep(500);//模拟保存时间
        return combRst;
    }
    public Double call() throws Exception {
        Double combRst = new Double(0f);
        for(FutureTask<Double> queryft : queryfts) {
            Double rst = queryft.get();
            if(rst != null) {
                combRst += rst;
            }
        }
        return save(combRst);
    }

    public static void main(String[] args) {
        FutureTask<Double>[] queryfts = new FutureTask[] {new FutureTask<Double>(new S1QueryTask()),new FutureTask<Double>(new S2QueryTask()),new FutureTask<Double>(new S3QueryTask())};
        FutureTask<Double> saveft3 = new FutureTask<Double>(new SaveTask(queryfts));
        ExecutorService executor = Executors.newFixedThreadPool(4);
        long start = System.currentTimeMillis();
        for(FutureTask<Double> queryft : queryfts) {
            executor.submit(queryft);
        }
        executor.submit(saveft3);
        try {
            Double combRst = saveft3.get();
            long end = System.currentTimeMillis();
            if(combRst != null) {
                System.out.println("保存成功,合并结果：" + combRst);
            }else {
                System.out.println("保存失败");
            }
            System.out.println("耗时：" + (end - start) + "ms");
        } catch (InterruptedException e) {
//按需处理
        } catch (ExecutionException e) {
//按需处理
        } catch (TimeoutException e) {
//按需处理
        }finally {
            executor.shutdown();
        }
    }
}
