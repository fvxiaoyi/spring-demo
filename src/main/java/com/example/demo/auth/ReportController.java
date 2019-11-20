package com.example.demo.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

	@GetMapping("/report/{companyName}")
	public String gain(@PathVariable String companyName, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String uri = httpServletRequest.getRequestURI();
		return companyName;
	}
	/*, @RequestParam String token, @RequestParam String noncestr,
	@RequestParam String timestamp, @RequestParam String signature,*/
}
