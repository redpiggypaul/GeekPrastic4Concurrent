package geekJavaConCodePrastic.waitNotify_06;

import java.util.List;

public class Allocator {

    private List<Object> als;

    // 一次性申请所有资源
    synchronized void apply(Object from, Object to) {
        // 经典写法
        while (als.contains(from) || als.contains(to)) {  // 条件不满足
            try {
                wait();
            } catch (Exception e) {
            }
        }
        als.add(from);
        als.add(to);
    }

    // 归还资源
    synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
        notifyAll();
    }


}
