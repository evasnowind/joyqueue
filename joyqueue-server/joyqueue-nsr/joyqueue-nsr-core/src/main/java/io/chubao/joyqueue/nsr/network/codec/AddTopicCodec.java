package io.chubao.joyqueue.nsr.network.codec;

import io.chubao.joyqueue.domain.PartitionGroup;
import io.chubao.joyqueue.network.serializer.Serializer;
import io.chubao.joyqueue.network.transport.command.Header;
import io.chubao.joyqueue.network.transport.command.Type;
import io.chubao.joyqueue.nsr.network.NsrPayloadCodec;
import io.chubao.joyqueue.nsr.network.command.AddTopic;
import io.chubao.joyqueue.nsr.network.command.NsrCommandType;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wylixiaobin
 * Date: 2019/1/29
 */
public class AddTopicCodec implements NsrPayloadCodec<AddTopic>, Type {
    @Override
    public AddTopic decode(Header header, ByteBuf buffer) throws Exception {
        AddTopic addTopic = new AddTopic();
        addTopic.topic(Serializer.readTopic(buffer));
        int size = buffer.readInt();
        List<PartitionGroup> partitionGroups = new ArrayList<>(size);
        for(int i = 0;i<size;i++){
            partitionGroups.add(Serializer.readPartitionGroup(buffer, header.getVersion()));
        }
        addTopic.partitiionGroups(partitionGroups);
        return addTopic;
    }

    @Override
    public void encode(AddTopic payload, ByteBuf buffer) throws Exception {
        Serializer.write(payload.getTopic(),buffer);
        List<PartitionGroup> partitionGroupList = payload.getPartitionGroups();
        buffer.writeInt(partitionGroupList.size());
        for(PartitionGroup group : partitionGroupList){
            Serializer.write(group,buffer);
        }
    }

    @Override
    public int type() {
        return NsrCommandType.ADD_TOPIC;
    }
}
