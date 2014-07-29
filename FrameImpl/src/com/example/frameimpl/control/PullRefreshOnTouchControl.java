package com.example.frameimpl.control;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
/**
 * 下拉刷新触摸控制类
 * @author wqYuan
 *
 */
/**
 * 下拉刷新触摸控制类
 * @author wqYuan
 *
 */
public class PullRefreshOnTouchControl implements BaseOnTouchControl {
	private static final int type = Animation.RELATIVE_TO_SELF;
	private static final int INVISIBLE = View.INVISIBLE;
	private static final int VISIBLE = View.VISIBLE;
	private View v;
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


	public PullRefreshOnTouchControl(View v,Context context) {
		this.v = v;
		mScroller = new Scroller(context);
//		touchSlop = ViewConfiguration.get(v.getContext()).getScaledTouchSlop();
		
//		iv_pulldown = (ImageView) v.findViewById(ConstantValue.iv_pulldown_id);
//		iv_pullup = (ImageView) v.findViewById(ConstantValue.iv_pullup_id);
//		iv_refresh = (ImageView) v.findViewById(ConstantValue.iv_refresh_id);
//		tv_pull = (TextView) v.findViewById(ConstantValue.tv_pull_id);
		
		initAnimation();
	}

	/**
	 * 设置下拉高度
	 * @param measuredHeight
	 */
	
	public void setMeasuredHeight(int measuredHeight) {
		this.measuredHeight = measuredHeight;
	}



	@Override
	public boolean onActionDown(MotionEvent event) {
		initState();
		mMostRecentY = (int) event.getY();
		return true;
	}

	@Override
	public boolean onActionMove(MotionEvent event) {
		int moveY = (int) event.getY();

		int deltaY = mMostRecentY - moveY;

		mMostRecentY = moveY;

		int newScrollY = v.getScrollY() + deltaY;
		if (v.getScrollY() <= 0) {
			if (newScrollY < -measuredHeight) {
				v.scrollTo(0, -measuredHeight);
				showUpPullImg();
				hasDownPlay = true;
			} else {
				v.scrollBy(0, deltaY);
				if (newScrollY > -measuredHeight + 10) {
					showDownPullImg();
					hasUpPlay = true;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onActionUp(MotionEvent ev) {
		int scrollY = v.getScrollY();
		if (scrollY < 0) {
			if (canRefresh) {
				showRefresh();
				mRefresh.pullRefresh(v);
			} else {
				int dy = -scrollY;
				mScroller.startScroll(0, scrollY, 0, dy, 500);
				v.invalidate();
			}
			return true;
		}
		return false;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) { // 判断是否正在模拟数据中, true 正在进行 false
			v.scrollTo(0, mScroller.getCurrY());
			v.invalidate();
		}
	}
	
	private void initState() {
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

		int dy = v.getScrollY();
		mScroller.startScroll(0, dy, 0, -dy, 500);
		v.invalidate();
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
	
	
}
