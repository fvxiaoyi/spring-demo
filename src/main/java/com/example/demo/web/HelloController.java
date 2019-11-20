package com.example.demo.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jpa.CatalogService;

@RestController
public abstract class HelloController {

	private HelloService helloService;

	@Autowired
	private CatalogService catalogService;

	@GetMapping("/catalog")
	public String catalog() {
		catalogService.persist();
		return "123";
	}

	@GetMapping("/vedio")
	public void vedio(HttpServletResponse resp) throws IOException {
		FileSystemResource res = new FileSystemResource("d:/test/school.mp4");
		try (InputStream ins = res.getInputStream(); OutputStream out = resp.getOutputStream()) {
			IOUtils.copy(ins, out);
		}
	}

	@GetMapping("/data")
	public Map<String, Object> data() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("name", "abc");
		result.put("num", "100");
		return result;
	}

	@GetMapping("/hello")
	public String hello() {
		return getHelloService().toString();
	}

	@Lookup
	public HelloService getHelloService() {
		return null;
	}

	@PostMapping("/numTest")
	public String numTest(@RequestBody NumTestVO params) {
		return params.getNum().toString();
	}

}
