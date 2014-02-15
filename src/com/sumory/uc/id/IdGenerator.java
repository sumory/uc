package com.sumory.uc.id;

import java.util.Map;

import com.sumory.uc.config.ConfigUtils;

/**
 * 全局id生成器
 * 
 * @author sumory.wu
 * 
 */
public class IdGenerator {

    private static Map<String, String> idGeneratorConfig = ConfigUtils.getIdGeneratorConfig();
    private static IdWorker groupIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-group-worker-id")));
    private static IdWorker userIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-user-worker-id")));
    private static IdWorker feedIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-feed-worker-id")));
    private static IdWorker commentIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-comment-worker-id")));
    private static IdWorker appIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-app-worker-id")));
    private static IdWorker storeIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-store-worker-id")));
    private static IdWorker broadcastIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-broadcast-worker-id")));
    private static IdWorker contactTagIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-contacttag-worker-id")));
    private static IdWorker fileTagIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-filetag-worker-id")));
    private static IdWorker msgIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-msg-worker-id")));
    private static IdWorker calendarIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-calendar-worker-id")));
    private static IdWorker assignmentIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-assignment-worker-id")));
    private static IdWorker answerIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-answer-worker-id")));
    private static IdWorker noticeIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-notice-worker-id")));
    private static IdWorker judgementIdWorker = new IdWorker(Integer.valueOf(idGeneratorConfig.get("default-judgement-worker-id")));

    public static long generateGroupId() throws Exception {
        return groupIdWorker.nextId();
    }

    public static long generateUserId() throws Exception {

        return userIdWorker.nextId();
    }

    public static long generateFeedId() throws Exception {
        return feedIdWorker.nextId();
    }

    public static long generateCommentId() throws Exception {
        return commentIdWorker.nextId();
    }

    public static long generateAppId() throws Exception {
        return appIdWorker.nextId();
    }

    public static long generateStoreId() throws Exception {
        return storeIdWorker.nextId();
    }

    public static long generateBroadcastId() throws Exception {
        return broadcastIdWorker.nextId();
    }

    public static long generateContactTagId() throws Exception {
        return contactTagIdWorker.nextId();
    }

    public static long generateGroupFeedId() throws Exception {
        return feedIdWorker.nextId();
    }

    public static long generateFileTagId() throws Exception {
        return fileTagIdWorker.nextId();
    }

    public static long generateMsgId() throws Exception {
        return msgIdWorker.nextId();
    }

    public static long generateCalendarId() throws Exception {
        return calendarIdWorker.nextId();
    }

    public static long generateAssignmentId() throws Exception {
        return assignmentIdWorker.nextId();
    }

    public static long generateAnswerId() throws Exception {
        return answerIdWorker.nextId();
    }

    public static long generateNoticeId() throws Exception {
        return noticeIdWorker.nextId();
    }

    public static long generateJudgementId() throws Exception {
        return judgementIdWorker.nextId();
    }
}
