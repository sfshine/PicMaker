package com.su.funycardpro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.su.funycardpro.R;

public class WebViewActivity extends Activity {
	/** Called when the activity is first created. */
	WebView wv;
	ProgressDialog pd;
	Handler handler;

	private String url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wv = new WebView(this);
		wv.setBackgroundColor(0xFF4BCCEC);
		setContentView(wv);

		url = this.getIntent().getStringExtra("URL");
		init();// 执行初始化函数
		loadurl(wv, url);
		handler = new Handler() {
			public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
				if (!Thread.currentThread().isInterrupted()) {
					switch (msg.what) {
					case 0:
						pd.show();
						break;
					case 1:
						pd.hide();
						break;
					}
				}
				super.handleMessage(msg);
			}
		};
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void init() {// 初始化

		wv.getSettings().setJavaScriptEnabled(true);// 可用JS
		wv.setScrollBarStyle(0);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
		wv.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {
				loadurl(view, url);// 载入网页
				return true;
			}// 重写点击动作,用webview载入

		});
		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
				pd.setProgress(progress);// 横向的需要
				if (progress == 100) {

					handler.sendEmptyMessage(1);// 如果全部载入,隐藏进度对话框
				}
				super.onProgressChanged(view, progress);
			}
		});

		pd = new ProgressDialog(WebViewActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 也可以是轮子的
		pd.setMessage("Loading...");

	}

	public void loadurl(final WebView view, final String url) {

		pd.show();
		view.loadUrl(url);// 载入网页

	}

}