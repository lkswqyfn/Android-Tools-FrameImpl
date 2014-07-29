/*package com.example.frameimpl.control;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.example.frameimpl.ConstantValue;
*//**
 * 侧滑删除触摸控制类
 * @author wqYuan
 *
 *//*
public class SlideDelOnTouchControl implements BaseOnTouchControl {
	private static final int HIDE_BUTTON = 0;
	private static final int SHOW_BUTTON = 1;
	private View_listView v;
	private boolean mLock;
	private int mMostRecentX;
	private int mMostRecentY;
	private int position;
	private View_SlideItem itemView;
	private int menuSlidingWidth;
	private View_SlideItem oldView;
	private View_SlideItem oldMoveView;
	private int mSlideX;
	private int deltaX;
	private int deltaY;
	private int touchSlop;
	private int mCurrentScreen;
	private Scroller mScroller;
	private Scroller mOldViewScroller;
	
	public SlideDelOnTouchControl(View_listView v,Context context) {
		this.v = v;
		mScroller = new Scroller(context);
		mOldViewScroller = new Scroller(context);
		touchSlop = ViewConfiguration.get(v.getContext()).getScaledTouchSlop();
	}

	@Override
	public boolean onActionDown(MotionEvent ev) {

		mLock = false;
		mMostRecentX = (int) ev.getX();
		mMostRecentY = (int) ev.getY();

		position = v.pointToPosition(mMostRecentX, mMostRecentY);
		if (position > 0) {
			itemView = (View_SlideItem) (v.getChildAt(position - v.getFirstVisiblePosition())).findViewById(ConstantValue.item_id);
			menuSlidingWidth = itemView.getDeleteBtnWidth();
		} else {
			itemView = null;
		}

		if (oldView != itemView)
			hideMenu();

		mSlideX = (int) ev.getX();
		
		return true;
	}

	@Override
	public boolean onActionMove(MotionEvent ev) {
		int moveX = (int) ev.getX();
		int moveY = (int) ev.getY();

		deltaX = mMostRecentX - moveX;
		deltaY = mMostRecentY - moveY;

		int mDeltaSlideX = mSlideX - moveX;
		mSlideX = moveX;

		if (!mLock && (itemView == null || Math.abs(deltaX) < Math.abs(deltaY))) {
			if (oldView == itemView)
				hideMenu();
			return false;
		} else {
			if (Math.abs(deltaX) < touchSlop)
				return false;
			mLock = true;
			int newScrollX = itemView.getScrollX() + mDeltaSlideX;
			if (newScrollX > menuSlidingWidth) {
				itemView.scrollTo(menuSlidingWidth, 0);
			} else if (newScrollX < 0) {
				itemView.scrollTo(0, 0);
			} else {
				itemView.scrollBy(mDeltaSlideX, 0);
			}
		}
		return true;
	}

	@Override
	public boolean onActionUp(MotionEvent ev) {
		if (!mLock && (itemView == null || Math.abs(deltaX) < Math.abs(deltaY) || Math.abs(deltaX) < touchSlop)) {
			if (oldView == itemView)
				hideMenu();
			if (mCurrentScreen == SHOW_BUTTON) {
				mCurrentScreen = HIDE_BUTTON;
				return true;
			}
			return false;
		} else {
			int scrollX = itemView.getScrollX();
			int menuXCenter = menuSlidingWidth / 2;

			if (scrollX < menuXCenter) {
				mCurrentScreen = HIDE_BUTTON;
			} else {
				mCurrentScreen = SHOW_BUTTON;
			}
			switchScreen();
			return true;
		}
	}

	private void switchScreen() {
		int scrollX = itemView.getScrollX();
		int dx = 0;

		if (mCurrentScreen == HIDE_BUTTON) {
			dx = -scrollX;
			oldView = null;
		} else if (mCurrentScreen == SHOW_BUTTON) {
			dx = menuSlidingWidth - scrollX;
			oldView = itemView;
		}

		mScroller.startScroll(scrollX, 0, dx, 0);
		v.invalidate();
	}

	private void hideMenu() {
		if (oldView == null)
			return;

		oldMoveView = oldView;
		oldView = null;

		int dx = oldMoveView.getScrollX();
		mOldViewScroller.startScroll(dx, 0, -dx, 0);
		v.invalidate();
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) { // 判断是否正在模拟数据中, true 正在进行 false
			itemView.scrollTo(mScroller.getCurrX(), 0);
			v.invalidate();
		}
		if (mOldViewScroller.computeScrollOffset()) { // 判断是否正在模拟数据中, true 正在进行
			oldMoveView.scrollTo(mOldViewScroller.getCurrX(), 0);
			v.invalidate();
		}
	}
}
*/