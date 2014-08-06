package com.example.frameimpl.util.zxing;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class ZXingUtil {
	public static String parse2WM(Bitmap bitmap) {
		MultiFormatReader reader = new MultiFormatReader();
		BitmapLuminanceSource source = new BitmapLuminanceSource(bitmap);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap image = new BinaryBitmap(binarizer);
		Result decode;
		try {
			decode = reader.decode(image);
			return decode.getText();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 检测是否含有摄像头
	 * @param context
	 * @return
	 */
	public static boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		}
		return false;
	}
}
