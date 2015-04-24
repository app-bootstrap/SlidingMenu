package com.xudafeng.slidingmenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity implements Callback, OnClickListener {
	private static final String TAG = "slidingmenu";
	private WebView webView;
	private Button leftButton;
	private SlidingMenu menu;
	private LinearLayout main;
	private RelativeLayout first_button;
	private RelativeLayout second_button;
	private RelativeLayout third_button;
	private RelativeLayout second_layout;
	private RelativeLayout third_layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		Log.i(TAG, "start loading...");
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initView() {
		leftButton = (Button) findViewById(R.id.button_first);
		leftButton.setOnClickListener(this);
		leftButton.setVisibility(View.VISIBLE);
		menu = (SlidingMenu) findViewById(R.id.id_menu);
		main = (LinearLayout) findViewById(R.id.id_main);
		main.setOnClickListener(this);
		first_button = (RelativeLayout) findViewById(R.id.first_button);
		first_button.setOnClickListener(this);
		second_button = (RelativeLayout) findViewById(R.id.second_button);
		second_button.setOnClickListener(this);
		third_button = (RelativeLayout) findViewById(R.id.third_button);
		third_button.setOnClickListener(this);
		second_layout = (RelativeLayout) findViewById(R.id.layout_second);
		third_layout = (RelativeLayout) findViewById(R.id.layout_third);
		initWebview();
	}

	void initWebview() {
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}
		});
		webView.loadUrl("http://www.xdf.me");
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_first:
			if (menu.isAnimated) {
				menu.slideIn();
			} else {
				menu.slideOut();
			}
			break;
		case R.id.id_main:
		case R.id.webview:
			if (menu.isAnimated) {
				menu.slideIn();
			}
			break;
		case R.id.first_button:
			webView.loadUrl("http://www.baidu.com");
			webView.setVisibility(View.VISIBLE);
			second_layout.setVisibility(View.GONE);
			third_layout.setVisibility(View.GONE);
			menu.slideIn();
			break;
		case R.id.second_button:
			webView.setVisibility(View.GONE);
			third_layout.setVisibility(View.GONE);
			second_layout.setVisibility(View.VISIBLE);
			menu.slideIn();
			break;
		case R.id.third_button:
			webView.setVisibility(View.GONE);
			second_layout.setVisibility(View.GONE);
			third_layout.setVisibility(View.VISIBLE);
			menu.slideIn();
			break;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
}
