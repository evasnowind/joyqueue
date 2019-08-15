package io.chubao.joyqueue.network.codec;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.chubao.joyqueue.network.command.FetchPartitionMessageRequest;
import io.chubao.joyqueue.network.command.FetchPartitionMessageData;
import io.chubao.joyqueue.network.command.JoyQueueCommandType;
import io.chubao.joyqueue.network.serializer.Serializer;
import io.chubao.joyqueue.network.transport.codec.JoyQueueHeader;
import io.chubao.joyqueue.network.transport.codec.PayloadCodec;
import io.chubao.joyqueue.network.transport.command.Type;
import io.netty.buffer.ByteBuf;

import java.util.Map;

/**
 * FetchPartitionMessageRequestCodec
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/13
 */
public class FetchPartitionMessageRequestCodec implements PayloadCodec<JoyQueueHeader, FetchPartitionMessageRequest>, Type {

    @Override
    public FetchPartitionMessageRequest decode(JoyQueueHeader header, ByteBuf buffer) throws Exception {
        Table<String, Short, FetchPartitionMessageData> partitions = HashBasedTable.create();
        int topicSize = buffer.readShort();
        for (int i = 0; i < topicSize; i++) {
            String topic = Serializer.readString(buffer, Serializer.SHORT_SIZE);
            int partitionSize = buffer.readShort();
            for (int j = 0; j < partitionSize; j++) {
                short partition = buffer.readShort();
                int count = buffer.readInt();
                long index = buffer.readLong();

                partitions.put(topic, partition, new FetchPartitionMessageData(count, index));
            }
        }

        FetchPartitionMessageRequest fetchPartitionMessageRequest = new FetchPartitionMessageRequest();
        fetchPartitionMessageRequest.setPartitions(partitions);
        fetchPartitionMessageRequest.setApp(Serializer.readString(buffer, Serializer.SHORT_SIZE));
        return fetchPartitionMessageRequest;
    }

    @Override
    public void encode(FetchPartitionMessageRequest payload, ByteBuf buffer) throws Exception {
        buffer.writeShort(payload.getPartitions().rowMap().size());
        for (Map.Entry<String, Map<Short, FetchPartitionMessageData>> topicEntry : payload.getPartitions().rowMap().entrySet()) {
            Serializer.write(topicEntry.getKey(), buffer, Serializer.SHORT_SIZE);
            buffer.writeShort(topicEntry.getValue().size());
            for (Map.Entry<Short, FetchPartitionMessageData> partitionEntry : topicEntry.getValue().entrySet()) {
                FetchPartitionMessageData fetchPartitionMessageData = partitionEntry.getValue();
                buffer.writeShort(partitionEntry.getKey());
                buffer.writeInt(fetchPartitionMessageData.getCount());
                buffer.writeLong(fetchPartitionMessageData.getIndex());
            }
        }
        Serializer.write(payload.getApp(), buffer, Serializer.SHORT_SIZE);
    }

    @Override
    public int type() {
        return JoyQueueCommandType.FETCH_PARTITION_MESSAGE_REQUEST.getCode();
    }
}