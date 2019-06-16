package geekJavaConCodePrastic.lockANDcondition_14;

public class SampleLock {
        volatile int state;
        // 加锁
        lock() {
            // 省略代码无数 state=1 之前有一段代码会查看状态是否为0，显然不能三七二十一直接设置
            state = 1;
        }
        // 解锁
        unlock() {
            // 省略代码无数 state=0 之前有一段代码会查看状态是否为0，显然不能三七二十一直接设置
            state = 0;
        }

}
