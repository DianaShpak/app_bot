package core.managers.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisManager {
    final JedisPoolConfig poolConfig;
    final JedisPool jedisPool;

    public RedisManager(String hostRedis, int port, int timeout) {
        this.poolConfig = buildPoolConfig();
        this.jedisPool = new JedisPool(poolConfig, hostRedis, port, timeout);
    }

    public RedisManager(String hostRedis) {
        this.poolConfig = buildPoolConfig();
        this.jedisPool = new JedisPool(poolConfig, hostRedis);
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(6_000);
//        jedisPoolConfig.setMaxIdle(6_000);
//        jedisPoolConfig.setMinIdle(2_000);
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setMaxWaitMillis(2_000);
        return jedisPoolConfig;
    }

    public void lpushKeyElement(String key, String... value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.lpush(key, value);
        }
    }

    public List<String> lrangeKey(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            List<String> list = jedis.lrange(key, 0, -1);
            jedis.close();
            return list;
        }
    }

    public void saddKeyElement(String key, String... value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.sadd(key, value);
        }
    }

    public Set<String> smembersKey(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            Set<String> set = jedis.smembers(key);
            jedis.close();
            return set;
        }
    }

    public void sremElement(String key, String member) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.srem(key, member);
        }
    }

    public void hsetKey(String key, Map<String, String> value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.hset(key, value);
        }
    }

    public String hgetFieldValue(String key, String field) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.hget(key, field);
        }
    }

    public Set<String> getKeysByPattern(String pattern) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.keys(pattern == null ? "*" : pattern);
        }
    }


    public void del(String... key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    public void flushAll(String... key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.flushAll();
        }
    }
}
