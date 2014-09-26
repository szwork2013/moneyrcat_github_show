package com.emperises.monercat.interfacesandevents;

public interface NativeJavaScriptCallBackInterface {

	String JsGetDeviceId();//获得设备ID
	String JsGetPersonalInformation();//获得个人信息
	void   JsUpdateBalance();//更新余额
}
