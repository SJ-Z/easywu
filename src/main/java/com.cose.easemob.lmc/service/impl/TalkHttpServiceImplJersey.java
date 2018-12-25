package com.cose.easemob.lmc.service.impl;

import com.cose.easemob.lmc.model.Authentic;
import com.cose.easemob.lmc.model.TalkNode;
import com.cose.easemob.lmc.service.TalkHttpService;

import java.io.File;
import java.util.Map;

/**
 * @ClassName: TalkHttpServiceImplJersey 
 * @Description: 另一个HTTP请求的实现
 * @author: zhang.xiaoming
 * @date: 2018年4月20日 下午2:42:17
 */
public class TalkHttpServiceImplJersey implements TalkHttpService {
	public TalkNode request(String url, int method, Object param, Authentic auth, String[][] field) throws Exception {
		// TODO 另一个http请求的实现
		return null;
	}

	public TalkNode upload(String url, File file, Authentic auth, String[][] equal) throws Exception {
		// TODO 另一个http请求的实现
		return null;
	}

	public void downLoad(String url, File file, Authentic auth, Map<String, String> header) throws Exception {
		// TODO 另一个http请求的实现
	}
}