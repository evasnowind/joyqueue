package io.chubao.joyqueue.broker.index.network.codec;

import io.chubao.joyqueue.broker.index.command.ConsumeIndexQueryRequest;
import io.chubao.joyqueue.network.command.CommandType;
import io.chubao.joyqueue.network.serializer.Serializer;
import io.chubao.joyqueue.network.transport.codec.JoyQueueHeader;
import io.chubao.joyqueue.network.transport.codec.PayloadDecoder;
import io.chubao.joyqueue.network.transport.command.Type;
import io.netty.buffer.ByteBuf;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuduohui on 2018/9/7.
 */
public class IndexQueryRequestDecoder implements PayloadDecoder<JoyQueueHeader>, Type {

    @Override
    public Object decode(JoyQueueHeader header, ByteBuf buffer) throws Exception {
        Map<String, List<Integer>> indexTopicPartitions = new HashedMap();
        String groupId = Serializer.readString(buffer, Serializer.SHORT_SIZE);
        int topics = buffer.readInt();
        for (int i = 0; i < topics; i++) {
            String topic = Serializer.readString(buffer, Serializer.SHORT_SIZE);
            List<Integer> partitions = new ArrayList<>();
            int partitionNum = buffer.readInt();
            for (int j = 0; j < partitionNum; j++) {
                partitions.add(buffer.readInt());
            }
            indexTopicPartitions.put(topic, partitions);
        }
        return new ConsumeIndexQueryRequest(groupId, indexTopicPartitions);
    }

    @Override
    public int type() {
        return CommandType.CONSUME_INDEX_QUERY_REQUEST;
    }
}
