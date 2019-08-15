package io.chubao.joyqueue.nsr.network.codec;

import io.chubao.joyqueue.domain.Consumer;
import io.chubao.joyqueue.network.serializer.Serializer;
import io.chubao.joyqueue.network.transport.command.Header;
import io.chubao.joyqueue.network.transport.command.Type;
import io.chubao.joyqueue.nsr.network.NsrPayloadCodec;
import io.chubao.joyqueue.nsr.network.command.GetConsumerByTopicAck;
import io.chubao.joyqueue.nsr.network.command.NsrCommandType;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wylixiaobin
 * Date: 2019/1/29
 */
public class GetConsumerByTopicAckCodec implements NsrPayloadCodec<GetConsumerByTopicAck>, Type {
    @Override
    public GetConsumerByTopicAck decode(Header header, ByteBuf buffer) throws Exception {
        GetConsumerByTopicAck getConsumerByTopicAck = new GetConsumerByTopicAck();
            int size = buffer.readInt();
            List<Consumer> list = new ArrayList<>(size);
            for(int i = 0;i<size;i++){
                list.add(Serializer.readConsumer(header.getVersion(), buffer));
            }
            getConsumerByTopicAck.consumers(list);
        return getConsumerByTopicAck;
    }

    @Override
    public void encode(GetConsumerByTopicAck payload, ByteBuf buffer) throws Exception {
        List<Consumer> consumers = payload.getConsumers();
        if(null==consumers||consumers.size()<1){
            buffer.writeInt(0);
            return;
        }
        buffer.writeInt(consumers.size());
        for(Consumer consumer : consumers){
            Serializer.write(payload.getHeader().getVersion(), consumer,buffer);
        }
    }

    @Override
    public int type() {
        return NsrCommandType.GET_CONSUMER_BY_TOPIC_ACK;
    }
}
