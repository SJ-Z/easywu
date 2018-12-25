package com.cose.easemob.lmc.tool;

/**
 * @ClassName: _Global 
 * @Description: 环信开发配置文件
 * @author: zhang.xiaoming
 * @date: 2018年4月20日 下午2:35:01
 */
public class _Global {
	public static int APP_PAGE_SIZE = 5;
	/** 以下配置value需要配置    */
	public static String APP_KEY = "1189181014177255#easywu";
	public static String APP_CLIENT_ID = "YXA67_BAYAZ5EemXcgtEdliScg";
	public static String APP_CLIENT_SECRET = "YXA616PsqEmcac0BP3oF_awZ4xjLX5k";
	/** 以上配置value需要配置    */
	public static final int HTTP_METHOD_GET = 1;
	public static final int HTTP_METHOD_POST = 2;
	public static final int HTTP_METHOD_PUT = 3;
	public static final int HTTP_METHOD_DELETE = 4;
	public static final String URL_HOST = "http://a1.easemob.com/" + APP_KEY.replace("#", "/") + "/";
	public static final String URR_TOKEN = URL_HOST + "token";
	public static final String URL_CHAT = URL_HOST + "chatmessages";
	public static final String URL_GROUP = URL_HOST + "chatgroups";
	public static final String URL_FILE = URL_HOST + "chatfiles";
	public static final String URL_ROOM = URL_HOST + "chatrooms";
	public static final String URL_MESSAGES = URL_HOST + "messages";
	public static final String URL_USER = URL_HOST + "users";
}