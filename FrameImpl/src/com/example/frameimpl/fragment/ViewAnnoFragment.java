package com.example.frameimpl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frameimpl.R;
import com.example.frameimpl.annotation.ViewAnnotation;
import com.example.frameimpl.util.ViewUtil;

public class ViewAnnoFragment extends BaseFragment {
	@ViewAnnotation(R.id.test_1)
	TextView tv;
	@ViewAnnotation(R.id.test_2)
	public Button button;
	@ViewAnnotation(R.id.test_3)
	private ImageView imageView;

	@Override
	public void onClick(View v) {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return View.inflate(getActivity(), R.layout.fg_view_anno, null);
	}

	@Override
	public void initView() {
		ViewUtil.initViewByAnnotation(getView(), this);
	}

	@Override
	public void initData() {
		tv.setText("oooxxx");
		button.setText("点我");
		imageView.setImageResource(R.drawable.smssdk_sharesdk_icon);
	}

	@Override
	public void initListener() {
	}

}
