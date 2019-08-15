package io.chubao.joyqueue.broker.kafka.handler;

import com.google.common.collect.Lists;
import io.chubao.joyqueue.broker.kafka.KafkaCommandType;
import io.chubao.joyqueue.broker.kafka.KafkaErrorCode;
import io.chubao.joyqueue.broker.kafka.command.ApiVersionsRequest;
import io.chubao.joyqueue.broker.kafka.model.ApiVersion;
import io.chubao.joyqueue.network.transport.command.Command;
import io.chubao.joyqueue.network.transport.Transport;
import io.chubao.joyqueue.broker.kafka.command.ApiVersionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * ApiVersionsRequestHandler
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/11/5
 */
public class ApiVersionsRequestHandler extends AbstractKafkaCommandHandler {

    protected static final Logger logger = LoggerFactory.getLogger(ApiVersionsRequestHandler.class);

    private static final List<ApiVersion> APIS;
    private static final ApiVersionsResponse SUPPORTED_RESPONSE;

    static {
        List<ApiVersion> apis = Lists.newLinkedList();
        for (KafkaCommandType command : KafkaCommandType.values()) {
            if (command.isExport()) {
                apis.add(new ApiVersion(command.getCode(), command.getMinVersion(), command.getMaxVersion()));
            }
        }
        APIS = Collections.unmodifiableList(apis);
        SUPPORTED_RESPONSE = new ApiVersionsResponse(KafkaErrorCode.NONE.getCode(), APIS);
    }

    @Override
    public Command handle(Transport transport, Command command) {
        ApiVersionsRequest apiVersionsRequest = (ApiVersionsRequest) command.getPayload();

        // TODO 临时日志
        logger.debug("fetch api version, transport: {}, version: {}", transport.remoteAddress(), apiVersionsRequest.getVersion());
        return new Command(SUPPORTED_RESPONSE);
    }

    @Override
    public int type() {
        return KafkaCommandType.API_VERSIONS.getCode();
    }
}