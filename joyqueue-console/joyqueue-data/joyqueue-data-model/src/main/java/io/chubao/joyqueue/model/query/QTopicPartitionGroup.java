package io.chubao.joyqueue.model.query;

import io.chubao.joyqueue.model.QKeyword;
import io.chubao.joyqueue.model.domain.Namespace;
import io.chubao.joyqueue.model.domain.Topic;

public class QTopicPartitionGroup extends QKeyword {
    private Topic topic;
    private Namespace namespace;
    private Integer group;

    public QTopicPartitionGroup(){}
    public QTopicPartitionGroup(Topic topic){
        this.topic=topic;
    }

    public QTopicPartitionGroup(Topic topic, Namespace namespace) {
        this.topic = topic;
        this.namespace = namespace;
    }

    public QTopicPartitionGroup(Topic topic, Namespace namespace, Integer group) {
        this.topic = topic;
        this.namespace = namespace;
        this.group = group;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }
}
