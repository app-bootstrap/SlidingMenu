package com.xudafeng.slidingmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView {
	private static final String TAG = "slidingmenu";
	private int paddingRight = 200;
	private int screenWidth;
	private int rightPadding;
	private int menuWidth;
	public boolean isAnimated;
	private boolean isChanged;
	private ViewGroup menu;
	private ViewGroup main;

	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		rightPadding = paddingRight;
	}

	public SlidingMenu(Context context) {
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (!isChanged) {
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			menu = (ViewGroup) wrapper.getChildAt(0);
			main = (ViewGroup) wrapper.getChildAt(1);
			menuWidth = screenWidth - rightPadding;
			menu.getLayoutParams().width = menuWidth;
			main.getLayoutParams().width = screenWidth;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(menuWidth, 0);
			isChanged = true;
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > menuWidth / 2) {
				this.smoothScrollTo(menuWidth, 0);
				isAnimated = false;
			} else {
				this.smoothScrollTo(0, 0);
				isAnimated = true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	public void slideOut() {
		if (isAnimated) {
			return;
		}

		this.smoothScrollTo(0, 0);
		isAnimated = true;
		Log.i(TAG, "slideOut");
	}

	public void slideIn() {
		if (isAnimated) {
			this.smoothScrollTo(menuWidth, 0);
			isAnimated = false;
			Log.i(TAG, "slideIn");
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / menuWidth;
		float leftScale = 1 - 0.3f * scale;
		float rightScale = 0.8f + scale * 0.2f;

		ViewHelper.setScaleX(menu, leftScale);
		ViewHelper.setScaleY(menu, leftScale);
		ViewHelper.setAlpha(menu, 0.6f + 0.4f * (1 - scale));
		ViewHelper.setTranslationX(menu, menuWidth * scale * 0.7f);
		ViewHelper.setPivotX(main, 0);
		ViewHelper.setPivotY(main, main.getHeight() / 2);
		ViewHelper.setScaleX(main, rightScale);
		ViewHelper.setScaleY(main, rightScale);
	}
}