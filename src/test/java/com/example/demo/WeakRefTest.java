package com.example.demo;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import com.example.demo.WeakRefTest.Apple;

public class WeakRefTest {

	private static ReferenceQueue<Apple> queue = new ReferenceQueue<>();

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/*System.out.println(System.getProperty("java.io.tmpdir"));
		WeakReference<String> sr = new WeakReference<String>(new String("hello"));
		System.out.println(sr.get());
		new Thread(() -> {
			System.gc(); //手工模拟JVM的gc进行垃圾回收
		}).start();
		Thread.sleep(5000);
		System.out.println(sr.get());*/

		PhantomReference<Apple> phantomReference = new PhantomReference<>(new Apple("abc"), queue);
		new Thread(() -> {

			try {
				while (true) {
					System.out.println("wait");
					Reference<? extends Apple> remove = queue.remove();
					System.out.println("remove");
					System.out.println(remove);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		Thread.sleep(2000);
		System.gc();
		System.out.println(phantomReference.get());

		/*AppleWeakRef ref = new AppleWeakRef(new Apple("abc"), queue);
		
		Thread.sleep(5000);
		System.gc();
		System.out.println(ref.get());*/
	}

	static class AppleWeakRef extends WeakReference<Apple> {

		public AppleWeakRef(Apple referent) {
			super(referent);
		}

		public AppleWeakRef(Apple referent, ReferenceQueue<? super Apple> q) {
			super(referent, q);
			// TODO Auto-generated constructor stub
		}

	}

	/**
	 * @author suyb
	 *
	 */
	static class Apple {
		private String name;

		public Apple(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Apple [name=" + name + "]";
		}

		@Override
		protected void finalize() throws Throwable {
			// TODO Auto-generated method stub
			super.finalize();
			System.out.println("finalize");
		}

	}

}
