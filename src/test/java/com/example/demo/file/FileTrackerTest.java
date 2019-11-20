package com.example.demo.file;

import java.io.File;
import java.io.IOException;

public class FileTrackerTest {

	public static void main(String[] args) throws InterruptedException, IOException {
		FileTracker fileTracker = new FileTracker();
		File file = new File("d:/test.txt");
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		fileTracker.track(file, new Object());
		Thread.sleep(5000l);
		System.out.println("gc");
		System.gc();
	}

	

}
