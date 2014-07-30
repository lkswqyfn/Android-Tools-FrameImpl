package com.example.frameimpl.fragment;

import java.util.HashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.example.frameimpl.R;

public class Red extends BaseFragment {

	@Override
	public void onClick(View v) {
		if (v.getId() == 33) {

			Bule f = new Bule();
			getActivity().ChangeView(f, R.id.ooxx, false, true);
		} else {
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// 解析注册结果
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
						String country = (String) phoneMap.get("country");
						String phone = (String) phoneMap.get("phone");

						// 提交用户信息
						registerUser(country, phone);
					}
				}
			});
			registerPage.show(getActivity());
		}
	}

	protected void registerUser(String country, String phone) {
		System.out.println("country:" + country + "-phone:" + phone);
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
		ll.addView(iv, p);
		iv.setOnClickListener(this);

		iv = new ImageView(getActivity());
		iv.setId(34);
		iv.setClickable(true);
		iv.setBackgroundColor(Color.BLACK);
		p = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		ll.addView(iv, p);
		iv.setOnClickListener(this);

		return ll;
	}

	@Override
	public void initView() {
		// ImageView iv = (ImageView) findViewById(33);
		// iv.setBackgroundColor(Color.GREEN);
	}

	@Override
	public void initData() {
	}

	@Override
	public void initListener() {
		// iv.setOnClickListener(this);
	}

	@Override
	public boolean OnBackPressed() {
		System.out.println("Red");
		return super.OnBackPressed();
	}

}
