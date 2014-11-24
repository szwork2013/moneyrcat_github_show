package com.emperises.monercat.ui.v3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.customview.CustomDialog.DialogClick;
import com.emperises.monercat.customview.CustomDialogConfig;
import com.emperises.monercat.domain.model.ZcmProduct;
import com.emperises.monercat.interfacesandevents.NativeJavaScriptCallBackInterface;
import com.emperises.monercat.ui.BindActivity;
import com.emperises.monercat.ui.DuiHuanDialogActivity;
import com.emperises.monercat.utils.Logger;
import com.google.gson.Gson;

@SuppressLint({ "NewApi", "JavascriptInterface", "SetJavaScriptEnabled" })
public class ActivityProdInfoHtml5 extends OtherBaseActivity implements NativeJavaScriptCallBackInterface {

	private WebView mProdWebView;
	private ZcmProduct mProductInfo;
	private ProgressBar mProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prodinfo_html5);
		setCurrentTitle(getString(R.string.loading));
	}
	@Override
	protected void initViews() {
		super.initViews();
		mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		mProductInfo = (ZcmProduct) getIntent().getSerializableExtra(INTENT_KEY_PRODUCINFO);
		mProdWebView = (WebView) findViewById(R.id.prodWebView);
		initWebSetting(mProdWebView);
//		mProdWebView.loadUrl(mProductInfo.getpUrl());
		mProdWebView.loadUrl("http://115.28.136.194:8086/zcm/ex/zcm/duihuan.html");
	}
	private String mErrorBeforeUrl;
	private void initWebSetting(final WebView webview) {
		webview.setWebChromeClient(new WebChromeClient() {
			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				Logger.i("PROGRESS", newProgress+"");
				mProgressBar.setVisibility(View.VISIBLE);
				mProgressBar.setProgress(newProgress);
				if(newProgress == 100){
					mProgressBar.setVisibility(View.GONE);
					setCurrentTitle(getString(R.string.prod_hteml5_title));
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				mErrorBeforeUrl = view.getUrl();//记录错误发生之前的URL
				//加载失败
				super.onReceivedError(view, errorCode, description, failingUrl);
				webview.loadUrl("file:///android_asset/error.html");
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
		});
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
		webview.addJavascriptInterface(this, "zcmJavaCallBack");
	}
	@JavascriptInterface
	@Override
	public String JsGetDeviceId() {
		return null;
	}
	@JavascriptInterface
	@Override
	public String JsGetPersonalInformation() {
		return null;
	}
	@JavascriptInterface
	@Override
	public String JsGetAdId() {
		return null;
	}
	@JavascriptInterface
	@Override
	public String JsGetProdInfo() {
		String s = new Gson().toJson(mProductInfo);
		return s;
	}
	@JavascriptInterface
	@Override
	public void JsUpdateBalance() {
		
	}
	@JavascriptInterface
	@Override
	public void JsRefresh() {
		
	}
	@JavascriptInterface
	@Override
	public void JsUploadImage(int des) {
		
	}
	
	@JavascriptInterface
	@Override
	public void JsStartActivity(String className) {
		
	}
	@JavascriptInterface
	@Override
	public void JsOnError() {
		mProdWebView.loadUrl(mErrorBeforeUrl);
	}
	@JavascriptInterface
	@Override
	public void JsStartDuiHuanDialog() {
		showDuihuanDialog();
	}
	private void showDuihuanDialog(){
		
		CustomDialogConfig config = new CustomDialogConfig();
		config.setSureButtonText("兑换");
		config.setCancleButtonText("取消");
		config.setTitle("兑换");
		config.setSureListener(new DialogClick() {
			@Override
			public void onClick(View v) {
				super.onClick(v);
				duihuan();
			}
		});
		config.setCancelListener(new DialogClick() {
			@Override
			public void onClick(View v) {
				super.onClick(v);
			}
		});
		config.setMessage("您当前兑换的商品是“" + mProductInfo.getPname()+"”,兑换申请提交成功之后将会扣除相应的喵币。");
		showDialog(config);
	}

	private void duihuan () {
		String tel = getStringValueForKey(LOCAL_CONFIGKEY_BIND_TEL);
		if(!TextUtils.isEmpty(tel)){
			Intent i  =  new Intent(this, DuiHuanDialogActivity.class);
			i.putExtra(INTENT_KEY_PRODUCINFO, mProductInfo);
			startActivity(i);
		} else {
			showBindDialog();
		}
	}
	private void showBindDialog() {
		CustomDialogConfig config = new CustomDialogConfig();
		config.setTitle("兑换");
		config.setCancleButtonText("取消");
		config.setMessage("绑定手机号码才能进行兑换哦!");
		config.setSureButtonText("绑定");
		config.setCancelListener(new DialogClick() {
			@Override
			public void onClick(View v) {
				super.onClick(v); 
			}
		});
		config.setSureListener(new DialogClick() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ActivityProdInfoHtml5.this , BindActivity.class));
				super.onClick(v);
			}
		});
		showDialog(config);
	}
	@JavascriptInterface
	@Override
	public void JsUploadImage() {
		
	}
	@Override
	public void JsStartBrowseActivity(String filePath) {
		// TODO Auto-generated method stub
		
	}
	
}

