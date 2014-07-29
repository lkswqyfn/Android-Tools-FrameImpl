package com.example.frameimpl.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

abstract public class BaseFragment extends Fragment implements OnClickListener {
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initView();
		initData();
		initListener();
	}

	abstract public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	abstract public void initView();

	abstract public void initData();

	abstract public void initListener();
}
