package com.example.frameimpl.util;

import android.os.Handler;
import android.os.Looper;
/**
 * 全局子线程Handler
 * @author wqYuan
 *
 */
public class WorkHandler extends Handler {
	private static WorkHandler instance;

	private WorkHandler(Looper looper) {
		super(looper);
	}

	public static WorkHandler getInstance() {
		if (instance == null) {
			instance = new WorkHandler(WorkThread.getInstance().getLooper());
		}
		return instance;
	}
}
