package com.example.demo;

import java.util.HashMap;

public class HashMapTest {

	public static void main(String[] args) {
		

		Long times1 = 0L;
		for (int j1 = 0; j1 < 10; j1++) {
			HashMap<String, String> map3 = new HashMap<>();
			long startMapTime2 = System.currentTimeMillis();
			for (int i = 0; i < 1000000000; i++) {
				map3.put("map", "map");
			}
			long endMapTime2 = System.currentTimeMillis();
			Long time = endMapTime2 - startMapTime2;
			times1 = times1 + time;
			System.out.println("没加初始容量的时间:" + j1 + "次" + time);
		}
		System.out.println("没加初始容量的平均时间:" + (times1 / 10));
		Long times = 0L;
		for (int j = 0; j < 10; j++) {
			HashMap<String, String> map2 = new HashMap<>(500);
			long startMapTime2 = System.currentTimeMillis();
			for (int i = 0; i < 1000000000; i++) {
				map2.put("map", "map");
			}
			long endMapTime2 = System.currentTimeMillis();
			Long time = endMapTime2 - startMapTime2;
			times = times + time;
			System.out.println("加了初始容量的时间:" + j + "次" + time);
		}
		System.out.println("加了初始容量的平均时间:" + (times / 10));

		System.out.println("=======================================");
	}

}
