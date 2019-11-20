package com.example.demo.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		new Server().start();
	}

	public void start() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(1234);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				Socket accept = ss.accept();
				InputStream inputStream = accept.getInputStream();
				BufferedInputStream bi = new BufferedInputStream(inputStream);
//				OutputStream outputStream = accept.getOutputStream();
				byte[] buffer = new byte[2048];
				inputStream.read(buffer);
				System.out.print(new String(buffer));
				accept.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
