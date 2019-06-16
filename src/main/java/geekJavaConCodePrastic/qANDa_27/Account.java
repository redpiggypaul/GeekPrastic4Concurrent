package geekJavaConCodePrastic.qANDa_27;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

        private int balance;
        private final Lock lock
                = new ReentrantLock();
        // 转账
        void transfer(Account tar, int amt){
            while (true) {
                if(this.lock.tryLock()) {
                    try {
                        if (tar.lock.tryLock()) {
                            try {
                                this.balance -= amt;
                                tar.balance += amt;
                                // 新增：退出循环
                                break;
                            } finally {
                                tar.lock.unlock();
                            }
                        }//if
                    } finally {
                        this.lock.unlock();
                    }
                }//if
                // 新增：sleep 一个随机时间避免活锁
                Thread.sleep(2345);
            }//while
        }//transfer

}
