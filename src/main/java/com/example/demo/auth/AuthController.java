package com.example.demo.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping
	public Map<String, Object> token(@RequestBody Map<String, Object> params) {
		String accessToken = authService.getAccessToken(params.get("appid").toString(), params.get("secret").toString());
		Map<String, Object> result = new HashMap<>();
		result.put("token", accessToken);
		return result;
	}
}
