package com.example.frameimpl.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.frameimpl.control.SensorManagerImpl;
import com.example.frameimpl.control.SensorManagerImpl.OnShareListener;

public class SnakeFragment extends BaseFragment {

	private Vibrator mVibrator;
	private SensorManagerImpl sensorManagerImpl;

	@Override
	public void onClick(View v) {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ImageView iv = new ImageView(getActivity());
		iv.setClickable(true);
		iv.setBackgroundColor(Color.GREEN);
		return iv;
	}

	@Override
	public void initView() {
		mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		sensorManagerImpl = new SensorManagerImpl(getActivity());
	}

	@Override
	public void initData() {
		sensorManagerImpl.start();
	}

	@Override
	public void initListener() {
		sensorManagerImpl.setOnShareListener(new OnShareListener() {
			@Override
			public void onShare() {
//				startVibrato();
			}
		});
	}

	public void startVibrato(){       
        mVibrator.vibrate( new long[]{500,200,500,200}, -1);
    }  
      
}
