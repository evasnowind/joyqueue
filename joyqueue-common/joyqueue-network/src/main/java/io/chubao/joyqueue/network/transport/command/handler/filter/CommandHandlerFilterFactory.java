package io.chubao.joyqueue.network.transport.command.handler.filter;

import java.util.List;

/**
 * CommandHandlerFilterFactory
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/8/16
 */
public interface CommandHandlerFilterFactory {

    List<CommandHandlerFilter> getFilters();
}