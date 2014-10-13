package com.emperises.monercat.ui.v3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

@SuppressLint("NewApi")
public class CopyRightActivity extends OtherBaseActivity {

	private WebView mAdWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_copyright);
		setCurrentTitle(getString(R.string.loading));
	}

	@Override
	protected void initViews() {
		mAdWebView = (WebView) findViewById(R.id.adwebView);
		initWebSetting(mAdWebView);
		mAdWebView.loadUrl("http://115.28.136.194:8086/zcm/ex/zcm/update.html");
		mAdWebView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if(newProgress == 100){
					setCurrentTitle(getString(R.string.copyright));
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		mAdWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	private void initWebSetting(WebView webview) {
		WebSettings webSettings = webview.getSettings();
		webSettings.setAllowContentAccess(true);
		webSettings.setAppCacheMaxSize(10 * 1024 * 1024);
		webSettings.setAllowFileAccess(true);
		webSettings.setAppCacheEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSettings.setDomStorageEnabled(true);
		webSettings.setDatabaseEnabled(true);

		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(true);

		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setRenderPriority(RenderPriority.HIGH);
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setGeolocationEnabled(true);
		webview.setHorizontalScrollBarEnabled(false);
		webview.setVerticalScrollBarEnabled(false);
	}
}
