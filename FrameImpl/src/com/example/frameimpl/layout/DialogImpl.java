package com.example.frameimpl.layout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout.LayoutParams;

import com.example.frameimpl.util.DensityUtil;

@SuppressWarnings("deprecation")
public class DialogImpl {
	private Dialog dialog;
	private static DialogImpl instance;

	public static DialogImpl getInstance() {
		if (instance == null) {
			instance = new DialogImpl();
		}
		return instance;
	}

	public void showDialog(Context context, View view) {
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new BitmapDrawable());

		LayoutParams params = new LayoutParams((int) (DensityUtil.getWindowWidth(context) * 0.7), (int) (DensityUtil.getWindowHeigh(context) * 0.6));
		dialog.getWindow().addContentView(view, params);
		dialog.setCancelable(false);
		dialog.show();
	}

	public void closeDialog() {
		if (dialog == null)
			return;

		dialog.dismiss();
		dialog = null;
	}
}
