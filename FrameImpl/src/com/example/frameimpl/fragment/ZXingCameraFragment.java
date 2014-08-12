package com.example.frameimpl.fragment;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.example.frameimpl.ConstantValue;
import com.example.frameimpl.R;
import com.example.frameimpl.util.DensityUtil;
import com.example.frameimpl.util.UIHandler;
import com.example.frameimpl.util.zxing.AmbientLightManager;
import com.example.frameimpl.util.zxing.BeepManager;
import com.example.frameimpl.util.zxing.DecodeThread;
import com.example.frameimpl.util.zxing.InactivityTimer;
import com.example.frameimpl.util.zxing.camera.CameraManager;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

public class ZXingCameraFragment extends BaseFragment implements SurfaceHolder.Callback {
	private static final String TAG = "ZXingCameraFragment";
	private Handler mHandler = UIHandler.addCallBack(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case ConstantValue.ID_restart_preview:
				cameraManager.requestPreviewFrame(decodeThread.getHandler(), ConstantValue.ID_decode);
				break;
			case ConstantValue.ID_decode_succeeded:
				Bundle bundle = msg.getData();
		        Bitmap barcode = null;
		        float scaleFactor = 1.0f;
		        if (bundle != null) {
		          byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
		          if (compressedBitmap != null) {
		            barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
		            // Mutable copy:
		            barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
		          }
		          scaleFactor = bundle.getFloat(DecodeThread.BARCODE_SCALED_FACTOR);          
		        }
		        handleDecode((Result) msg.obj, barcode, scaleFactor);
				break;
			case ConstantValue.ID_decode_failed:
				cameraManager.requestPreviewFrame(decodeThread.getHandler(), ConstantValue.ID_decode);
				break;
			}
			return false;
		}
	});
	private com.example.frameimpl.util.zxing.BeepManager beepManager;
	private InactivityTimer inactivityTimer;
	private com.example.frameimpl.util.zxing.AmbientLightManager ambientLightManager;
	private SurfaceView mSurface;
	private CameraManager cameraManager;
	private boolean hasSurface;
	private DecodeThread decodeThread;

	@Override
	public void onClick(View v) {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fg_view_2wm, null);
		int w = (int) (DensityUtil.getWindowWidth(getActivity()) * 0.7);
		int h = (int) (DensityUtil.getWindowHeigh(getActivity()) * 0.7);
		LayoutParams params = new LayoutParams(w, h);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		view.findViewById(R.id.fl_frame).setLayoutParams(params);

		return view;
	}

	@Override
	public void initView() {
		mSurface = (SurfaceView) findViewById(R.id.sv_camera);
	}

	@Override
	public void initData() {
		inactivityTimer = new InactivityTimer(getActivity());
		beepManager = new BeepManager(getActivity());
		ambientLightManager = new AmbientLightManager(getActivity());
		cameraManager = new CameraManager(getActivity());
		decodeThread = new DecodeThread(this, null, null, null,
		        new ResultPointCallback() {
					@Override
					public void foundPossibleResultPoint(ResultPoint arg0) {
					}
				});
		hasSurface = false;
	}

	@Override
	public void initListener() {
	}

	@Override
	public void onResume() {
		if (hasSurface) {
			initCamera(mSurface.getHolder());
		} else {
			mSurface.getHolder().addCallback(this);
		}
		beepManager.updatePrefs();
		ambientLightManager.start(cameraManager);
		inactivityTimer.onResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		cameraManager.stopPreview();
		inactivityTimer.onPause();
		ambientLightManager.stop();
		beepManager.close();
		cameraManager.closeDriver();
		if (hasSurface)
			mSurface.getHolder().removeCallback(this);
		super.onPause();
	}

	@Override
	public boolean OnBackPressed() {
		inactivityTimer.shutdown();
		return super.OnBackPressed();
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		if (!hasSurface){
			hasSurface = true;
			initCamera(surfaceHolder);
		}
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
			return;
		}

		try {
			cameraManager.openDriver(surfaceHolder);
			 decodeThread.start();
			 cameraManager.startPreview();
			 mHandler.sendEmptyMessage(ConstantValue.ID_restart_preview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public Handler getHandler() {
		return mHandler;
	}

	protected void handleDecode(Result obj, Bitmap barcode, float scaleFactor) {
		
	}
}
