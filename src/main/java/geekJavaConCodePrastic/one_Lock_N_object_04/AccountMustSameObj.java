package geekJavaConCodePrastic.one_Lock_N_object_04;

public class AccountMustSameObj {

        private Object lock;
        private int balance;
        private AccountMustSameObj(){};
        // 创建 AccountMustSameObj 时传入同一个 lock 对象
        public AccountMustSameObj(Object lock) {
            this.lock = lock;
        }
        // 转账
        void transfer(AccountMustSameObj target, int amt){
            // 此处检查所有对象共享的锁
            synchronized(lock) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }


}
