package geekJavaConCodePrastic.lockANDcondition_14;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VisableLockModel {
        private final Lock rtl =
                new ReentrantLock();
        int value;
        public void addOne() {
            // 获取锁
            rtl.lock();
            try {
                value+=1;
            } finally {
                // 保证锁能释放
                rtl.unlock();
            }
        }

}
