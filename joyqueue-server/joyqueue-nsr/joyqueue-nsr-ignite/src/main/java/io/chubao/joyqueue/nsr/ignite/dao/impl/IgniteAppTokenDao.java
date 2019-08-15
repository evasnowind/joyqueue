package io.chubao.joyqueue.nsr.ignite.dao.impl;

import io.chubao.joyqueue.model.PageResult;
import io.chubao.joyqueue.model.QPageQuery;
import io.chubao.joyqueue.nsr.ignite.dao.AppTokenDao;
import io.chubao.joyqueue.nsr.ignite.dao.IgniteDao;
import io.chubao.joyqueue.nsr.ignite.model.IgniteAppToken;
import io.chubao.joyqueue.nsr.model.AppTokenQuery;
import io.chubao.joyqueue.nsr.ignite.model.IgniteBaseModel;
import org.apache.ignite.Ignite;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.QueryIndex;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static io.chubao.joyqueue.nsr.ignite.model.IgniteAppToken.COLUMN_ID;
import static io.chubao.joyqueue.nsr.ignite.model.IgniteAppToken.COLUMN_APP;
import static io.chubao.joyqueue.nsr.ignite.model.IgniteAppToken.COLUMN_TOKEN;
import static io.chubao.joyqueue.nsr.ignite.model.IgniteAppToken.COLUMN_EFFECTIVE_TIME;
import static io.chubao.joyqueue.nsr.ignite.model.IgniteAppToken.COLUMN_EXPIRATION_TIME;

public class IgniteAppTokenDao implements AppTokenDao {

    public static CacheConfiguration<String, IgniteAppToken> cacheCfg;
    public static final String CACHE_NAME = "app_token";
    private IgniteDao igniteDao;

    static {
        cacheCfg = new CacheConfiguration<>();
        cacheCfg.setName(CACHE_NAME);
        cacheCfg.setSqlSchema(IgniteBaseModel.SCHEMA);
        cacheCfg.setCacheMode(CacheMode.REPLICATED);
        cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        QueryEntity queryEntity = new QueryEntity();
        queryEntity.setKeyType(Long.class.getName());
        queryEntity.setValueType(IgniteAppToken.class.getName());
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put(COLUMN_ID, Long.class.getName());
        fields.put(COLUMN_APP, String.class.getName());
        fields.put(COLUMN_TOKEN, String.class.getName());
        fields.put(COLUMN_EFFECTIVE_TIME, Date.class.getName());
        fields.put(COLUMN_EXPIRATION_TIME, Date.class.getName());
        queryEntity.setFields(fields);
        queryEntity.setTableName(CACHE_NAME);
        queryEntity.setIndexes(Arrays.asList(new QueryIndex(COLUMN_ID)));
        cacheCfg.setQueryEntities(Arrays.asList(queryEntity));
    }

    public IgniteAppTokenDao(Ignite ignite) {
        this.igniteDao = new IgniteDao(ignite, cacheCfg);
    }


    @Override
    public IgniteAppToken findById(Long id) {
        return igniteDao.getById(id);
    }

    @Override
    public void add(IgniteAppToken model) {
        igniteDao.getById(new IgniteAppToken(model).getId());
    }

    @Override
    public void addOrUpdate(IgniteAppToken model) {
        igniteDao.addOrUpdate(model);
    }

    @Override
    public void deleteById(Long id) {
        igniteDao.deleteById(id);
    }

    @Override
    public PageResult<IgniteAppToken> pageQuery(QPageQuery<AppTokenQuery> pageQuery) {
        return igniteDao.pageQuery(buildQuery(pageQuery.getQuery()), pageQuery.getPagination());
    }

    @Override
    public List<IgniteAppToken> list(AppTokenQuery query) {
        return igniteDao.query(buildQuery(query));
    }

    private SqlQuery buildQuery(AppTokenQuery query) {
        IgniteDao.SimpleSqlBuilder sqlBuilder = IgniteDao.SimpleSqlBuilder.create(IgniteAppToken.class);
        if (query != null) {
            if (query.getApp() != null && !query.getApp().isEmpty()) {
                sqlBuilder.and(COLUMN_APP, query.getApp());
            }

            if (query.getToken() != null && !query.getToken().isEmpty()) {
                sqlBuilder.and(COLUMN_TOKEN, query.getToken());
            }

        }
        return sqlBuilder.build();
    }

}
