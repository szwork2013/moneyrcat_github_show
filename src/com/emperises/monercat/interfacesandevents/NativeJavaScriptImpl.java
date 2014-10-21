package com.emperises.monercat.interfacesandevents;

import android.content.Intent;
import android.webkit.JavascriptInterface;
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
	private ResCallBack callback;
	public NativeJavaScriptImpl(BaseActivity context , WebView mWebView , String adId) {
		this.context = context;
		this.adId = adId;
		this.mWebView = mWebView;
	}
	public void setRefreshCallBack(ResCallBack callback){
		this.callback = callback;
	}
	public interface ResCallBack{
		void onRefresh();
	}
	
	@JavascriptInterface
	@Override
	public String JsGetDeviceId() {
		Logger.i("JS", "callback getdevicesid");
		return Util.getDeviceId(context.getApplicationContext());
	}

	@JavascriptInterface
	@Override
	public String JsGetPersonalInformation() {
		Logger.i("JS", "callback JsGetPersonalInformation");
		ZcmUser user = context.getDatabaseInterface().getMyInfo();
		String json =  new Gson().toJson(user);
		return json;
	}
	@JavascriptInterface
	@Override
	public void JsUpdateBalance() {
		Logger.i("JS", "callback JsUpdateBalance");
		context.updateBalance();
	}
	@JavascriptInterface 
	@Override
	public void JsRefresh() {
		Logger.i("RES", "刷新:"+mWebView.toString());
		if(callback != null){
			callback.onRefresh();
		}
	}
	@JavascriptInterface
	@Override
	public String JsGetAdId() {
		
		return adId;
	}
	@JavascriptInterface
	@Override
	public void JsUploadImage() {
		//获取当前的广告ID
		Intent i = new Intent(context,UploadImageActivity.class);
		i.putExtra("adId", adId);
		context.startActivity(i);
	}
	@JavascriptInterface
	@Override
	public void JsStartActivity(String className) {
		Class<?> classIntent;
		try {
			classIntent = Class.forName(className);
			Intent i = new Intent(context, classIntent);
			context.startActivity(i);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Logger.e("ERROR", "class no found",e);
		}
		
	}
	@Override
	public void JsOnError() {
		Logger.i("ERROR", "因为网页加载错误,所以出现这个信息");
	}
	
}
