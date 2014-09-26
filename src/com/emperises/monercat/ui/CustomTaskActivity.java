package com.emperises.monercat.ui;

import android.os.Bundle;
import android.webkit.WebView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;


public class CustomTaskActivity extends OtherBaseActivity {

	private WebView web;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__customtask);
	}
	@Override
	protected void initViews() {
		super.initViews();
		web = (WebView) findViewById(R.id.webview);
		web.getSettings().setJavaScriptEnabled(true); 
		web.loadUrl("http://bmw.thefront.com.cn/bmw-x4/?from=groupmessage&isappinstalled=0#/");
	}
}
