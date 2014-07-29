package com.example.frameimpl.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
/**
 * 单例Toast
 * @author wqYuan
 *
 */
public class ToastUtil {
	private Toast toast;
	private Handler mHandler;
	
	private static ToastUtil instance;

	public static ToastUtil getInstance(Context context) {
		if (instance == null){
			instance = new ToastUtil(context);
		}
		return instance;
	}

	private ToastUtil(final Context context) {
		mHandler = new Handler(Looper.getMainLooper());
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				toast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
			}
		});
	}

	public void showMsg(final String msg) {
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				toast.setText(msg);
				toast.show();
			}
		});
	}

}
