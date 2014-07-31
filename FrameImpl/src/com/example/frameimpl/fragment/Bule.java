package com.example.frameimpl.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.frameimpl.R;

public class Bule extends BaseFragment {

	private ImageView iv;

	@Override
	public void onClick(View v) {
		int type2 = Animation.RELATIVE_TO_SELF;
		Animation enterAnim = new TranslateAnimation(type2 , 1, type2, 0, type2, 0, type2, 0);
		enterAnim.setDuration(300);
//		
		Animation exitAnim = new TranslateAnimation(type2 , 0, type2, 1, type2, 0, type2, 0);
		exitAnim.setDuration(300);
		
		BBaiduMap f = new BBaiduMap();
//		Red f = new Red();
//		Green f = new Green();
		f.fragmentPendingTransition(enterAnim, exitAnim);
		getActivity().ChangeView(f, R.id.ooxx, true, true);
		
//		FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//		transaction.replace(R.id.ooxx, f);
//		transaction.addToBackStack(null);
//		transaction.setCustomAnimations(R.anim.anim_next_in, R.anim.anim_next_out);
//		transaction.commit();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		iv = new ImageView(getActivity());
		iv.setClickable(true);
		iv.setBackgroundColor(Color.BLUE);
		return iv;
	}

	@Override
	public void initView() {
	}

	@Override
	public void initData() {
	}

	@Override
	public void initListener() {
		iv.setOnClickListener(this);
	}

	@Override
	public boolean OnBackPressed() {
		System.out.println("bule");
		return super.OnBackPressed();
	}

}
