package com.emperises.monercat.interfacesandevents;


public interface UrlPostInterface {

	
//	http://115.28.136.194:8086/zcm/zcm/user_app_logs.html 日志地址
	String HTTP_RESULE_SUCCESS = "00";
	//服务器IP地址
	String SERVER_ADDRESS  = "115.28.136.194";
//	String SERVER_PORT = "8088";
	String SERVER_PORT = "8086";
	String  BASE_URL = "http://"+SERVER_ADDRESS+":"+SERVER_PORT+"/zcm/";
	//注册
	String SERVER_URL_REG = BASE_URL + "user/bind_devices.do";  //绑定
	String SERVER_URL_BALANCE = BASE_URL + "user/query_user_balance.do";//获取余额 
	String SERVER_URL_ADLIST = BASE_URL + "advert/query_advert.do"; //获取广告列表
	String SERVER_URL_UPDATE_VERSION = BASE_URL  + "/app/query_new_version.do";//版本更新
	String SERVER_URL_SMSCODE = BASE_URL +"user/sendSms.do";//发送短信验证码
	String SERVER_URL_PRODUCT = BASE_URL +"product/query_products.do";//兑换列表
	String SERVER_URL_USERINFO = BASE_URL +"user/query_user_info.do";//查询用户信息
	String SERVER_URL_LOOPAD = BASE_URL +"advert/query_poll_advert.do";//获得轮询广列表
	String SERVER_URL_SAVEUSERINFO = BASE_URL + "user/perfect_user.do"; //提交用户信息
	String SERVER_URL_TRADE_DETAIL = BASE_URL + "trans/user_accounts.do"; //账务明细
	String SERVER_URL_AD_HISTORY = BASE_URL + "advert/query_user_logs.do";//我的广告记录
	String SERVER_URL_TIXIAN = BASE_URL + "trans/trans_tixian.do";//申请提现
	String SERVER_URL_BINDPHONE = BASE_URL + "user/bind_phone.do"; //绑定手机号码
	String SERVER_URL_UPLOAD_HEADERIMAGE = BASE_URL + "user/upload_user_img.do"; //上传头像
	String SERVER_URL_UPLOAD_FILE = BASE_URL + "rh_ext/rh_ext_save.do"; //上传文件
	String SERVER_URL_DUIHUAN_DEFAULT_INFO = BASE_URL + "/trans/trans_duihuan.do";//提交兑换信息
	String SERVER_URL_MESSAGELIST = BASE_URL + "/messages/query_msgs.do";//查询消息列表
	String SERVER_URL_FEEDBACK = BASE_URL + "/feedback/add.do";//提交意见反馈
	String SERVER_URL_UPLOAD_LOG_FILE = BASE_URL + "user/upload_user_log_file.do";//提交log文件
	String SERVER_URL_DUIHUANHISTORY = BASE_URL + "/duihuan/query_logs.do"; //查询兑换记录
	String SERVER_URL_ROANDOM_REG = BASE_URL + "user/bind_devices_vir.do";
}
