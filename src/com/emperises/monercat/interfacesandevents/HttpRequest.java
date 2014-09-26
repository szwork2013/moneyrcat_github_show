package com.emperises.monercat.interfacesandevents;

import net.tsz.afinal.http.AjaxCallBack;
public class HttpRequest extends AjaxCallBack<String> {
	
	private HttpInterface mHttpInterface;
	public void onFailure(Throwable t, int errorNo, String strMsg) {
		mHttpInterface.onFail(t, errorNo, strMsg);
	};
	@Override
	public void onSuccess(String t) {
		super.onSuccess(t);
		mHttpInterface.onFinished(t);
	}
	@Override
	public void onStart() {
		super.onStart();
		mHttpInterface.onHttpStart();
	}
	@Override
	public void onLoading(long count, long current) {
		super.onLoading(count, current);
		mHttpInterface.onLoading(count, current);
	}
	public HttpRequest(HttpInterface callback) {
		this.mHttpInterface = callback;
	}
}
