package com.example.demo.auth;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthIntercepter extends HandlerInterceptorAdapter {

	@Autowired
	private OnceAuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String appid = request.getParameter("appid");
		String noncestr = request.getParameter("noncestr");
		String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		return authService.validateSignature(appid, noncestr, timestamp, URLDecoder.decode(request.getRequestURI()), signature);
	}

}
