package geekJavaConCodePrastic.lockANDcondition_14;

import java.util.concurrent.locks.Lock;

public class ReentrantLock {
        private final Lock rtl = (Lock) new ReentrantLock();
        int value;
        public int get() {
            // 获取锁
            rtl.lock();         ②
            try {
                return value;
            } finally {
                // 保证锁能释放
                rtl.unlock();
            }
        }
        public void addOne() {
            // 获取锁
            rtl.lock();
            try {
                value = 1 + get(); ①
            } finally {
                // 保证锁能释放
                rtl.unlock();
            }
        }

}
