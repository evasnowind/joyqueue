package io.chubao.joyqueue.broker.mqtt.cluster;

import io.chubao.joyqueue.broker.mqtt.connection.MqttConnection;
import com.google.common.base.Strings;
import io.chubao.joyqueue.toolkit.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author majun8
 */
public class MqttConnectionManager extends Service {
    private static final Logger LOG = LoggerFactory.getLogger(MqttConnectionManager.class);

    private final ConcurrentMap<String, MqttConnection> connections = new ConcurrentHashMap<>();

    public MqttConnection addConnection(final MqttConnection connection) {
        return connections.putIfAbsent(connection.getClientId(), connection);
    }

    public boolean removeConnection(MqttConnection connection) {
        return connections.remove(connection.getClientId(), connection);
    }

    public Optional<MqttConnection> lookupConnection(String clientID) {
        if (clientID == null) {
            return Optional.empty();
        }

        MqttConnection connection = connections.get(clientID);
        if (connection == null) {
            return Optional.empty();
        }
        return Optional.of(connection);
    }

    public MqttConnection getConnection(String clientID) {
        MqttConnection connection = null;
        if (Strings.isNullOrEmpty(clientID)) {
            LOG.error("ClientID is null or empty for find connection, aborting...");
        } else {
            connection = connections.get(clientID);
            if (connection == null) {
                LOG.error("Can't find the connection for client: <{}>", clientID);
                throw new RuntimeException("Can't find the connection for client <" + clientID + ">");
            }
        }
        return connection;
    }

    public boolean isConnected(String clientID) {
        return connections.containsKey(clientID);
    }

    public int countConnections() {
        return connections.size();
    }

    public Collection<String> getConnectionIds() {
        return connections.keySet();
    }

    public ConcurrentMap<String, MqttConnection> listConnections() {
        return connections;
    }

    public int connectionsTotal() {
        return connections.size();
    }
}
