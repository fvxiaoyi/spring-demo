package com.example.demo.jvm;

import java.lang.ref.WeakReference;

public class JVMTest1 {

	public static void main(String[] args) {
		A a = new A();
		WeakReference<A> r1 = new WeakReference<A>(a);
		WeakReference<A> r2 = new WeakReference<A>(new A());
		try {
			Thread.sleep(6000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(r1.get());
		System.out.println(r2.get());
	}

}
