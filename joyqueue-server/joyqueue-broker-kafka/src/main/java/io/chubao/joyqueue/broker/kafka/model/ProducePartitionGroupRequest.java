package io.chubao.joyqueue.broker.kafka.model;

import io.chubao.joyqueue.broker.kafka.message.KafkaBrokerMessage;
import io.chubao.joyqueue.message.BrokerMessage;

import java.util.List;
import java.util.Map;

/**
 * ProducePartitionGroupRequest
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2019/4/9
 */
public class ProducePartitionGroupRequest {

    private List<Integer> partitions;
    private List<BrokerMessage> messages;
    private List<KafkaBrokerMessage> kafkaMessages;
    private Map<Integer, List<BrokerMessage>> messageMap;
    private Map<Integer, List<KafkaBrokerMessage>> kafkaMessageMap;

    public ProducePartitionGroupRequest() {

    }

    public ProducePartitionGroupRequest(List<Integer> partitions, List<BrokerMessage> messages, List<KafkaBrokerMessage> kafkaMessages,
                                        Map<Integer, List<BrokerMessage>> messageMap, Map<Integer, List<KafkaBrokerMessage>> kafkaMessageMap) {
        this.partitions = partitions;
        this.messages = messages;
        this.kafkaMessages = kafkaMessages;
        this.messageMap = messageMap;
        this.kafkaMessageMap = kafkaMessageMap;
    }

    public void setPartitions(List<Integer> partitions) {
        this.partitions = partitions;
    }

    public List<Integer> getPartitions() {
        return partitions;
    }

    public void setMessages(List<BrokerMessage> messages) {
        this.messages = messages;
    }

    public List<BrokerMessage> getMessages() {
        return messages;
    }

    public void setKafkaMessageMap(Map<Integer, List<KafkaBrokerMessage>> kafkaMessageMap) {
        this.kafkaMessageMap = kafkaMessageMap;
    }

    public List<KafkaBrokerMessage> getKafkaMessages() {
        return kafkaMessages;
    }

    public void setMessageMap(Map<Integer, List<BrokerMessage>> messageMap) {
        this.messageMap = messageMap;
    }

    public Map<Integer, List<BrokerMessage>> getMessageMap() {
        return messageMap;
    }

    public void setKafkaMessages(List<KafkaBrokerMessage> kafkaMessages) {
        this.kafkaMessages = kafkaMessages;
    }

    public Map<Integer, List<KafkaBrokerMessage>> getKafkaMessageMap() {
        return kafkaMessageMap;
    }

    @Override
    public String toString() {
        return "ProducePartitionGroupRequest{" +
                "partitions=" + partitions +
                ", messages=" + messages +
                ", kafkaMessages=" + kafkaMessages +
                ", messageMap=" + messageMap +
                ", kafkaMessageMap=" + kafkaMessageMap +
                '}';
    }
}