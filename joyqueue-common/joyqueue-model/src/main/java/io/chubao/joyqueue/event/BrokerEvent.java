package io.chubao.joyqueue.event;

import io.chubao.joyqueue.domain.Broker;

public class BrokerEvent extends MetaEvent {
    private Broker broker;

    public BrokerEvent() {
    }

    private BrokerEvent(EventType type, Broker broker) {
        super(type);
        this.broker = broker;
    }
    @Override
    public String getTypeName() {
        return getClass().getTypeName();
    }
    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public static BrokerEvent event(Broker broker) {
        return new BrokerEvent(EventType.UPDATE_BROKER, broker);
    }

    @Override
    public String toString() {
        return "BrokerEvent{" +
                "broker=" + broker +
                '}';
    }
}
