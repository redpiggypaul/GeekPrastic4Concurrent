package geekJavaConCodePrastic.balkingSample_32;

public class AutoSave2 {

    boolean changed=false;
    // 自动存盘操作
    void autoSave(){
        synchronized(this){
            if (!changed) {
                return;
            }
            changed = false;
        }
        // 执行存盘操作
        // 省略且实现
        this.execSave();
    }


    // 编辑操作
    void edit(){
        // 省略编辑逻辑
  
        change();
    }
    // 改变状态
    void change(){
        synchronized(this){
            changed = true;
        }
    }
    private void execSave() {
    }
}
