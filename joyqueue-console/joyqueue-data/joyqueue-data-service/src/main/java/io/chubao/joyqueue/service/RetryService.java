package io.chubao.joyqueue.service;

import io.chubao.joyqueue.domain.ConsumeRetry;
import io.chubao.joyqueue.exception.JoyQueueException;
import io.chubao.joyqueue.model.PageResult;
import io.chubao.joyqueue.model.QPageQuery;
import io.chubao.joyqueue.model.query.QRetry;
import io.chubao.joyqueue.server.retry.model.RetryMessageModel;

/**
 * Created by wangxiaofei1 on 2018/12/5.
 */
public interface RetryService {

    PageResult<ConsumeRetry> findByQuery(QPageQuery<QRetry> qRetryQPageQuery) throws JoyQueueException;

    ConsumeRetry getDataById(Long id) throws JoyQueueException;

    void add(RetryMessageModel retryMessageModel);

    /**
     * 恢复 根据状态修改
     * @param retry
     * @return
     */
    void recover(ConsumeRetry retry) throws Exception;

    void delete(ConsumeRetry retry) throws Exception;

    /**
     * 重试服务是否可用
     * @throws Exception
     */
    boolean isServerEnabled();
}
