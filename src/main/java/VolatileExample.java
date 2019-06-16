public class VolatileExample {
// 以下代码来源于【参考 1】
    int x = 0;
    volatile boolean v = false;
    public void writer() {
        x = 42;
        v = true;
    }
    public void reader() {
        if (v == true) {
            // 这里 x 会是多少呢？
        }
    }
}
