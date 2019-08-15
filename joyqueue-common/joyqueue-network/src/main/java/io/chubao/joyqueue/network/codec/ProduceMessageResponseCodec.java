package io.chubao.joyqueue.network.codec;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.chubao.joyqueue.exception.JoyQueueCode;
import io.chubao.joyqueue.network.command.JoyQueueCommandType;
import io.chubao.joyqueue.network.command.ProduceMessageAckData;
import io.chubao.joyqueue.network.command.ProduceMessageAckItemData;
import io.chubao.joyqueue.network.command.ProduceMessageResponse;
import io.chubao.joyqueue.network.serializer.Serializer;
import io.chubao.joyqueue.network.transport.codec.JoyQueueHeader;
import io.chubao.joyqueue.network.transport.codec.PayloadCodec;
import io.chubao.joyqueue.network.transport.command.Type;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.Map;

/**
 * ProduceMessageResponseCodec
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/19
 */
public class ProduceMessageResponseCodec implements PayloadCodec<JoyQueueHeader, ProduceMessageResponse>, Type {

    @Override
    public ProduceMessageResponse decode(JoyQueueHeader header, ByteBuf buffer) throws Exception {
        short dataSize = buffer.readShort();
        Map<String, ProduceMessageAckData> data = Maps.newHashMap();
        for (int i = 0; i < dataSize; i++) {
            String topic = Serializer.readString(buffer, Serializer.SHORT_SIZE);
            JoyQueueCode code = JoyQueueCode.valueOf(buffer.readInt());
            short itemSize = buffer.readShort();
            List<ProduceMessageAckItemData> item = Lists.newArrayListWithCapacity(itemSize);

            for (int j = 0; j < itemSize; j++) {
                short partition = buffer.readShort();
                long index = buffer.readLong();
                long startTime = buffer.readLong();
                item.add(new ProduceMessageAckItemData(partition, index, startTime));
            }
            data.put(topic, new ProduceMessageAckData(item, code));
        }

        ProduceMessageResponse produceMessageResponse = new ProduceMessageResponse();
        produceMessageResponse.setData(data);
        return produceMessageResponse;
    }

    @Override
    public void encode(ProduceMessageResponse payload, ByteBuf buffer) throws Exception {
        buffer.writeShort(payload.getData().size());
        for (Map.Entry<String, ProduceMessageAckData> entry : payload.getData().entrySet()) {
            Serializer.write(entry.getKey(), buffer, Serializer.SHORT_SIZE);
            buffer.writeInt(entry.getValue().getCode().getCode());
            buffer.writeShort(entry.getValue().getItem().size());
            for (ProduceMessageAckItemData data : entry.getValue().getItem()) {
                buffer.writeShort(data.getPartition());
                buffer.writeLong(data.getIndex());
                buffer.writeLong(data.getStartTime());
            }
        }
    }

    @Override
    public int type() {
        return JoyQueueCommandType.PRODUCE_MESSAGE_RESPONSE.getCode();
    }
}