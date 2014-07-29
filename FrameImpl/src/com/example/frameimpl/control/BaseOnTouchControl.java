package com.example.frameimpl.control;

import android.view.MotionEvent;
/**
 * 触摸控制基类 -- OnTouch
 * @author wqYuan
 *
 */
public interface BaseOnTouchControl {
		public boolean onActionDown(MotionEvent ev);
		public boolean onActionUp(MotionEvent ev);
		public boolean onActionMove(MotionEvent ev);
		public void computeScroll();
}
