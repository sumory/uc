package com.sumory.uc.passport;

import java.util.Date;

/**
 * Memcache接口
 * 
 * @author sumory.wu
 * @date 2012-2-24 下午4:12:56
 */
public interface IMc {

    /**
     * 判断key是否存在
     * 
     * @param key
     * @return
     */
    public boolean keyExists(String key);

    /**
     * add一个值到mc，成功add后永不过期
     * 
     * @param key
     * @param userId
     * @return
     */
    public boolean add(String key, long userId);

    /**
     * add一个值到mc，带过期时间expiry
     * 
     * @param key key
     * @param userId value
     * @param expiry 过期时间，毫秒级别，示例：10分钟可以为System.currentTimeInMillis()+10*60*1000, 也可以为10*60*1000，由于服务器时间和客户端时间可能不一致，建议使用后者以服务器时间为基准
     * @return
     */
    public boolean add(String key, long userId, Date expiry);

    /**
     * 储存此数据，无论何时
     * 
     * @param key
     * @param userId
     * @return
     */
    public boolean set(String key, long userId);

    /**
     * 获取值，mc返回null或转换为long失败时抛出异常
     * 
     * @param key
     * @return
     */
    public long get(String key) throws Exception;

    /**
     * 储存此数据，只在服务器*曾*保留此键值的数据时
     * 
     * @param key
     * @param userId
     * @return
     */
    public boolean replace(String key, long userId);

    /**
     * 删除key的键值对
     * 
     * @param key
     * @return
     */
    public boolean delete(String key);

}
