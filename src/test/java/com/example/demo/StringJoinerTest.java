package com.example.demo;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringJoinerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str1 = "1";
		String str2 = String.valueOf(1).intern();
		System.out.println(str1 == str2);
		System.out.println(Arrays.asList("", "1", "2", "3").stream().map(m -> m).collect(Collectors.joining(",")));
	}

}
