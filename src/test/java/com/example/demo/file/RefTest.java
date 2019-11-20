package com.example.demo.file;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.file.FileTracker.Tracker;
import com.example.demo.file.RefTest.PhRef;

public class RefTest {
	static ReferenceQueue<Object> queue = new ReferenceQueue<>();
	static List<Object> list = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException {

		List<Object> list = new ArrayList<>();
		//		list.add();

		new Thread(() -> {
			try {
				while (true) {
					System.out.println("wait");
					System.out.println(queue.remove());
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {

				Thread.sleep(2000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("exec method");
			method();
		}).start();
		//		method();
		Thread.sleep(5000l);
		//		System.out.println("exec gc");
		//		System.gc();
	}

	static void method() {
		PhRef phRef = new PhRef(new Entity("abc"), queue);
		phRef = null;
	}

	static class PhRef extends PhantomReference<Entity> {

		public PhRef(Entity referent, ReferenceQueue<? super Entity> q) {
			super(referent, q);
		}

	}
}
