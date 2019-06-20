package geekJavaConCodePrastic.twoStepStop_35;

public class ProxyQA01 {
    private boolean started = false;
    private boolean terminated = false;
    // 变量声明，（留言超字数，此处未做修改，省略）

    public static void main(String[] args) {
        ProxyQA01 proxy=new ProxyQA01();
        for (int i=0; i<100; i++) {
            new Thread(() -> {
                proxy.start();
                proxy.stop();
            }, "外部线程_"+i)
                    .start();
        }
    }

    private void stop() {
    }

    // 启动采集功能
    synchronized void start() {
        // 不允许同时启动多个采集线程
        String outerName=Thread.currentThread().getName();
        System.out.println("["+outerName+"]线程是否启动？"+started);

        if (started) {
            System.out.println("["+outerName+"]线程 return");
            return;
        }
        started=true;
        terminated=false;

        Thread rptThread=new Thread(() -> {
            while (!terminated) {
                // 每隔两秒钟采集、回传一次数据（留言超字数，此处未做修改，省略）
            }
            // 执行到此处说明线程马上终止
            started=false;
            System.out.println("["+outerName+",内部线程："+Thread.currentThread().getName()+"] started=false 成功执行");
        });

        rptThread.start();
        System.out.println("["+outerName+"]线程执行完毕，内部子线程正在执行中...");
    }

// 终止采集功能（留言超字数，此处未做修改，省略）

}


/*

```java
// 因为留言超字数：1. 省略未修改的代码片段，2. println 是 System.out.println 的简写
class Proxy {
// 变量声明，（留言超字数，此处未做修改，省略）

public static void main(String[] args) {
  Proxy proxy=new Proxy();
  for (int i=0; i<100; i++) {
    new Thread(() -> {
    proxy.start();
    proxy.stop();
    }, "外部线程_"+i)
    .start();
  }
}

// 启动采集功能
synchronized void start() {
  // 不允许同时启动多个采集线程
  String outerName=Thread.currentThread().getName();
  println("["+outerName+"]线程是否启动？"+started);

  if (started) {
    println("["+outerName+"]线程 return");
    return;
  }
  started=true;
  terminated=false;

  rptThread=new Thread(() -> {
    while (!terminated) {
      // 每隔两秒钟采集、回传一次数据（留言超字数，此处未做修改，省略）
    }
    // 执行到此处说明线程马上终止
    started=false;
    println("["+outerName+",内部线程："+Thread.currentThread().getName()+"] started=false 成功执行");
  });

  rptThread.start();
  println("["+outerName+"]线程执行完毕，内部子线程正在执行中...");
}

// 终止采集功能（留言超字数，此处未做修改，省略）
}
```

```
执行结果：
[外部线程_77]线程是否启动？false
[外部线程_77]线程执行完毕，内部子线程正在执行中...
[外部线程_82]线程是否启动？true
[外部线程_82]线程 return
[外部线程_81]线程是否启动？false
[外部线程_77,内部线程：Thread-72] started=false 成功执行
[外部线程_81]线程执行完毕，内部子线程正在执行中...
[外部线程_81,内部线程：Thread-73] started=false 成功执行
[外部线程_84]线程是否启动？false
[外部线程_84]线程执行完毕，内部子线程正在执行中...
[外部线程_80]线程是否启动？true
[外部线程_84,内部线程：Thread-74] started=false 成功执行
[外部线程_80]线程执行完毕，内部子线程正在执行中...
[外部线程_79]线程是否启动？true
[外部线程_80,内部线程：Thread-75] started=false 成功执行
```

解释说明：
1. “[外部线程_81]线程是否启动？false” 先于 “[外部线程_77,内部线程：Thread-72] started=false 成功执行”：
[外部线程_77,内部线程：Thread-72] 执行完 started=false，还没执行 System.out 输出语句，[外部线程_81] 就已经拿到 started=false 的结果了。

2. “[外部线程_80]线程是否启动？true” 然后又 “[外部线程_80]线程执行完毕，内部子线程正在执行中...”：
这时[外部线程_80]让出了 cpu，等到时间片后再次执行时并没有 return，而是成功执行了内部子线程。

结论：started 在线程之间可以保证可见性的，但是具体原因，自己也没想明白。

-----

自己套用了下面的 Happens-Before 规则：
0. Happens-Before 的传递性。
1. 管程中锁的规则。
2. 线程启动规则。
3. 线程终止规则。
4. 线程中断规则。
好像也无法推导出：为何在内部线程 rptThread 修改的 started 变量，可以保证可见性。
是根据什么规则，保证了 started 变量的可见性，老师可以帮忙分析一下么？期待您的回复，谢谢老师！！

 */