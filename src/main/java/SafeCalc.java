public class SafeCalc {
    long value = 0L;
    long get() {
        return value;
    }
    synchronized void addOne() {
        value += 1;
    }
}
