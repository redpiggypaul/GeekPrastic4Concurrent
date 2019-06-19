package geekJavaConCodePrastic.balkingSample_32;

public class SingletonSample {

    private static volatile
    SingletonSample singleton;

    // 构造方法私有化
    private SingletonSample() {
    }

    // 获取实例（单例）
    public static SingletonSample
    getInstance() {
        // 第一次检查
        if (singleton == null) {
            synchronized{
                SingletonSample.class {
                    // 获取锁后二次检查
                    if (singleton == null) {
                        singleton = new SingletonSample();
                    }
                }
            }
            return singleton;
        }
    }

}
