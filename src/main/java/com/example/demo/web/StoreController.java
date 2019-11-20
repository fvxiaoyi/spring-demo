package com.example.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/store")
public class StoreController {

	@PostMapping("/upload")
	public void upload(@RequestParam(name = "file") MultipartFile file, @RequestParam String name) {
		System.out.println(file);
	}

	@GetMapping("/uploadPage")
	public String uploadPage() {
		return "/uploadPage.html";
	}

	@GetMapping("/gc")
	public void gc() {
		System.gc();
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.io.tmpdir"));
	}
}
