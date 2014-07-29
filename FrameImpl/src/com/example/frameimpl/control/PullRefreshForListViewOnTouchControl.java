package com.example.frameimpl.control;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * 下拉刷新触摸控制类
 * @author wqYuan
 *
 */
/**
 * 下拉刷新触摸控制类 For ListView版
 * 
 * @author wqYuan
 * 
 */
public class PullRefreshForListViewOnTouchControl implements BaseOnTouchControl, OnScrollListener {
	private static final int type = Animation.RELATIVE_TO_SELF;
	private static final int INVISIBLE = View.INVISIBLE;
	private static final int VISIBLE = View.VISIBLE;
	private View v;
	private ListView mListView;
	private Scroller mScroller;
	private int mMostRecentY;
	private int measuredHeight;
	private boolean hasDownPlay;
	private boolean hasUpPlay;
	private boolean canRefresh;
	private ImageView iv_pulldown;
	private ImageView iv_pullup;
	private ImageView iv_refresh;
	private TextView tv_pull;
	private RotateAnimation ra_down;
	private RotateAnimation ra_up;
	private RotateAnimation ra;
	private OnPullRefresh mRefresh;
	private int firstVisibleItemPosition;
	private boolean isScroll2Bottom;
	private boolean isLoadMoving;

	// private boolean isRefresh;

	public PullRefreshForListViewOnTouchControl(View v, Context context) {
		this.v = v;
		mScroller = new Scroller(context);

//		iv_pulldown = (ImageView) v.findViewById(ConstantValue.iv_pulldown_id);
//		iv_pullup = (ImageView) v.findViewById(ConstantValue.iv_pullup_id);
//		iv_refresh = (ImageView) v.findViewById(ConstantValue.iv_refresh_id);
//		tv_pull = (TextView) v.findViewById(ConstantValue.tv_pull_id);

		initAnimation();
	}

	/**
	 * 设置下拉高度
	 * 
	 * @param measuredHeight
	 */

	public void setMeasuredHeight(int measuredHeight) {
		this.measuredHeight = measuredHeight;
	}

	@Override
	public boolean onActionDown(MotionEvent event) {
		if (mRefresh == null)
			return false;

		// if (isRefresh)
		// return true;

		if (mListView == null) {
			mListView = (ListView) v.getParent();
		}
		initState();
		mMostRecentY = (int) event.getY();
		return false;
	}

	@Override
	public boolean onActionMove(MotionEvent event) {
		if (mRefresh == null)
			return false;

		// if (isRefresh)
		// return true;

		/*int moveY = (int) event.getY();

		int deltaY = moveY - mMostRecentY;

		mMostRecentY = moveY;

		int paddingTop = v.getPaddingTop() + deltaY;

		if (firstVisibleItemPosition == 0 && mListView.getChildAt(0).getTop() > 0 && paddingTop > -measuredHeight ) {
			if (paddingTop >= 0) { // 完全显示, 进入到刷新状态
				v.setPadding(0, 0, 0, 0);
				showUpPullImg();
				hasDownPlay = true;
			} else { // 没有完全显示, 进入到下拉状态
				v.setPadding(0, paddingTop, 0, 0);
				if (paddingTop > -measuredHeight + 10) {
					showDownPullImg();
					hasUpPlay = true;
				}
			}
			return true;
		}*/
		int moveY = (int) event.getY();	// 移动中的y轴的偏移量
		
		int diffY = moveY - mMostRecentY;
		
		int paddingTop = -measuredHeight + (diffY / 2);
		
		if(firstVisibleItemPosition == 0
				&& paddingTop > -measuredHeight) {
			if(paddingTop > 0) {		// 完全显示, 进入到刷新状态  
				showUpPullImg();
				hasDownPlay = true;
			} else if(paddingTop < 0) {		// 没有完全显示, 进入到下拉状态
				showDownPullImg();
				hasUpPlay = true;
			}
			v.setPadding(0, paddingTop, 0, 0);
			return true;
		}
		return false;
	}

	@Override
	public boolean onActionUp(MotionEvent ev) {
		if (mRefresh == null)
			return false;

		// if (isRefresh)
		// return true;

		int top = v.getPaddingTop();

		if (top > -measuredHeight) {
			if (top > 0){
				v.setPadding(0, 0, 0, 0);
				top = 0;
			}
			if (canRefresh) {
				// isRefresh = true;
				showRefresh();
				mRefresh.pullRefresh(v);
			} else {
				int dy = top - measuredHeight;
				mScroller.startScroll(0, top, 0, dy, 500);
				mListView.invalidate();
			}
			return true;
		}
		return false;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) { // 判断是否正在模拟数据中, true 正在进行 false
			v.setPadding(0, mScroller.getCurrY(), 0, 0);
			mListView.invalidate();
		}
	}

	private void initState() {
		v.setPadding(0, -measuredHeight, 0, 0);
		hasDownPlay = false;
		hasUpPlay = true;
		canRefresh = false;

		iv_pulldown.setVisibility(VISIBLE);

		iv_refresh.setVisibility(INVISIBLE);
		iv_pullup.setVisibility(INVISIBLE);
	}

	private void showDownPullImg() {
		if (!hasDownPlay)
			return;
		hasDownPlay = false;
		iv_pulldown.startAnimation(ra_down);
	}

	private void showUpPullImg() {
		if (!hasUpPlay)
			return;
		hasUpPlay = false;
		iv_pullup.startAnimation(ra_up);
	}

	private void showRefresh() {
		tv_pull.setText("正在刷新...");
		iv_refresh.setVisibility(VISIBLE);

		iv_pullup.clearAnimation();

		iv_pulldown.setVisibility(INVISIBLE);
		iv_pullup.setVisibility(INVISIBLE);

		iv_refresh.startAnimation(ra);
	}

	/**
	 * 外部调用
	 */
	public void HideRefresh() {
		tv_pull.setText("下拉可以刷新");

		iv_refresh.clearAnimation();
		iv_pulldown.clearAnimation();
		iv_pullup.clearAnimation();

		int top = v.getPaddingTop();
		int dy = top - measuredHeight;
		mScroller.startScroll(0, top, 0, dy, 500);
		mListView.invalidate();
	}

	private void initAnimation() {
		ra_down = new RotateAnimation(-180, 0, type, 0.5f, type, 0.5f);
		ra_down.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				iv_pulldown.setVisibility(View.VISIBLE);
				iv_pullup.setVisibility(View.INVISIBLE);
				tv_pull.setText("下拉可以刷新");
				canRefresh = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
		ra_down.setDuration(500);

		ra_up = new RotateAnimation(-180, 0, type, 0.5f, type, 0.5f);
		ra_up.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				iv_pulldown.setVisibility(View.INVISIBLE);
				iv_pullup.setVisibility(View.VISIBLE);
				tv_pull.setText("松开可以刷新");
				canRefresh = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
		ra_up.setDuration(500);

		ra = new RotateAnimation(0, 359, type, 0.5f, type, 0.5f);
		ra.setDuration(500);
		ra.setRepeatCount(Animation.INFINITE);
		LinearInterpolator lin = new LinearInterpolator();
		ra.setInterpolator(lin);
	}

	public interface OnPullRefresh {
		public void pullRefresh(View view);
	}

	public void setRefresh(OnPullRefresh mRefresh) {
		this.mRefresh = mRefresh;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING) {
			if (isScroll2Bottom && !isLoadMoving) { // 滚动到底部
				// 加载更多
				/*
				 * footerView.setPadding(0, 0, 0, 0);
				 * this.setSelection(this.getCount()); // 滚动到ListView的底部
				 * isLoadMoving = true;
				 * 
				 * if (mOnRefreshListener != null) {
				 * mOnRefreshListener.onLoadMoring(); }
				 */
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		firstVisibleItemPosition = firstVisibleItem;

		if ((firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount > 0) {
			isScroll2Bottom = true;
		} else {
			isScroll2Bottom = false;
		}
	}
}
