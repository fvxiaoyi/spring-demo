package com.example.demo.auth;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class OnceAuthService {

	public static void main(String[] args) throws InterruptedException {
		String t1 = AuthUtils.createTimestamp();
		Thread.sleep(10000l);
		String t2 = AuthUtils.createTimestamp();
		System.out.println(Long.valueOf(t2) - Long.valueOf(t1));
	}

	public static final String SIGN_STR = "appid={0}&secret={1}&noncestr={2}&timestamp={3}&url={4}";

	public static final String APP_ID_KEY = "appid";
	public static final String APP_SECRET_KEY = "secret";

	public static Map<String, Object> redis = new HashMap<>();

	public boolean validateSignature(String appId, String noncestr, String timestamp, String url, String signature) {
		Map<String, String> appInfo = getAppInfo(appId);
		String appSecret = appInfo.get(APP_SECRET_KEY);
		String sign = sign(appId, appSecret, noncestr, timestamp, url);
		if (sign.equals(signature)) {
			return Long.valueOf(AuthUtils.createTimestamp()) - Long.valueOf(timestamp) <= 60;
		} else {
			throw new RuntimeException("resource auth fail");
		}
	}

	private String sign(String appId, String appSecret, String noncestr, String timestamp, String url) {
		//FIXME 空校验
		String signStr = MessageFormat.format(SIGN_STR, appId, appSecret, noncestr, timestamp, url);
		return AuthUtils.digestSignature(signStr);
	}

	private Map<String, String> getAppInfo(String appId) {
		if (!redis.containsKey(appId)) {
			if (appId.equals("123")) {
				Map<String, String> json = new HashMap<>();
				json.put(APP_ID_KEY, "123");
				json.put(APP_SECRET_KEY, "123");
				redis.put(APP_ID_KEY, json);
				return json;
			}
			throw new RuntimeException("未授权");
		}
		return (Map<String, String>) redis.get(appId);
	}
}
