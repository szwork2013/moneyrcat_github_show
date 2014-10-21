package com.emperises.monercat.ui.v3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
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
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.interfacesandevents.NativeJavaScriptCallBackInterface;
import com.emperises.monercat.interfacesandevents.NativeJavaScriptImpl;
import com.emperises.monercat.interfacesandevents.NativeJavaScriptImpl.ResCallBack;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class ActivityAdDetail_HTML5 extends OtherBaseActivity implements NativeJavaScriptCallBackInterface{

	private WebView mAdWebView;
	private ImageView mShareButton;
	private String mErrorBeforeUrl;
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad_detail_v3_html5);
		setCurrentTitle(getString(R.string.loading));

	}

	protected void setShareInfo() {
		info = (ZcmAdertising) getIntent().getSerializableExtra(
				INTENT_KEY_ADINFO);
		String url = info.getAdUrl();
		if(!TextUtils.isEmpty(url)){
			StringBuilder sb = new StringBuilder(url);
			sb.delete(sb.lastIndexOf("/")+1, sb.length());
			sb.append("index.html?"); 
			sb.append("p1="+Util.getDeviceId(this)+"&p2="+info.getAdId());
			setShareUrl(sb.toString());
			setShareTitle(info.getAdTitle());
			setShareLogoUrl(info.getAdIcon());
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
		webview.addJavascriptInterface(this, "zcmJavaCallBack");
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
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				mErrorBeforeUrl = view.getUrl();//记录错误发生之前的URL
				//加载失败
				super.onReceivedError(view, errorCode, description, failingUrl);
				mAdWebView.loadUrl("file:///android_asset/error.html");
			}
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			String url = mAdWebView.getUrl();
			if(!TextUtils.isEmpty(url)){
				if(url.equals(info.getAdUrl())){
					finish();
				} else {
					mAdWebView.loadUrl(info.getAdUrl());
				}
			} else {
				finish();
			}
		}
		return true;
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.ad_share:
			openShare();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 以下是java回调js代码
	 */
	@JavascriptInterface
	@Override
	public String JsGetDeviceId() {
		Logger.i("JS", "callback getdevicesid");
		return Util.getDeviceId(this);
	}

	@JavascriptInterface
	@Override
	public String JsGetPersonalInformation() {
		Logger.i("JS", "callback JsGetPersonalInformation");
		ZcmUser user = getDatabaseInterface().getMyInfo();
		String json =  new Gson().toJson(user);
		return json;
	}
	@JavascriptInterface
	@Override
	public void JsUpdateBalance() {
		Logger.i("JS", "callback JsUpdateBalance");
		updateBalance();
	}
	@JavascriptInterface 
	@Override
	public void JsRefresh() {
		mAdWebView.loadUrl(mAdWebView.getUrl());
	}
	@JavascriptInterface
	@Override
	public String JsGetAdId() {
		
		return info.getAdId();
	}
	@JavascriptInterface
	@Override
	public void JsUploadImage() {
		//获取当前的广告ID
		Intent i = new Intent(this,UploadImageActivity.class);
		i.putExtra("adId", info.getAdId());
		startActivity(i);
	}
	@JavascriptInterface
	@Override
	public void JsStartActivity(String className) {
		Class<?> classIntent;
		try {
			classIntent = Class.forName(className);
			Intent i = new Intent(this, classIntent);
			startActivity(i);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Logger.e("ERROR", "class no found",e);
		}
		
	}
	@Override
	public void JsOnError() {
		mAdWebView.loadUrl(mErrorBeforeUrl);
		Logger.i("ERROR", "因为网页加载错误,所以出现这个信息");
	}
}
