package com.example.frameimpl.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.example.frameimpl.R;

public class Red extends BaseFragment {

	@Override
	public void onClick(View v) {
		if (v.getId() == 33){
			
			Bule f = new Bule();
			getActivity().ChangeView(f, R.id.ooxx, false, true);
		}else{
			
//			int i = 1/0;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ImageView iv = new ImageView(getActivity());
		iv.setId(33);
		iv.setClickable(true);
		iv.setBackgroundColor(Color.RED);
		LayoutParams p = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		ll.addView(iv,p );
		iv.setOnClickListener(this);
		
		iv = new ImageView(getActivity());
		iv.setId(34);
		iv.setClickable(true);
		iv.setBackgroundColor(Color.BLACK);
		 p = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		ll.addView(iv,p );
		iv.setOnClickListener(this);
		
		
		return ll;
	}
	
	@Override
	public void initView() {
//		ImageView iv = (ImageView) findViewById(33);
//		iv.setBackgroundColor(Color.GREEN);
	}

	@Override
	public void initData() {
	}

	@Override
	public void initListener() {
//		iv.setOnClickListener(this);
	}

	@Override
	public boolean OnBackPressed() {
		System.out.println("Red");
		return super.OnBackPressed();
	}


}
