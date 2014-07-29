package com.example.frameimpl.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public abstract class BaseLayout {
	protected static final int MATCH_PARENT = LayoutParams.MATCH_PARENT;
	protected static final int WRAP_CONTENT = LayoutParams.WRAP_CONTENT;
	
	public abstract View getView(Context context);
}
