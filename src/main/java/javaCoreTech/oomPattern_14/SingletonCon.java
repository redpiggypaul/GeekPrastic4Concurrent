package javaCoreTech.oomPattern_14;

public class SingletonCon {

    private static volatile SingletonCon singleton = null;

    private SingletonCon() {
    }

    public static SingletonCon getSingleton() {
        if (singleton == null) { // 尽量避免重复进入同步块
            synchronized (SingletonCon.class) { // 同步.class，意味着对同步类方法调用
                if (singleton == null) {
                    singleton = new SingletonCon();
                }
            }
        }
        return singleton;
    }
}


