package com.example.demo.auth;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

	public static final String SIGN_STR = "appid={0}&secret={1}&noncestr={2}&timestamp={3}&url={4}";

	public static final String APP_ID_KEY = "appid";
	public static final String APP_SECRET_KEY = "secret";

	public static Map<String, Object> redis = new HashMap<>();

	public String getAccessToken(String appId, String appSecret) {
		//TODO
		String token = UUID.randomUUID().toString().replace("-", "");
		Map<String, String> json = new HashMap<>();
		json.put(APP_ID_KEY, appId);
		json.put(APP_SECRET_KEY, appSecret);
		redis.put(token, json);
		return token;
	}

	public boolean validateSignature(String accessToken, String noncestr, String timestamp, String url, String signature) {
		Map<String, String> appInfo = getAppInfo(accessToken);
		String appId = appInfo.get(APP_ID_KEY);
		String appSecret = appInfo.get(APP_SECRET_KEY);
		String sign = sign(appId, appSecret, noncestr, timestamp, url);
		return sign.equals(signature);
	}

	private String sign(String appId, String appSecret, String noncestr, String timestamp, String url) {
		//FIXME 空校验
		String signStr = MessageFormat.format(SIGN_STR, appId, appSecret, noncestr, timestamp, url);
		return AuthUtils.digestSignature(signStr);
	}

	private Map<String, String> getAppInfo(String accessToken) {
		if (!redis.containsKey(accessToken)) {
			throw new RuntimeException("还未登录");
		}
		return (Map<String, String>) redis.get(accessToken);
	}

}
