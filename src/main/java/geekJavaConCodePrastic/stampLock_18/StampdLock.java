package geekJavaConCodePrastic.stampLock_18;

import java.io.IOException;
import java.util.concurrent.locks.StampedLock;

public class StampdLock {
    final StampedLock sl = new StampedLock();

    // 获取 / 释放悲观读锁示意代码
    long stamp = sl.readLock();
    try{
        // 省略业务相关代码a
        System.out.println();
    } catch(Exception e){
        e.printStackTrace();
    }
    finally {
        sl.unlockRead(stamp);
    }

    // 获取 / 释放写锁示意代码
    long stamp = sl.writeLock();
    try {
        // 省略业务相关代码
    } catch (Exception e){}
    finally {
        sl.unlockWrite(stamp);
    }


}
