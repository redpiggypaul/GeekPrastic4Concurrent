package geekJavaConCodePrastic.one_Lock_N_object_04;

public class AccountLockWithClass {
    private int balance;

    // 转账
    void transfer(AccountLockWithClass target, int amt) {
        synchronized (AccountLockWithClass.class) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }
}


