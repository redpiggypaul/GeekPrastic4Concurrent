package geekJavaConCodePrastic.atomi_21;

public class SimulatedCAS {

        int count;
        synchronized int cas(
                int expect, int newValue){
            // 读目前 count 的值
            int curValue = count;
            // 比较目前 count 值是否 == 期望值
            if(curValue == expect){
                // 如果是，则更新 count 的值
                count = newValue;
            }
            // 返回写入前的值
            return curValue;
        }

}
