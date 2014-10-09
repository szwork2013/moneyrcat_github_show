package com.emperises.monercat.interfacesandevents;

public interface LocalConfigKey {

	//绑定标志
	String LOCAL_CONFIGKEY_BIND_FLG = "bind_flg";
	//本地头像
	String LOCAL_CONFIGKEY_HEADER_RESID = "header_image_resid";
	//第一次运行标识
	String LOCAL_CONFIGKEY_FIRSTRUN = "isFirstRun";
	String LOCAL_CONFIGKEY_BALANCE = "balance";//余额
	String LOCAL_CONFIGKEY_REG = "first_reg"; //第一次注册
	String LOCAL_CONFIGKEY_DUIHUAN_NAME = "duihuan_name"; //兑换名字
	String LOCAL_CONFIGKEY_DUIHUAN_TEL = "duihuan_tel"; //兑换手机
	String LOCAL_CONFIGKEY_DUIHUAN_ADDR = "duihuan_addr"; //兑换地址
	
	String INTENT_KEY_EDIT_TYPE = "edit_type"; //编辑标识
	String INTENT_KEY_EDIT_VALUE = "edit_value"; //编辑内容
	String INTENT_KEY_ADINFO = "adinfo"; //广告信息
	String INTENT_KEY_TIXIAN_TYPE = "tixian_type";//提现类型 10 , 50 ,100
	String INTENT_KEY_TEL = "tel";
	String INTENT_KEY_PRODUCTID = "productid";
	//URL 参数key
	String POST_KEY_DEVICESID = "udevicesId";
	
	
}
