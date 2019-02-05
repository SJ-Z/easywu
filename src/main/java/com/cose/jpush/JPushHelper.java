package com.cose.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JPushHelper {

    private static final Logger log = LoggerFactory.getLogger(JPushHelper.class);
    private static String MASTER_SECRET = "fe571ecd5820757b66d23835";
    private static String APP_KEY = "aa2515bb1d4b5c159b74158a";

    // 通知的类型
    public static int TYPE_GOODS_COMMENT = 0;
    public static int TYPE_GOODS_REPLY = 1;
    public static int TYPE_NEW_GOODS_ORDER = 2;
    public static int TYPE_CONFIRM_GOODS_ORDER = 3;
    public static int TYPE_REFUSE_GOODS_ORDER = 4;

    /**
     * 极光推送，外部调用方法
     */
    public static void jPushNotification(String alias, String alert, String g_id, Date createTime, int type) {
        PushResult result = push(alias, alert, g_id, createTime, type);
        if (result != null && result.isResultOK()) {
            log.info("对别名" + alias + "的信息推送成功！");
        } else {
            log.info("对别名" + alias + "的信息推送失败！");
        }
    }

    /**
     * 极光推送商品下订单通知，外部调用方法
     */
    public static void jPushNotification(String alias, String alert, String g_id, String g_buyer_id, Date createTime, int type) {
        PushResult result = push(alias, alert, g_id, g_buyer_id, createTime, type);
        if (result != null && result.isResultOK()) {
            log.info("对别名" + alias + "的信息推送成功！");
        } else {
            log.info("对别名" + alias + "的信息推送失败！");
        }
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param alias
     * @param alert
     * @param g_id
     * @param createTime
     * @param type
     * @return PushPayload
     */
    private static PushPayload buildPushObject_android_alias_alert(String alias, String alert, String g_id, Date createTime, int type) {
        if (type == JPushHelper.TYPE_CONFIRM_GOODS_ORDER) {
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.alias(alias))
                    .setNotification(Notification.newBuilder()
                            .setAlert(alert)
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .addExtra("ConfirmGoodsOrderType", true)
                                    .addExtra("goods_id", g_id)
                                    .addExtra("time", createTime.getTime())
                                    .addExtra("type", type).build())
                            .build())
                    .build();
        } else if (type == JPushHelper.TYPE_REFUSE_GOODS_ORDER) {
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.alias(alias))
                    .setNotification(Notification.newBuilder()
                            .setAlert(alert)
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .addExtra("RefuseGoodsOrderType", true)
                                    .addExtra("goods_id", g_id)
                                    .addExtra("time", createTime.getTime())
                                    .addExtra("type", type).build())
                            .build())
                    .build();
        } else {
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.alias(alias))
                    .setNotification(Notification.newBuilder()
                            .setAlert(alert)
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .addExtra("GoodsChatType", true)
                                    .addExtra("goods_id", g_id)
                                    .addExtra("time", createTime.getTime())
                                    .addExtra("type", type).build())
                            .build())
                    .build();
        }
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param alias
     * @param alert
     * @param g_id
     * @param createTime
     * @param type
     * @return PushPayload
     */
    private static PushPayload buildPushObject_android_alias_alert(String alias, String alert, String g_id, String g_buyer_id, Date createTime, int type) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("NewGoodsOrderType", true)
                                .addExtra("goods_id", g_id)
                                .addExtra("goods_buyer_id", g_buyer_id)
                                .addExtra("time", createTime.getTime())
                                .addExtra("type", type).build())
                        .build())
                .build();
    }

    /**
     * 极光推送方法(采用java SDK)
     * @param alias
     * @param alert
     * @param g_id
     * @param createTime
     * @param type
     * @return PushResult
     */
    private static PushResult push(String alias, String alert, String g_id, Date createTime, int type) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        PushPayload payload = buildPushObject_android_alias_alert(alias, alert, g_id, createTime, type);
        try {
            return jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }

    /**
     * 极光推送方法(采用java SDK)
     * @param alias
     * @param alert
     * @param g_id
     * @param createTime
     * @param type
     * @return PushResult
     */
    private static PushResult push(String alias, String alert, String g_id, String g_buyer_id, Date createTime, int type) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        PushPayload payload = buildPushObject_android_alias_alert(alias, alert, g_id, g_buyer_id, createTime, type);
        try {
            return jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }

}
