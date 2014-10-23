package com.emperises.monercat.interfacesandevents;

public interface NativeJavaScriptCallBackInterface {

	String JsGetDeviceId();//获得设备ID
	String JsGetPersonalInformation();//获得个人信息
	String JsGetAdId();
	String JsGetProdInfo();
	void JsStartDuiHuanDialog();
	void   JsUpdateBalance();//更新余额
	void JsRefresh();
	void JsUploadImage();
	void JsStartActivity(String className);
	void JsOnError();
	
}
