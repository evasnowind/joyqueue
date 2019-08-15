package io.chubao.joyqueue.nsr.ignite.message;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.chubao.joyqueue.event.MetaEvent;
import io.chubao.joyqueue.nsr.ignite.model.IgniteMessage;
import io.chubao.joyqueue.nsr.ignite.dao.IgniteMessageDao;
import io.chubao.joyqueue.nsr.message.MessageListener;
import io.chubao.joyqueue.nsr.message.Messenger;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteMessaging;
import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.internal.binary.BinaryObjectImpl;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgnitePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


@Singleton
public class IgniteMessenger implements Messenger<MetaEvent> {
    private static Logger logger = LoggerFactory.getLogger(IgniteMessenger.class);
    private IgniteMessageDao msg;
    private IgniteMessaging igniteMessaging;
    private Ignite ignite;
    private static final String EVENT_TOPIC = "event_topic";
    @Inject
    public IgniteMessenger(Ignite ignite) {
        this.ignite = ignite;
        msg = new IgniteMessageDao(ignite);
        igniteMessaging = ignite.message();
    }

    @Override
    public void publish(MetaEvent event) {
        IgniteMessage igniteMessage = new IgniteMessage(event.getClass().getTypeName(), JSON.toJSONString(event));
        logger.info("begin public message [{}]",igniteMessage.getContent());
        msg.addOrUpdate(igniteMessage);
        igniteMessaging.send(EVENT_TOPIC,JSON.toJSONString(igniteMessage));
    }

    @Override
    public void addListener(MessageListener listener) {
        IgniteTableListener messageListener = new IgniteTableListener(listener);
        IgniteTopicListener topicListener = new IgniteTopicListener(listener);
        if(!ignite.cluster().localNode().isClient())ignite.events(ignite.cluster().forLocal()).remoteListen(messageListener,
                (IgnitePredicate<CacheEvent>) event -> event.cacheName().equals(IgniteMessageDao.CACHE_NAME),
                EventType.EVT_CACHE_OBJECT_PUT,
                EventType.EVT_CACHE_OBJECT_REMOVED);
        else ignite.message(ignite.cluster().forLocal()).remoteListen(EVENT_TOPIC,topicListener);
    }

    static class IgniteTableListener implements IgniteBiPredicate<UUID, CacheEvent>{
        private static Logger logger = LoggerFactory.getLogger(IgniteTableListener.class);
        private MessageListener listener;
        IgniteTableListener(MessageListener listener){
            this.listener = listener;
        }
        @Override
        public boolean apply(UUID uuid, CacheEvent cacheEvent) {
            Object value = cacheEvent.newValue();
            logger.info("receive cache {}, event {}, eventClass {},onevent {}", cacheEvent.cacheName(),value,value.getClass().getName(),value instanceof IgniteMessage);
            try {
                IgniteMessage message = null;
                if (value instanceof IgniteMessage) {
                    message = (IgniteMessage) value;
                }else if(value instanceof BinaryObjectImpl){
                    message = ((BinaryObjectImpl)value).deserialize();
                }
                if(null!=message){
                    logger.info("receive meta cache event:{}", message.getContent());
                    listener.onEvent(JSON.parseObject(message.getContent(), Class.forName(message.getType())));
                }
                return true;
            } catch (Throwable ignore) {
                logger.error("broadcast error",ignore);
                return true;
            }
        }
    }
    static class IgniteTopicListener implements IgniteBiPredicate<UUID, String>{
        private static Logger logger = LoggerFactory.getLogger(IgniteTopicListener.class);
        private MessageListener listener;
        IgniteTopicListener(MessageListener listener){
            this.listener = listener;
        }
        @Override
        public boolean apply(UUID uuid, String eventMessage) {
            logger.info("receive meta cache event:{}", eventMessage);
            try {
                IgniteMessage igniteMessage  = JSON.parseObject(eventMessage,IgniteMessage.class);
                listener.onEvent(JSON.parseObject(igniteMessage.getContent(), Class.forName(igniteMessage.getType())));
                return true;
            } catch (Throwable ignore) {
                logger.error("broadcast error",ignore);
                return true;
            }
        }
    }

}
