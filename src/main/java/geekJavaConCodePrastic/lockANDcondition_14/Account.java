package geekJavaConCodePrastic.lockANDcondition_14;

import java.util.concurrent.locks.Lock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class Account {
    private int balance;
    private final Lock lock = (Lock) new ReentrantLock();
    // 转账
    void transfer(Account tar, int amt){
        boolean flag = true;
        while (flag) {
            if(this.lock.tryLock(随机数, NANOSECONDS)) {
                try {
                    if (tar.lock.tryLock(随机数, NANOSECONDS)) {
                        try {
                            this.balance -= amt;
                            tar.balance += amt;
                            flag = false;
                        } finally {
                            tar.lock.unlock();
                        }
                    }//if
                } finally {
                    this.lock.unlock();
                }
            }//if
        }//while
    }//transfer
}
