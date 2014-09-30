package com.emperises.monercat.interfacesandevents;

import android.content.Intent;
import android.webkit.WebView;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.ui.v3.UploadImageActivity;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class NativeJavaScriptImpl implements NativeJavaScriptCallBackInterface{

	private BaseActivity context;
	private WebView mWebView;
	private String adId;
	public NativeJavaScriptImpl(BaseActivity context , WebView mWebView , String adId) {
		this.context = context;
		this.adId = adId;
		this.mWebView = mWebView;
	}
	@Override
	public String JsGetDeviceId() {
		Logger.i("JS", "callback getdevicesid");
		return Util.getDeviceId(context.getApplicationContext());
	}

	@Override
	public String JsGetPersonalInformation() {
		Logger.i("JS", "callback JsGetPersonalInformation");
		ZcmUser user = context.getDatabaseInterface().getMyInfo();
		String json =  new Gson().toJson(user);
		return json;
	}
	@Override
	public void JsUpdateBalance() {
		Logger.i("JS", "callback JsUpdateBalance");
		context.updateBalance();
	}

	@Override
	public void JsRefresh() {
		mWebView.loadUrl(mWebView.getUrl());
	}
	@Override
	public String JsGetAdId() {
		
		return adId;
	}
	@Override
	public void JsUploadImage() {
		//获取当前的广告ID
		Intent i = new Intent(context,UploadImageActivity.class);
		i.putExtra("adId", adId);
		context.startActivity(i);
	}
	
}
