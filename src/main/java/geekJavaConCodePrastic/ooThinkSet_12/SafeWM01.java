package geekJavaConCodePrastic.ooThinkSet_12;

import java.util.concurrent.atomic.AtomicLong;

public class SafeWM01 {
        // 库存上限
        private final AtomicLong upper =
                new AtomicLong(0);
        // 库存下限
        private final AtomicLong lower =
                new AtomicLong(0);
        // 设置库存上限
        void setUpper(long v){
            upper.set(v);
        }
        // 设置库存下限
        void setLower(long v){
            lower.set(v);
        }
        // 省略其他业务代码

}
