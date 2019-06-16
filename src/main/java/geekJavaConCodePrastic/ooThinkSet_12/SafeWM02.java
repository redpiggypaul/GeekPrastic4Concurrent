package geekJavaConCodePrastic.ooThinkSet_12;

import java.util.concurrent.atomic.AtomicLong;

public class SafeWM02 {
        // 库存上限
        private final AtomicLong upper = new AtomicLong(0);
        // 库存下限
        private final AtomicLong lower = new AtomicLong(0);
        // 设置库存上限
        void setUpper(long v){
            // 检查参数合法性
            if (v < lower.get()) {
                throw new IllegalArgumentException();
            }
            upper.set(v);
        }
        // 设置库存下限
        void setLower(long v){
            // 检查参数合法性
            if (v > upper.get()) {
                throw new IllegalArgumentException();
            }
            lower.set(v);
        }
        // 省略其他业务代码


    public class Boundary {
        private final long local_lower;
        private final long local_upper;

        public Boundary(long lower, long upper) {
            if(lower >= upper) {
                // throw exception
            }
            this.local_lower = lower;
            this.local_upper = upper;
        }
    }
}
