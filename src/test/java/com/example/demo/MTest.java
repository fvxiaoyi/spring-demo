package com.example.demo;

import org.assertj.core.util.Arrays;

public class MTest {
	public static void main(String[] args) {
		System.out.println(5 & 1);
		System.out.println(9 << 1);
		System.out.println(8 >> 2);
		System.out.println(19 >>> 1);
		System.out.println(3 ^ 5 ^ 3);
		int a = 345;
		int b = 9834;
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		System.out.println(a);
		System.out.println(b);
		Integer[] arr = Arrays.array(1, 3, 5, 1, 5, 9, 6, 9, 6, 8, 3);
		Arrays.asList(arr).forEach(System.out::println);
		System.out.println("========");
		popSort(arr);
		System.out.println("========");
		Arrays.asList(arr).forEach(System.out::println);
		System.out.println(1 >>> 2);
	}

	public static void popSort(Integer[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					arr[i] = arr[i] ^ arr[j];
					arr[j] = arr[i] ^ arr[j];
					arr[i] = arr[i] ^ arr[j];
				}
			}
		}
	}
}
