package com.example.demo.file;

public class GCTest2 {
	public static void main(String[] args) throws InterruptedException {
		/*for (;;) {
			byte[] one = new byte[10 * 1024 * 1024];
			Thread.sleep(30000l);
			byte[] two = new byte[10 * 1024 * 1024];
		}*/
		Cat cat = new Cat("new");
		Cat cat1 = new Cat("new1");
		Cat cat2 = new Cat("new2");
	}

	static class Cat {
		final A c2 = new A("final");
		A c = new A("putong");
		static A c1 = new A("static");
		

		public Cat(String name) {
			super();
			System.out.println(name + "初始化");
		}

	}

	static class A {
		public A(String name) {
			System.out.println(name + "初始化");
		}
	}

	public static void method(boolean b) throws Exception {
		if (b) {
			throw new Exception();
		}

	}

	public static void method2() {
		throw new RuntimeException();
	}
}
