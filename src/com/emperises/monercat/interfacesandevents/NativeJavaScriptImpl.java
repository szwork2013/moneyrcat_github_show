package com.emperises.monercat.interfacesandevents;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class NativeJavaScriptImpl implements NativeJavaScriptCallBackInterface{

	private BaseActivity context;
	public NativeJavaScriptImpl(BaseActivity context) {
		this.context = context;
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

}
