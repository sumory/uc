package com.sumory.uc.init;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sumory.uc.config.ConfigUtils;
import com.sumory.uc.passport.IMc;
import com.sumory.uc.passport.PassportMc;
import com.uucampus.sns.commons.db.DbItem;
import com.uucampus.sns.commons.db.DbSelector;
import com.uucampus.sns.commons.handler.ListEntityHandler;
import com.uucampus.sns.commons.handler.SimpleWrapper;
import com.uucampus.sns.gen.core.type.User;

/**
 * 初始化UC需要的数据
 * 
 * @author sumory.wu
 * @date 2012-3-2 下午4:19:29
 */
public class InitData {

    private static final Logger logger = LoggerFactory.getLogger(InitData.class);
    private static IMc iMc;
    private static String USERNAME_PREFIX = ConfigUtils.getPassportConfig().get("username-prefix").toString();// 如：u_
    private static String EMAIL_PREFIX = ConfigUtils.getPassportConfig().get("email-prefix").toString();// 如：e_

    public static void main(String[] args) {
        iMc = new PassportMc();
        try {
            initMC();
        }
        catch (Exception e) {
            logger.error("初始化UC数据出错[重新初始化时请先清空Memcache内存]", e);
        }
    }

    /**
     * 初始化memcache需要两部分内容：<userName,userId>,<email,userId>
     * 
     * @throws Exception
     */
    public static void initMC() throws Exception {

        long userCount = 0;
        long t1 = System.currentTimeMillis();

        logger.info("--------------start initing memcache...");

        List<DbItem> dbItems = DbSelector.getAllDbItem("user");
        for (int i = 0; i < dbItems.size(); i++) {
            DbItem dbItem = dbItems.get(i);
            String sql = "select * from " + dbItem.getTable();

            QueryRunner qr = new QueryRunner(dbItem.getReadDataSource());
            try {
                List<User> userList = qr.query(sql, new ListEntityHandler<User>(UserWrapper.INSTANCE));
                logger.info("数据来自表：" + dbItem.getTable() + ",  个数：" + userList.size());
                userCount += userList.size();
                for (User u : userList) {
                    if (!iMc.add(USERNAME_PREFIX + u.getUsername(), u.getId())) {
                        throw new Exception("用户名冲突:" + u.getUsername());
                    }
                    if (!iMc.add(EMAIL_PREFIX + u.getEmail(), u.getId())) {
                        throw new Exception("Email冲突:" + u.getEmail());
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage());
                throw e;
            }
        }
        long t2 = System.currentTimeMillis();

        logger.info("用户总数：" + userCount + ",请检查memcache中数目是否为此两倍.");
        logger.info("--------------end initing memcache...初始化memcache耗时(ms)：" + (t2 - t1));
    }
}

class UserWrapper extends SimpleWrapper<User> {
    public static final UserWrapper INSTANCE = new UserWrapper();

    private UserWrapper() {
    }

    @Override
    public User phase(ResultSet rs) throws SQLException {
        User user = new User();
        HashSet<String> lables = getColumLables(rs);
        if (lables.contains("id"))
            user.setId(rs.getLong("id"));
        if (lables.contains("username"))
            user.setUsername(rs.getString("username"));
        if (lables.contains("email"))
            user.setEmail(rs.getString("email"));
        return user;
    }
}