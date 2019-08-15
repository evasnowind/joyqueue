package io.chubao.joyqueue.client.internal.producer.transport;

import io.chubao.joyqueue.client.internal.nameserver.NameServerConfig;
import io.chubao.joyqueue.client.internal.nameserver.helper.NameServerHelper;
import io.chubao.joyqueue.client.internal.transport.config.TransportConfig;

/**
 * ProducerClientManagerFactory
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/27
 */
public class ProducerClientManagerFactory {

    public static ProducerClientManager create(String address, String app, String token) {
        return create(address, app, token, null, null);
    }

    public static ProducerClientManager create(String address, String app, String token, String region, String namespace) {
        NameServerConfig nameServerConfig = NameServerHelper.createConfig(address, app, token, region, namespace);
        return create(nameServerConfig, new TransportConfig());
    }

    public static ProducerClientManager create(NameServerConfig nameServerConfig) {
        return create(nameServerConfig, new TransportConfig());
    }

    public static ProducerClientManager create(NameServerConfig nameServerConfig, TransportConfig transportConfig) {
        return new ProducerClientManager(transportConfig, nameServerConfig);
    }
}