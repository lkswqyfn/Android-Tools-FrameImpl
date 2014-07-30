package com.example.frameimpl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import cn.smssdk.SMSSDK;
import cn.yicha.applite.appmgr.UninstallCollectionInfoService;

import com.baidu.mapapi.SDKInitializer;

public abstract class BaseActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initTheme();
		super.onCreate(savedInstanceState);

		initActivity();
		initView();
		initData();
		initListener();
		initServiceNReceiver();
	}

	/**
	 * 初始化主题
	 */
	private void initTheme() {
		getWindow().setWindowAnimations(android.R.style.Animation_Dialog);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(android.R.style.Theme_Light);
	}

	/**
	 * 初始化广播接收者和服务
	 */
	private void initServiceNReceiver() {
		Intent service = new Intent(this, UninstallCollectionInfoService.class);
		startService(service);
		SMSSDK.initSDK(this, "2803bd5b9e8c", "c478bc614fe371a4cfa978fa65921554");
		SDKInitializer.initialize(getApplicationContext());
	}

	/**
	 * 初始化界面
	 */
	public abstract void initActivity();

	/**
	 * 初始化View对象
	 */
	public abstract void initView();

	/**
	 * 初始化数据
	 */
	public abstract void initData();

	/**
	 * 初始化监听器适配器
	 */
	public abstract void initListener();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
}
