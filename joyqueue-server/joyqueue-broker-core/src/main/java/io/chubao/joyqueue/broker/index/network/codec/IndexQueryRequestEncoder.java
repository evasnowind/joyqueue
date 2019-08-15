package io.chubao.joyqueue.broker.index.network.codec;

import io.chubao.joyqueue.broker.index.command.ConsumeIndexQueryRequest;
import io.chubao.joyqueue.network.command.CommandType;
import io.chubao.joyqueue.network.serializer.Serializer;
import io.chubao.joyqueue.network.transport.codec.PayloadEncoder;
import io.chubao.joyqueue.network.transport.command.Type;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuduohui on 2018/9/7.
 */
public class IndexQueryRequestEncoder implements PayloadEncoder<ConsumeIndexQueryRequest>, Type {

    @Override
    public void encode(ConsumeIndexQueryRequest payload, ByteBuf buf) throws Exception {
        Map<String, List<Integer>> indexTopicPartitions = payload.getTopicPartitions();
        Serializer.write(payload.getApp(), buf, Serializer.SHORT_SIZE);
        buf.writeInt(indexTopicPartitions.size());
        for (String topic : indexTopicPartitions.keySet()) {
            Serializer.write(topic, buf, Serializer.SHORT_SIZE);
            List<Integer> partitions = indexTopicPartitions.get(topic);
            buf.writeInt(partitions.size());
            for (int partition : partitions) {
                buf.writeInt(partition);
            }
        }
    }

    @Override
    public int type() {
        return CommandType.CONSUME_INDEX_QUERY_REQUEST;
    }
}
