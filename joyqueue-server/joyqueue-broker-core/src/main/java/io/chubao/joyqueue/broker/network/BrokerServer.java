package io.chubao.joyqueue.broker.network;

import com.google.common.base.Preconditions;
import io.chubao.joyqueue.broker.BrokerContext;
import io.chubao.joyqueue.broker.monitor.SessionManager;
import io.chubao.joyqueue.broker.network.backend.BackendServer;
import io.chubao.joyqueue.broker.network.frontend.FrontendServer;
import io.chubao.joyqueue.broker.network.listener.BrokerTransportListener;
import io.chubao.joyqueue.broker.network.protocol.ProtocolManager;
import io.chubao.joyqueue.network.transport.config.ServerConfig;
import io.chubao.joyqueue.toolkit.service.Service;

/**
 * BrokerServer
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/8/14
 */
public class BrokerServer extends Service {

    private FrontendServer frontendServer;
    private BackendServer backendServer;
    private BrokerTransportListener transportListener;

    public BrokerServer(BrokerContext brokerContext, ProtocolManager protocolManager) {
        Preconditions.checkArgument(brokerContext != null, "broker context can not be null");
        Preconditions.checkArgument(protocolManager != null, "protocol manager can not be null");

        ServerConfig frontendConfig = brokerContext.getBrokerConfig().getFrontendConfig();
        ServerConfig backendConfig = brokerContext.getBrokerConfig().getBackendConfig();
        SessionManager sessionManager = brokerContext.getSessionManager();

        frontendConfig.setAcceptThreadName("joyqueue-frontend-accept-eventLoop");
        frontendConfig.setIoThreadName("joyqueue-frontend-io-eventLoop");
        backendConfig.setAcceptThreadName("joyqueue-backend-accept-eventLoop");
        backendConfig.setIoThreadName("joyqueue-backend-io-eventLoop");

        this.transportListener = new BrokerTransportListener(sessionManager);
        this.frontendServer = new FrontendServer(frontendConfig, brokerContext, protocolManager);
        this.backendServer = new BackendServer(backendConfig, brokerContext);
        this.frontendServer.addListener(transportListener);
        this.backendServer.addListener(transportListener);
    }

    @Override
    protected void doStart() throws Exception {
        this.frontendServer.start();
        this.backendServer.start();
    }

    @Override
    protected void doStop() {
        this.frontendServer.removeListener(transportListener);
        this.backendServer.removeListener(transportListener);
        this.frontendServer.stop();
        this.backendServer.stop();
    }
}