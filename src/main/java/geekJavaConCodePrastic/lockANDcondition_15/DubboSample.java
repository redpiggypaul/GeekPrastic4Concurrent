package geekJavaConCodePrastic.lockANDcondition_15;

import javax.xml.ws.Response;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DubboSample {
    // 创建锁与条件变量
    private final Lock lock
            = new ReentrantLock();
    private final Condition done
            = lock.newCondition();

    // 调用方通过该方法等待结果
    Object get(int timeout) throws TimeoutException {
        long start = System.nanoTime();
        lock.lock();
        try {
            while (!isDone()) {
                done.await(timeout);
                long cur=System.nanoTime();
                if (isDone() ||
                        cur-start > timeout){
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
        if (!isDone()) {
            throw new TimeoutException();
        }
        return returnFromResponse();
    }
    // RPC 结果是否已经返回
    boolean isDone() {
        return response != null;
    }
    // RPC 结果返回时调用该方法
    private void doReceived(Response res) {
        lock.lock();
        try {
            response = res;
            if (done != null) {  // changed in new version
                done.signal();
            }
        } finally {
            lock.unlock();
        }
    }

}
