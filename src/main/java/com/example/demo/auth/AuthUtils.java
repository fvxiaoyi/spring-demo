package com.example.demo.auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

public abstract class AuthUtils {

	public static String digestSignature(final String signStr) {
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(signStr.getBytes("UTF-8"));
			byte[] hash = crypt.digest();
			Formatter formatter = new Formatter();
			for (byte b : hash) {
				formatter.format("%02x", b);
			}
			String result = formatter.toString();
			formatter.close();
			return result;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String createNonceStr() {
		return UUID.randomUUID().toString();
	}

	public static String createTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

}
