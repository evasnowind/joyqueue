package io.chubao.joyqueue.client.samples.api.producer;

import io.openmessaging.KeyValue;
import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.OMS;
import io.openmessaging.OMSBuiltinKeys;
import io.openmessaging.interceptor.Context;
import io.openmessaging.interceptor.ProducerInterceptor;
import io.openmessaging.message.Message;
import io.openmessaging.producer.Producer;
import io.openmessaging.producer.SendResult;

/**
 * SimpleProducer
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2019/2/19
 */
public class SimpleProducerInterceptor {

    public static void main(String[] args) {
        KeyValue keyValue = OMS.newKeyValue();
        keyValue.put(OMSBuiltinKeys.ACCOUNT_KEY, "test_token");

        MessagingAccessPoint messagingAccessPoint = OMS.getMessagingAccessPoint("oms:joyqueue://test_app@%s:50088/UNKNOWN", keyValue);

        Producer producer = messagingAccessPoint.createProducer();
        producer.addInterceptor(new ProducerInterceptor() {
            @Override
            public void preSend(Message message, Context attributes) {
                // 发送前执行
                System.out.println(String.format("preSend, message: %s", message));
            }

            @Override
            public void postSend(Message message, Context attributes) {
                // 发送后执行
                System.out.println(String.format("postSend, message: %s", message));
            }
        });

        producer.start();

        Message message = producer.createMessage("test_topic_0", "body".getBytes());
        SendResult sendResult = producer.send(message);
        System.out.println(String.format("messageId: %s", sendResult.messageId()));
    }
}