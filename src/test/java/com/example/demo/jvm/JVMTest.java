package com.example.demo.jvm;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import org.apache.commons.io.FileCleaningTracker;

import com.example.demo.file.FileTracker;

public class JVMTest {
	public static void main(String[] args) throws InterruptedException, IOException {

		A a = new A();
		WeakReference<A> r1 = new WeakReference<A>(a);
		WeakReference<A> r2 = new WeakReference<A>(new A());
		System.out.println(r1.get());
		System.out.println(r2.get());
		System.gc();
		Thread.sleep(2000l);
		System.out.println(r1.get());
		System.out.println(r2.get());

		/*
		 * method(); System.gc(); Thread.sleep(5000l);
		 */
	}

	public static void method() throws InterruptedException, IOException {
		FileTracker tc = new FileTracker();
//		FileCleaningTracker tc = new FileCleaningTracker();
		File f = new File("e:/temp/test.txt");
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();
		A a = new A();
		tc.track(f, a);
		Thread.sleep(5000l);
	}
}
