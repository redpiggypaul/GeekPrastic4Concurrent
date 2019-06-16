package geekJavaConCodePrastic.one_Lock_N_object_04;

public class Account {
    // 锁：保护账户余额
    private final Object balLock
            = new Object();
    // 锁：保护账户密码
    private final Object pwLock
            = new Object();
    // 账户余额
    private Integer balance;
    // 账户密码
    private String password;

    // 账户ID
    private Integer id;

    // 取款
    void withdraw(Integer amt) {
        synchronized (balLock) {
            if (this.balance > amt) {
                this.balance -= amt;
            }
        }
    }

    // 查看余额
    Integer getBalance() {
        synchronized (balLock) {
            return balance;
        }
    }

    // 更改密码
    void updatePassword(String pw) {
        synchronized (pwLock) {
            this.password = pw;
        }
    }

    // 查看密码
    String getPassword() {
        synchronized (pwLock) {
            return password;
        }
    }

    // 转账
    void transfer(Account target, int amt) {
        synchronized (Account.class) {  //使用共享的锁 Account.class 来保护不同对象的临界区
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }

   /*
        // 转账
        void transfer(Account target, int amt){
            // 锁定转出账户
            synchronized(this) {
                // 锁定转入账户
                synchronized(target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        }
    */



        // 转账 破坏循环等待条件

        void transferWithID(Account target, int amt){
            Account left = this;       // ①
            Account right = target;   // ②
            if (this.id > target.id) {// ③
                left = target;         //  ④
                right = this;         //   ⑤
            }                       //   ⑥
            // 锁定序号小的账户
            synchronized(left){
                // 锁定序号大的账户
                synchronized(right){
                    if (this.balance > amt){
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        }


    private static Object lock = new Object();
   //Account中添加一个静态object，通过锁这个object来实现一个锁保护多个资源。这种方式比锁class更安全，因为这个缺是私有的。有些最佳实践要求必须这样做
    // 转账
    void transferSafer(Account target, int amt){
        synchronized(lock) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }
}

