package io.chubao.joyqueue.broker.index.network.codec;

import io.chubao.joyqueue.broker.index.command.ConsumeIndexStoreRequest;
import io.chubao.joyqueue.broker.index.model.IndexAndMetadata;
import io.chubao.joyqueue.network.transport.codec.PayloadEncoder;
import io.chubao.joyqueue.network.command.CommandType;
import io.chubao.joyqueue.network.serializer.Serializer;
import io.chubao.joyqueue.network.transport.command.Type;
import io.netty.buffer.ByteBuf;

import java.util.Map;

/**
 * Created by zhuduohui on 2018/9/7.
 */
public class IndexStoreRequestEncoder implements PayloadEncoder<ConsumeIndexStoreRequest>, Type {

    @Override
    public void encode(final ConsumeIndexStoreRequest request, ByteBuf buffer) throws Exception {
        Map<String, Map<Integer, IndexAndMetadata>> indexMetadata = request.getIndexMetadata();
        Serializer.write(request.getApp(), buffer, Serializer.SHORT_SIZE);
        buffer.writeInt(indexMetadata.size());
        for (String topic : indexMetadata.keySet()) {
            Serializer.write(topic, buffer, Serializer.SHORT_SIZE);
            Map<Integer, IndexAndMetadata> partitionMetadata = indexMetadata.get(topic);
            buffer.writeInt(partitionMetadata.size());
            for (int partition : partitionMetadata.keySet()) {
                buffer.writeInt(partition);
                IndexAndMetadata indexAndMetadata = partitionMetadata.get(partition);
                buffer.writeLong(indexAndMetadata.getIndex());
                Serializer.write(indexAndMetadata.getMetadata(), buffer, Serializer.SHORT_SIZE);
                buffer.writeLong(indexAndMetadata.getIndexCacheRetainTime());
                buffer.writeLong(indexAndMetadata.getIndexCommitTime());
            }
        }
    }

    @Override
    public int type() {
        return CommandType.CONSUME_INDEX_STORE_REQUEST;
    }
}
