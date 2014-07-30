package com.example.frameimpl;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.example.frameimpl.activity.Main;
import com.example.frameimpl.util.LogUtils;
import com.example.frameimpl.util.MemoryUtils;

public class ApplicationImpl extends Application implements UncaughtExceptionHandler {
	private static ApplicationImpl instance = null;

	public static ApplicationImpl getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		Thread.setDefaultUncaughtExceptionHandler(this);
		super.onCreate();
		instance = this;
		initLog();
	}

	private void initLog() {
		File logPath = new File(Environment.getExternalStorageDirectory(), "/frameimpl/log.txt");
		if (!logPath.getParentFile().exists()) {
			logPath.getParentFile().mkdirs();
		}
		if (!logPath.exists()) {
			try {
				logPath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LogUtils.initLogUtils(true, true, logPath.getAbsolutePath());
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		LogUtils.logExceptionInfo(ex);
		LogUtils.pa("MemInfo", MemoryUtils.getMemoryInfo());
		LogUtils.uninitLogUtils();

		Intent localIntent = new Intent(this, Main.class);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(localIntent);

		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

	public Object getSystem_Service(String service) {
		return getSystemService(service);
	}

	public Context getContext() {
		return getApplicationContext();
	}
}
