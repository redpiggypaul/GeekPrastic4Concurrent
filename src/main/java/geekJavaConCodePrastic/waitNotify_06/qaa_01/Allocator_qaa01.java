package geekJavaConCodePrastic.waitNotify_06.qaa_01;

import geekJavaConCodePrastic.one_Lock_N_object_04.Account;

import java.util.LinkedList;
import java.util.List;

public class Allocator_qaa01 {
    private final List<Account_qaa01> als=new LinkedList<Account_qaa01>();
    // 一次性申请所有资源
    public synchronized void apply(Account_qaa01 from, Account_qaa01 to) {
// 经典写法
        while (als.contains(from) || als.contains(to)) {
            try {
                System.out.println("等待用户 -> "+from.getId()+"_"+to.getId());
                wait();
            } catch (Exception e) {
//notify + notifyAll 不会来这里
                System.out.println("异常用户 -> "+from.getId()+"_"+to.getId());
                e.printStackTrace();
            }
        }
        als.add(from);
        als.add(to);
    }
    // 归还资源
    public synchronized void free(Account_qaa01 from, Account_qaa01 to) {
        System.out.println("唤醒用户 -> "+from.getId()+"_"+to.getId());
        als.remove(from);
        als.remove(to);
        notifyAll();
    }
}
