package com.example.demo.file;

import java.io.File;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FileTracker {

	ReferenceQueue<Object> queue = new ReferenceQueue<>();

	private Set<Tracker> trackers = Collections.synchronizedSet(new HashSet<>());

	private Thread reaper;

	public void track(File file, Object marker) {
		if (reaper == null) {
			reaper = new Thread(() -> {
				try {
					while (true) {
						System.out.println("wait");
						Tracker remove = (Tracker) queue.remove();
						System.out.println("do delete");
						remove.delete();
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			reaper.start();
		}
		Tracker tracker = new Tracker(file, marker, queue);
		trackers.add(tracker);
		System.out.println(tracker);
	}

	static class Tracker extends WeakReference<Object> {

		String path;

		public Tracker(File file, Object referent, ReferenceQueue<? super Object> q) {
			super(referent, q);
			this.path = file.getPath();
		}

		public void delete() {
			new File(path).delete();
		}

	}
}
