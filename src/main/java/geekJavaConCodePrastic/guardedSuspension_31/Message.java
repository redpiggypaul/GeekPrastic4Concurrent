package geekJavaConCodePrastic.guardedSuspension_31;

import java.security.GuardedObject;

public class Message {
        String id;
        String content;

    public Message(String s, String s1) {
    }

    public Message(int id, String s1) {
    }

    // 该方法可以发送消息
    void send(Message msg){
        // 省略相关代码
    }

    // 处理浏览器发来的请求
    Respond handleWebReq(){
        int id= SNGenerator.get();
        // 创建一消息
        Message msg1 = new
                Message(id,"");
        // 创建 GuardedObject 实例
        GuardedObject<Message> go=
                GuardedObject.create(id);
        // 发送消息
        send(msg1);
        // 等待 MQ 消息
        Message r = go.get(t->t != null);
    }
    void onMessage(Message msg){
        // 唤醒等待的线程
        GuardedObject.fireEvent(msg.id, msg);
    }





}
