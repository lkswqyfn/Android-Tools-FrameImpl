package com.example.frameimpl.activity;

import android.os.Bundle;

import com.example.frameimpl.R;
import com.example.frameimpl.fragment.Red;

public class Main extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initActivity() {
		setContentView(R.layout.activity_main);
		Red f = new Red();
		ChangeView(f, R.id.ooxx, false, false);
//		ShortCutUtil.deleteShortCut(getApplicationContext(), "FrameImpl", getClass());
		
//		ShortCutUtil.hideDesktopIcon(this);
		
//		ShortCutUtil.creatShortCut(getApplicationContext(), "iii", R.drawable.ic_launcher, getClass());
//		System.out.println(ShortCutUtil.hasShortcut(getApplicationContext(), "FrameImpl"));
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
//		ViewUtil.initViewByAnnotation(getWindow().getDecorView(), this);
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
