package com.example.frameimpl.util;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 全局UIHandler
 * 
 * @author wqYuan
 * 
 */
public class UIHandler extends Handler {
	private static UIHandler instance;
	private static List<Callback> mCallbacks = new ArrayList<Handler.Callback>();

	private UIHandler(Looper looper) {
		super(looper);
	}

	public static Handler addCallBack(Callback callback){
		if (instance == null) {
			instance = new UIHandler(Looper.getMainLooper());
		}
		mCallbacks.add(callback);
		return instance;
	}
	
	@Override
	public void handleMessage(Message msg) {
		for (Callback item : mCallbacks){
			item.handleMessage(msg);
		}
	}
	
	public void clear(){
		mCallbacks.clear();
	}
}
