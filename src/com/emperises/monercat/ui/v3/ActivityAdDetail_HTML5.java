package com.emperises.monercat.ui.v3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.ZcmAdertising;
import com.emperises.monercat.interfacesandevents.NativeJavaScriptImpl;
import com.emperises.monercat.ui.RecommendDialogActivity;
import com.emperises.monercat.ui.WYCJDialogActivity;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;

@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class ActivityAdDetail_HTML5 extends OtherBaseActivity {

	private WebView mAdWebView;
	private ImageView mShareButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad_detail_v3_html5);
		setCurrentTitle(getString(R.string.loading));

	}

	protected void setShareInfo() {
		info = (	ZcmAdertising) getIntent().getSerializableExtra(
				INTENT_KEY_ADINFO);
		String url = info.getAdUrl();
		if(!TextUtils.isEmpty(url)){
			StringBuilder sb = new StringBuilder(url);
			sb.delete(sb.lastIndexOf("/")+1, sb.length());
			sb.append("index.html?"); 
			sb.append("p1="+Util.getDeviceId(this)+"&p2="+info.getAdId());
			setShareUrl(sb.toString());
			setShareTitle(info.getAdTitle());
			setShareContent(info.getAdContent());		
		}
	};
	@SuppressLint("JavascriptInterface")
	private void initWebSetting(WebView webview) {
		WebSettings webSettings = webview.getSettings();
		webSettings.setAllowContentAccess(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setDatabaseEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setRenderPriority(RenderPriority.HIGH);
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview.setHorizontalScrollBarEnabled(false);
		webview.setVerticalScrollBarEnabled(false);
		webview.addJavascriptInterface(new NativeJavaScriptImpl(this,mAdWebView,info.getAdId()), "zcmJavaCallBack");
	}

	@Override
	protected void initViews() {
		super.initViews();
		mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		mShareButton = (ImageView) findViewById(R.id.ad_share);
		mShareButton.setVisibility(View.VISIBLE);
		mAdWebView = (WebView) findViewById(R.id.adwebView);
		initWebSetting(mAdWebView);
		Logger.i("URL", "AD URL："+info.getAdUrl());
		mAdWebView.loadUrl(info.getAdUrl());
		mAdWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
		});
		mAdWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				Logger.i("PROGRESS", newProgress+"");
				mProgressBar.setVisibility(View.VISIBLE);
				mProgressBar.setProgress(newProgress);
				if(newProgress == 100){
					mProgressBar.setVisibility(View.GONE);
					setCurrentTitle(getString(R.string.ad_hteml5_title));
					//获取新的余额信息
					updateBalance();
				}
				super.onProgressChanged(view, newProgress);
			}
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String acceptType) {
				mUploadMessage = uploadMsg; 
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				ActivityAdDetail_HTML5.this.startActivityForResult(
						Intent.createChooser(i, "File Browser"),
						FILECHOOSER_RESULTCODE);
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String acceptType, String capture) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				ActivityAdDetail_HTML5.this.startActivityForResult(
						Intent.createChooser(i, "File Chooser"),
						FILECHOOSER_RESULTCODE);

			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				Logger.i("FILE", "openFileChooser");
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				ActivityAdDetail_HTML5.this.startActivityForResult(
						Intent.createChooser(i, "File Browser"),
						FILECHOOSER_RESULTCODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage)
				return;
			Uri result = intent == null || resultCode != RESULT_OK ? null
					: intent.getData();
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
		}
	}

	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;
	private ZcmAdertising info;
	private ProgressBar mProgressBar;

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.woyaocanjia:
			startActivity(new Intent(this, WYCJDialogActivity.class));
			break;
		case R.id.recommend_friend:
			startActivity(new Intent(this, RecommendDialogActivity.class));
			break;
		case R.id.ad_share:
			openShare();
			break;

		default:
			break;
		}
	}
}
