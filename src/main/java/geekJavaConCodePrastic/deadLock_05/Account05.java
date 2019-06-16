package geekJavaConCodePrastic.deadLock_05;

public class Account05 {
    // actr 应该为单例
    private Allocator actr;
    private int balance;

    // 转账
    void transfer(Account05 target, int amt) {
        // 一次性申请转出账户和转入账户，直到成功
        while (!actr.apply(this, target)) ;
        try {
            // 锁定转出账户
            synchronized (this) {
                // 锁定转入账户
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            actr.free(this, target);
        }
    }
}
