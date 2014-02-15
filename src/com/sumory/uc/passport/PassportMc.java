package com.sumory.uc.passport;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sumory.uc.config.ConfigUtils;
import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

/**
 * Passport的memcache接口实现
 * <p>
 * 业务要求<b><i>强一致性</i></b>，因此只使用一个节点的一台mc服务器，add，get，delete，replace等方法都是以这个为前提考虑的
 * 
 * @author sumory.wu
 * @date 2012-2-23 下午6:35:31
 */
public class PassportMc implements IMc {

    private static final Logger log = LoggerFactory.getLogger(PassportMc.class);

    private static MemcachedClient mcc = null;
    private static String[] servers = (String[]) (ConfigUtils.getPassportConfig().get("mc-nodes"));// 全局考虑，只需要一个节点一台mc服务器，避免多节点的各种同步问题
    private static String poolName = "passport";

    public PassportMc() {
        // 读取配置servers到server数组,poolName到poolName
        if (servers == null || servers.length <= 0) {
            log.error("no memcache machine found!");
            System.exit(0);// 记录日志并正常退出程序
        }
        SockIOPool pool = SockIOPool.getInstance(poolName);
        pool.setServers(servers);
        pool.initialize();// 读源码得知同一个pool不会被重复初始化
        // com.meetup.memcached.Logger.getLogger(MemcachedClient.class.getName()
        // ).setLevel(com.meetup.memcached.Logger.LEVEL_ERROR );//关闭大部分logging
        mcc = new MemcachedClient(poolName);// 每个client使用poolName的连接池
    }

    /**
     * 判断key是否存在,不存在或服务器宕机时返回false
     * 
     * @param key
     * @return
     */
    public boolean keyExists(String key) {
        return mcc.keyExists(key);
    }

    /**
     * add一个值到mc，成功add后永不过期
     * 
     * @param key
     * @param userId
     * @return
     */
    public boolean add(String key, long userId) {
        return mcc.add(key, userId);
    }

    /**
     * add一个值到mc，带过期时间expiry
     * 
     * @param key key
     * @param userId value
     * @param expiry 过期时间，毫秒级别，示例：10分钟可以为System.currentTimeInMillis()+10*60*1000, 也可以为10*60*1000，由于服务器时间和客户端时间可能不一致，建议使用后者以服务器时间为基准
     * @return
     */
    public boolean add(String key, long userId, Date expiry) {
        return mcc.add(key, userId, expiry);
    }

    /**
     * set一个值到mc，无论存不存在，成功set后永不过期
     */
    public boolean set(String key, long userId) {
        return mcc.set(key, userId);
    }

    /**
     * 获取值，mc返回null或转换为long失败时抛出异常
     */
    public long get(String key) throws Exception {
        Object value = mcc.get(key);
        if (value == null) {
            log.error("value of key(" + key + ") is null.");
            throw new Exception("value of key(" + key + ") is null.");
        }

        else {
            try {
                return ((Long) value).longValue();
            }
            catch (Exception e) {
                log.error("cannot convert value(" + value + ") from Object to Long.");
                throw new Exception("cannot convert value(" + value + ") from Object to Long.");
            }
        }
    }

    /**
     * replace一个值，只有当key存在时才替换，否则返回false，成功后永不过期
     */
    public boolean replace(String key, long userId) {
        return mcc.replace(key, userId);
    }

    /**
     * 删除k-v
     */
    public boolean delete(String key) {
        return mcc.delete(key);
    }
}
