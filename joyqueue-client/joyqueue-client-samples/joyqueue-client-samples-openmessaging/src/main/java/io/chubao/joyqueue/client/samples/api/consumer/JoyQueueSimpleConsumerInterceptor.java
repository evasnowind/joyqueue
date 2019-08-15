package io.chubao.joyqueue.client.samples.api.consumer;

import io.chubao.joyqueue.client.internal.common.ordered.Ordered;
import io.chubao.joyqueue.client.internal.consumer.domain.ConsumeMessage;
import io.chubao.joyqueue.client.internal.consumer.domain.ConsumeReply;
import io.chubao.joyqueue.client.internal.consumer.interceptor.ConsumeContext;
import io.chubao.joyqueue.client.internal.consumer.interceptor.ConsumerInterceptor;

import java.util.List;

/**
 * JoyQueueSimpleConsumerInterceptor
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2019/4/8
 */
// Ordered接口提供getOrder方法，用于指定顺序，可以不实现
// context还有attributes等可使用，具体看io.chubao.joyqueue.client.internal.consumer.interceptor.ConsumeContext
public class JoyQueueSimpleConsumerInterceptor implements ConsumerInterceptor, Ordered {

    @Override
    public boolean preConsume(ConsumeContext context) {
        System.out.println("preConsume");

        // 循环一批消息，单条和批消息都是按批拦截
        for (ConsumeMessage message : context.getMessages()) {
            // 过滤消息
            context.filterMessage(message);
        }

        // 返回true表示这批消息可以消费，返回false表示这批消息不可消费
        return true;
    }

    @Override
    public void postConsume(ConsumeContext context, List<ConsumeReply> consumeReplies) {
        System.out.println("postConsume");
    }

    @Override
    public int getOrder() {
        // 值小的先执行
        return Ordered.LOWEST_PRECEDENCE;
    }
}