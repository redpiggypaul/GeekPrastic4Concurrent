package geekJavaConCodePrastic.qANDa_27;

import java.util.concurrent.atomic.AtomicReference;

public class SafeWM {
        class WMRange{
            final int upper = 0;
            final int lower = 0;
            WMRange(int upper,int lower){
                // 省略构造函数实现
            }
        }
        final AtomicReference<WMRange>
                rf = new AtomicReference<>(
                new WMRange(0,0)
        );
        // 设置库存上限
        void setUpper(int v){
            WMRange nr;
            WMRange or;
            // 原代码在这里
            //WMRange or=rf.get();
            do{
                // 移动到此处
                // 每个回合都需要重新获取旧值
                or = rf.get();
                // 检查参数合法性
                if(v < or.lower){
                    throw new IllegalArgumentException();
                }
                nr = new
                        WMRange(v, or.lower);
            }while(!rf.compareAndSet(or, nr));
        }

}
