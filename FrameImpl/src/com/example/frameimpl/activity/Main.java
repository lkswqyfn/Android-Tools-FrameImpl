package com.example.frameimpl.activity;

import android.os.Bundle;

import com.example.frameimpl.R;
import com.example.frameimpl.fragment.Green;
import com.example.frameimpl.test.LockDao;

public class Main extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initActivity() {
		setContentView(R.layout.activity_main);
		Green f = new Green();
		ChangeView(f, R.id.ooxx, false, false);
		new Thread(){
			public void run() {
				LockDao.getInstance(getBaseContext()).write();
			};
		}.start();
		new Thread(){
			public void run() {
				LockDao.getInstance(getBaseContext()).write();
			};
		}.start();
		new Thread(){
			public void run() {
				LockDao.getInstance(getBaseContext()).read(1);
			};
		}.start();
		new Thread(){
			public void run() {
				LockDao.getInstance(getBaseContext()).read(2);
			};
		}.start();
		new Thread(){
			public void run() {
				LockDao.getInstance(getBaseContext()).read(3);
			};
		}.start();
/*		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ooxx, f);
		transaction.commit();*/
		/*int type2 = Animation.RELATIVE_TO_SELF;
		Animation enterAnim = new TranslateAnimation(type2 , 1, type2, 0, type2, 0, type2, 0);
		enterAnim.setDuration(300);
		
		Animation exitAnim = new TranslateAnimation(type2 , 0, type2, 1, type2, 0, type2, 0);
		exitAnim.setDuration(300);
		fragmentPendingTransition(enterAnim, exitAnim);*/
	}

	@Override
	public void initView() {
	}

	@Override
	public void initData() {
	}

	@Override
	public void initListener() {
	}

	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
	}
}
