package com.example.demo.jvm;

public class ReferenceTest {
	public ReferenceTest() {
	}

	// 消耗大量内存
	public static void drainMemory() {
		String[] array = new String[1024 * 10];
		for (int i = 0; i < 1024 * 10; i++) {
			for (int j = 'a'; j <= 'z'; j++) {
				array[i] += (char) j;
			}
		}
	}
}
