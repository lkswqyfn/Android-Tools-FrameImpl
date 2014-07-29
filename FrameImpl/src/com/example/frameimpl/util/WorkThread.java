package com.example.frameimpl.util;

import android.os.HandlerThread;

public class WorkThread extends HandlerThread {

	private static WorkThread instance = null;

	private WorkThread() {
		super("mWork");
		start();
	}

	public static WorkThread getInstance() {
		if (instance == null) {
			instance = new WorkThread();
		}
		return instance;
	}

	@Override
	public void run() {
		super.run();
		instance = null;
	}
}
