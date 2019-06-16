package geekJavaConCodePrastic.waitNotify_06.qaa_01;

import java.util.Random;

public class Account_qaa01 {
    // actr 应该为单例
    private final Allocator_qaa01 actr;
    //唯一账号
    private final long id;
    //余额
    private int balance;

    public Account_qaa01(Allocator_qaa01 actr, long id, int balance) {
        this.actr = actr;
        this.id = id;
        this.balance = balance;
    }

    // 转账
    public void transfer(Account_qaa01 target, int amt) {
        // 一次性申请转出账户和转入账户，直到成功
        actr.apply(this, target);
        try {
            //TODO 有了资源管理器，这里的synchronized锁就不需要了吧？！ no, you don't need it any more
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
            //模拟数据库操作时间
            try {
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            actr.free(this, target);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    /**
     * 用于判断两个用户是否一致
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account_qaa01 other = (Account_qaa01) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public long getId() {
        return id;
    }
}
