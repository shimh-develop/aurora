package com.shimh.aurora.passport.session;

import com.shimh.aurora.common.exception.AuroraException;
import com.shimh.aurora.common.utils.SerializeUtils;
import lombok.Data;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Data
public class RedisSessionDAO extends CachingSessionDAO{

    private static final String SESSION_KEY_PREFIX = "session_";

    private RedisTemplate redisTemplate;

    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        ValueOperations<String,String> operations = redisTemplate.opsForValue();
        operations.set(SESSION_KEY_PREFIX + sessionId, SerializeUtils.serialize(session), session.getTimeout(), TimeUnit.MILLISECONDS);
        return session.getId();
    }
    protected void doUpdate(Session session) {
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return; //如果会话过期/停止 没必要再更新了
        }
        ValueOperations<String,String> operations = redisTemplate.opsForValue();
        operations.set(SESSION_KEY_PREFIX + session.getId(), SerializeUtils.serialize(session), session.getTimeout(), TimeUnit.MILLISECONDS);
    }
    protected void doDelete(Session session) {
        redisTemplate.delete(SESSION_KEY_PREFIX + session.getId());
    }
    protected Session doReadSession(Serializable sessionId) {
        ValueOperations<String,String> operations = redisTemplate.opsForValue();
        String session = operations.get(SESSION_KEY_PREFIX + sessionId);
        if (null == session) {
            return null;
        }
        return (Session) SerializeUtils.deserialize(session);
    }

    @PostConstruct
    public void init() {
        Objects.requireNonNull(redisTemplate, "RedisTemplate不能是空.");
    }
}
