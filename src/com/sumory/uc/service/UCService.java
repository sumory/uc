package com.sumory.uc.service;

import java.util.Date;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sumory.uc.config.ConfigUtils;
import com.sumory.uc.id.IdGenerator;
import com.sumory.uc.passport.IMc;
import com.sumory.uc.passport.PassportMc;
import com.uucampus.sns.gen.uc.exception.IdErrorCode;
import com.uucampus.sns.gen.uc.exception.IdException;
import com.uucampus.sns.gen.uc.exception.PassportErrorCode;
import com.uucampus.sns.gen.uc.exception.PassportException;
import com.uucampus.sns.gen.uc.service.UC;

public class UCService implements UC.Iface {

    private final static Logger logger = LoggerFactory.getLogger(UCService.class);
    private final static String USERNAME_PREFIX = ConfigUtils.getPassportConfig().get("username-prefix").toString();// 如：u_
    private final static String EMAIL_PREFIX = ConfigUtils.getPassportConfig().get("email-prefix").toString();// 如：e_

    private IMc iMc;

    public UCService() {
        iMc = new PassportMc();
    }

    @Override
    public long genUserId() throws IdException, TException {
        try {
            return IdGenerator.generateUserId();
        }
        catch (Exception e) {
            logger.error("Generate UserId error:" + e.getMessage());
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genGroupId() throws IdException, TException {
        try {
            return IdGenerator.generateGroupId();
        }
        catch (Exception e) {
            logger.error("Generate GroupId error:" + e.getMessage());
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genFeedId() throws IdException, TException {
        try {
            return IdGenerator.generateFeedId();
        }
        catch (Exception e) {
            logger.error("Generate FeedId error:" + e.getMessage());
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genGroupFeedId() throws IdException, TException {
        try {
            return IdGenerator.generateGroupFeedId();
        }
        catch (Exception e) {
            logger.error("Generate GroupFeedId error:" + e.getMessage());
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genCommentId() throws IdException, TException {
        try {
            return IdGenerator.generateCommentId();
        }
        catch (Exception e) {
            logger.error("Generate CommentId error:" + e.getMessage());
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genAppId() throws IdException, TException {
        try {
            return IdGenerator.generateAppId();
        }
        catch (Exception e) {
            logger.error("Generate AppId error:" + e.getMessage());
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genStoreId() throws IdException, TException {
        try {
            return IdGenerator.generateStoreId();
        }
        catch (Exception e) {
            logger.error("Generate StoreId error:" + e.getMessage());
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genBroadcastId() throws IdException, TException {
        try {
            return IdGenerator.generateBroadcastId();
        }
        catch (Exception e) {
            logger.error("Generate BroadcastId error:" + e.getMessage());
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genContactTagId() throws IdException, TException {
        try {
            return IdGenerator.generateContactTagId();
        }
        catch (Exception e) {
            logger.error("Generate ContactTagId error:" + e.getMessage());
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genFileTagId() throws IdException, TException {
        try {
            return IdGenerator.generateFileTagId();
        }
        catch (Exception e) {
            logger.error("generate fileTagId error:", e);
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genMsgId() throws IdException, TException {
        try {
            return IdGenerator.generateMsgId();
        }
        catch (Exception e) {
            logger.error("generate msg error:", e);
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genCalendarId() throws IdException, TException {
        try {
            return IdGenerator.generateCalendarId();
        }
        catch (Exception e) {
            logger.error("generate calendar error:", e);
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genAnswerId() throws IdException, TException {
        try {
            return IdGenerator.generateAnswerId();
        }
        catch (Exception e) {
            logger.error("generate answer error:", e);
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genAssignmentId() throws IdException, TException {
        try {
            return IdGenerator.generateAssignmentId();
        }
        catch (Exception e) {
            logger.error("generate assignment error:", e);
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genNoticeId() throws IdException, TException {
        try {
            return IdGenerator.generateNoticeId();
        }
        catch (Exception e) {
            logger.error("generate notice error:", e);
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    @Override
    public long genJudgementId() throws IdException, TException {
        try {
            return IdGenerator.generateJudgementId();
        }
        catch (Exception e) {
            logger.error("generate Judgement error:", e);
            throw new IdException(IdErrorCode.UNKNOWN);
        }
    }

    /**
     * 判断userName是否存在
     */
    @Override
    public boolean validateUserName(String userName) throws PassportException, TException {
        if (userName == null || "".equals(userName)) {
            throw new PassportException(PassportErrorCode.ILLEGAL_USERNAME);
        }
        return iMc.keyExists(USERNAME_PREFIX + userName);
    }

    /**
     * 判断email是否存在
     */
    @Override
    public boolean validateEmail(String email) throws PassportException, TException {
        if (email == null || "".equals(email)) {
            throw new PassportException(PassportErrorCode.ILLEGAL_EMAIL);
        }
        return iMc.keyExists(EMAIL_PREFIX + email);
    }

    /**
     * add一个值到mc，带默认过期时间expiry(由配置文件得到)
     * 
     * @param userName
     *            key
     * @param userId
     *            value
     * @param expiry
     *            过期时间，毫秒级别，示例：10分钟可以为System.currentTimeInMillis()+10*60*1000,
     *            也可以为10*60*1000，由于服务器时间和客户端时间可能不一致，建议使用后者以服务器时间为基准
     * @return
     */
    @Override
    public boolean addUserName(String userName, long id) throws PassportException, TException {
        if (userName == null || "".equals(userName)) {
            throw new PassportException(PassportErrorCode.ILLEGAL_USERNAME);
        }
        try {
            return iMc.add(USERNAME_PREFIX + userName, id, new Date(Integer.valueOf(ConfigUtils.getPassportConfig().get("username-expiry").toString())));
        }
        catch (NumberFormatException e) {
            logger.error("cannot parse [" + ConfigUtils.getPassportConfig().get("username-expiry").toString() + "] to Integer.");
            return false;
        }
        catch (Exception e) {
            logger.error("addUserName [userName:" + userName + ",userId:" + id + "] error", e);
            return false;
        }
    }

    /**
     * 在插入mysql成功后将此userName-userId键值对设置为永不过期
     */
    @Override
    public boolean freezeUserName(String userName) throws PassportException, TException {
        if (userName == null || "".equals(userName)) {
            throw new PassportException(PassportErrorCode.ILLEGAL_USERNAME);
        }
        try {
            long userId = iMc.get(USERNAME_PREFIX + userName);// 如果键值不存在了是不能replace的
            return iMc.replace(USERNAME_PREFIX + userName, userId);
        }
        catch (Exception e) {
            logger.error("freezeUserName [userName:" + userName + "] error", e);
            throw new PassportException(PassportErrorCode.USERID_FETCH_ERROR);
        }
    }

    /**
     * 通过userName获取userId，若无此userName则抛出异常
     * 
     * @param userName
     * @return
     * @throws PassportException
     * @throws TException
     */
    @Override
    public long getUserIdByUserName(String userName) throws PassportException, TException {
        if (userName == null || "".equals(userName)) {
            throw new PassportException(PassportErrorCode.ILLEGAL_USERNAME);
        }
        try {
            return iMc.get(USERNAME_PREFIX + userName);
        }
        catch (Exception e) {
            throw new PassportException(PassportErrorCode.USERID_FETCH_ERROR);
        }
    }

    /**
     * add一个值到mc，带默认过期时间expiry(由配置文件得到)
     * 
     * @param email
     *            key
     * @param userId
     *            value
     * @param expiry
     *            过期时间，毫秒级别，示例：10分钟可以为System.currentTimeInMillis()+10*60*1000,
     *            也可以为10*60*1000，由于服务器时间和客户端时间可能不一致，建议使用后者以服务器时间为基准
     * @return
     */
    @Override
    public boolean addEmail(String email, long userId) throws PassportException, TException {
        if (email == null || "".equals(email)) {
            throw new PassportException(PassportErrorCode.ILLEGAL_EMAIL);
        }
        try {
            return iMc.add(EMAIL_PREFIX + email, userId, new Date(Integer.valueOf(ConfigUtils.getPassportConfig().get("email-expiry").toString())));
        }
        catch (NumberFormatException e) {
            logger.error("cannot parse [" + ConfigUtils.getPassportConfig().get("email-expiry").toString() + "] to Integer.");
            return false;
        }
        catch (Exception e) {
            logger.error("addEmail [userName:" + email + ",userId:" + userId + "] error", e);
            return false;
        }
    }

    @Override
    public boolean freezeEmail(String email) throws PassportException, TException {
        if (email == null || "".equals(email)) {
            throw new PassportException(PassportErrorCode.ILLEGAL_EMAIL);
        }
        try {
            long userId = iMc.get(EMAIL_PREFIX + email);
            return iMc.replace(EMAIL_PREFIX + email, userId);
        }
        catch (Exception e) {
            logger.error("freezeEmail [email:" + email + "] error", e);
            throw new PassportException(PassportErrorCode.USERID_FETCH_ERROR);
        }
    }

    @Override
    public long getUserIdByEmail(String email) throws PassportException, TException {
        if (email == null || "".equals(email)) {
            throw new PassportException(PassportErrorCode.ILLEGAL_EMAIL);
        }
        try {
            return iMc.get(EMAIL_PREFIX + email);
        }
        catch (Exception e) {
            throw new PassportException(PassportErrorCode.USERID_FETCH_ERROR);
        }
    }

}
