package io.chubao.joyqueue.network.codec;

import com.google.common.collect.Lists;
import io.chubao.joyqueue.network.command.FetchAssignedPartitionData;
import io.chubao.joyqueue.network.command.FetchAssignedPartitionRequest;
import io.chubao.joyqueue.network.command.JoyQueueCommandType;
import io.chubao.joyqueue.network.serializer.Serializer;
import io.chubao.joyqueue.network.transport.codec.JoyQueueHeader;
import io.chubao.joyqueue.network.transport.codec.PayloadCodec;
import io.chubao.joyqueue.network.transport.command.Type;
import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * FetchAssignedPartitionRequestCodec
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/4
 */
public class FetchAssignedPartitionRequestCodec implements PayloadCodec<JoyQueueHeader, FetchAssignedPartitionRequest>, Type {

    @Override
    public FetchAssignedPartitionRequest decode(JoyQueueHeader header, ByteBuf buffer) throws Exception {
        short dataSize = buffer.readShort();
        List<FetchAssignedPartitionData> data = Lists.newArrayListWithCapacity(dataSize);
        for (int i = 0; i < dataSize; i++) {
            FetchAssignedPartitionData fetchAssignedPartitionData = new FetchAssignedPartitionData();
            fetchAssignedPartitionData.setTopic(Serializer.readString(buffer, Serializer.SHORT_SIZE));
            fetchAssignedPartitionData.setSessionTimeout(buffer.readInt());
            fetchAssignedPartitionData.setNearby(buffer.readBoolean());
            data.add(fetchAssignedPartitionData);
        }

        FetchAssignedPartitionRequest fetchAssignedPartitionRequest = new FetchAssignedPartitionRequest();
        fetchAssignedPartitionRequest.setApp(Serializer.readString(buffer, Serializer.SHORT_SIZE));
        fetchAssignedPartitionRequest.setData(data);
        return fetchAssignedPartitionRequest;
    }

    @Override
    public void encode(FetchAssignedPartitionRequest payload, ByteBuf buffer) throws Exception {
        buffer.writeShort(payload.getData().size());
        for (FetchAssignedPartitionData fetchAssignedPartitionData : payload.getData()) {
            Serializer.write(fetchAssignedPartitionData.getTopic(), buffer, Serializer.SHORT_SIZE);
            buffer.writeInt(fetchAssignedPartitionData.getSessionTimeout());
            buffer.writeBoolean(fetchAssignedPartitionData.isNearby());
        }
        Serializer.write(payload.getApp(), buffer, Serializer.SHORT_SIZE);
    }

    @Override
    public int type() {
        return JoyQueueCommandType.FETCH_ASSIGNED_PARTITION_REQUEST.getCode();
    }
}