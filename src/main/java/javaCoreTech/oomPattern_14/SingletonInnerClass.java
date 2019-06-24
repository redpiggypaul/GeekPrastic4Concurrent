package javaCoreTech.oomPattern_14;

public class SingletonInnerClass {
    private SingletonInnerClass() {
    }

    public static SingletonInnerClass getSingleton() {
        return Holder.singleton;
    }

    private static class Holder {
        private static SingletonInnerClass singleton = new SingletonInnerClass();
    }

}
