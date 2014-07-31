package com.example.frameimpl.control;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;

public class SensorManagerImpl implements SensorEventListener2 {
	private static final long UPTATE_INTERVAL_TIME = 70;
	private static final int SPEED_SHRESHOLD  = 300;
	private Context mContext;
	private SensorManager mSensorManager;
	private long mLastUpdateTime;
	private float mLastX;
	private float mLastY;
	private OnShareListener mListener;

	public SensorManagerImpl(Context context) {
		mContext = context;
		mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
	}

	public void start(){
		Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (sensor != null){
			mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	public void stop(){
			mSensorManager.unregisterListener(this);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		long currentUpdateTime = System.currentTimeMillis();
		long timeInterval = currentUpdateTime - mLastUpdateTime; 
		if (timeInterval < UPTATE_INTERVAL_TIME) {
			return;
		}
		mLastUpdateTime = currentUpdateTime;
		
		float x = event.values[0];
		float y = event.values[1];
		
		float dx = x - mLastX;
		float dy = y - mLastY;
		
		mLastX = x;
		mLastX = y;
		
		double sqrt = Math.sqrt(dx*dx + dy*dy) / 70 * 1000;
		if (sqrt > SPEED_SHRESHOLD && mListener != null){
			mListener.onShare();
		}
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	@Override
	public void onFlushCompleted(Sensor sensor) {
	}

	public void setOnShareListener(OnShareListener listener){
		mListener = listener;
	}
	
	public interface OnShareListener{
		public void onShare();
	}
}
