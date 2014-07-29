package com.example.frameimpl.util;

import android.os.Handler;
import android.os.Looper;
/**
 * 全局UIHandler
 * @author wqYuan
 *
 */
public class UIHandler extends Handler {
	private static UIHandler instance;

	private UIHandler(Looper looper) {
		super(looper);
	}

	public static UIHandler getInstance(){
		if (instance == null){
			instance = new UIHandler(Looper.getMainLooper());
		}
		return instance;
	}
}
