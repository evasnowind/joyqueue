package io.chubao.joyqueue.nsr.network.command;

import io.chubao.joyqueue.network.transport.command.JoyQueuePayload;

/**
 * @author wylixiaobin
 * Date: 2019/1/29
 */
public class GetConfig extends JoyQueuePayload {
    private String group;
    private String key;
    public GetConfig group(String group){
        this.group = group;
        return this;
    }

    public GetConfig key(String key){
        this.key = key;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public String getKey() {
        return key;
    }

    @Override
    public int type() {
        return NsrCommandType.GET_CONFIG;
    }

    @Override
    public String toString() {
        return "GetConfig{" +
                "group='" + group + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
