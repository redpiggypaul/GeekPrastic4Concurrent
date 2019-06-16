package geekJavaConCodePrastic.highPerformQueue_40;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

public class SimpleDisruptor {
    // 指定 RingBuffer 大小,
// 必须是 2 的 N 次方
    int bufferSize = 1024;
    // 构建 Disruptor
    Disruptor<LongEvent> disruptor
            = new Disruptor<>(
            LongEvent::new,
            bufferSize,
            DaemonThreadFactory.INSTANCE);
    // 获取 RingBuffer
    RingBuffer<LongEvent> ringBuffer
            = disruptor.getRingBuffer();

// 注册事件处理器
disruptor.handleEventsWith(
        (event,sequence,endOfBatch)->
            System.out.println("E: "+event));

// 启动 Disruptor
disruptor.start();
    // 生产 Event
    ByteBuffer bb = ByteBuffer.allocate(8);
    long l = 0;
for(

    {
        bb.putLong(0, l);
        // 生产者生产消息
        ringBuffer.publishEvent(
                (event, sequence, buffer) ->
                        event.set(buffer.getLong(0)), bb);
        Thread.sleep(1000);
    } true;l++)

    // 自定义 Event
    class LongEvent {
        private long value;

        public void set(long value) {
            this.value = value;
        }
    }

}
