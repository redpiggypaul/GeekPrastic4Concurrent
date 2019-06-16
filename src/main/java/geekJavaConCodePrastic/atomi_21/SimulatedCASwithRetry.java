package geekJavaConCodePrastic.atomi_21;

public class SimulatedCASwithRetry {

    volatile int count;

    // 实现 count+=1
    public int addOne() {
        //  int result = 0;
        int newValue = 0;
        try {
            do {
                newValue = count + 1; //①
            } while (count != cas(count, newValue)); //②
        } catch (Exception e) {
        } finally {

            return newValue;
        }
    }

    // 模拟实现 CAS，仅用来帮助理解
    synchronized int cas(
            int expect, int newValue) {
        // 读目前 count 的值
        int curValue = count;
        // 比较目前 count 值是否 == 期望值
        if (curValue == expect) {
            // 如果是，则更新 count 的值
            count = newValue;
        }
        // 返回写入前的值
        return curValue;
    }

}
