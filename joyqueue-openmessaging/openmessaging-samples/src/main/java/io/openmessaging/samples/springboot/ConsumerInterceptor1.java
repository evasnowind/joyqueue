package io.openmessaging.samples.springboot;

import io.openmessaging.interceptor.ConsumerInterceptor;
import io.openmessaging.interceptor.Context;
import io.openmessaging.message.Message;
import io.openmessaging.spring.boot.annotation.OMSInterceptor;

@OMSInterceptor
public class ConsumerInterceptor1 implements ConsumerInterceptor {

    @Override
    public void preReceive(Message message, Context attributes) {
        System.out.println(String.format("preReceive, message: %s", message));
    }

    @Override
    public void postReceive(Message message, Context attributes) {
        System.out.println(String.format("postReceive, message: %s", message));
    }
}