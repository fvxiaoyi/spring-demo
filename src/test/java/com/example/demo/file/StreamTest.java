package com.example.demo.file;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class StreamTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		/*byte[] oneMb = new byte[5 * 1024 * 1024];
		ByteArrayInputStream ins = new ByteArrayInputStream(oneMb);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		IOUtils.copy(ins, bos);
		ByteArrayInputStream ins1 = new ByteArrayInputStream(bos.toByteArray());
		Thread.sleep(2000000000000l);*/
		//		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		String url = "http://901818.jios.org:9011/pingtai/api/yigongcheng/report/scoreDetail?companyName=acb&name=%E4%B8%81%E5%A2%9E%E7%A5%A5&phone=18859208035&idNumber=350582198005280514&token=9f1673ea-e21d-4df6-90c3-4772c4e799ee";
		ResponseEntity<byte[]> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(new HttpHeaders()),
				byte[].class);
		System.out.println(response);
	}

}
