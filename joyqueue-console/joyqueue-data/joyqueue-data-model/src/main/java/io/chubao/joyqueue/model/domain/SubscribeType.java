package io.chubao.joyqueue.model.domain;

import static io.chubao.joyqueue.model.domain.Consumer.CONSUMER_TYPE;
import static io.chubao.joyqueue.model.domain.Producer.PRODUCER_TYPE;

/**
 * 订阅类型
 */
public enum SubscribeType implements EnumItem {

    PRODUCER(PRODUCER_TYPE, "生产者"),
    CONSUMER(CONSUMER_TYPE, "消费者");

    private int value;
    private String description;

    SubscribeType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public String description() {
        return this.description;
    }

    public static SubscribeType resolve(Object valueOrName) {
        if (valueOrName == null) {
            return null;
        }
        for (SubscribeType type : SubscribeType.values()) {
            if ((valueOrName instanceof String && type.name().equals(valueOrName))
                    || (valueOrName instanceof Integer && type.value == Integer.valueOf(valueOrName.toString()))
                    || (valueOrName instanceof SubscribeType && type.name() == ((SubscribeType) valueOrName).name())) {
                return type;
            }
        }
        return null;
    }


}
