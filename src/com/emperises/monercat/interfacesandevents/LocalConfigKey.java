package com.emperises.monercat.interfacesandevents;

public interface LocalConfigKey {
	
	String INTENT_SERVICE_FLG 								= "push_logs";
	String INTENT_KEY_LOG_PATH 								= "logs_path";
	String LOCAL_CONFIG_KEY_UPLOAD_LOGS 					= "upload_logs";
	String VERSION 											= "version";			//版本号
	String LOCAL_CONFIGKEY_BIND_FLG 						= "bind_flg";			//绑定标志
	String LOCAL_CONFIGKEY_HEADER_RESID 					= "header_image_resid";	//本地头像
	String LOCAL_CONFIGKEY_HEADER_PATH 						= "header_image_path";	//第一次运行标识
	String LOCAL_CONFIGKEY_FIRSTRUN 						= "isFirstRun";
	String LOCAL_CONFIGKEY_BIND_TEL 						= "bind_tel";
	String LOCAL_CONFIGKEY_BALANCE 							= "balance";			//余额
	String LOCAL_CONFIGKEY_HEADER_IMAGE_URL 				= "header_image_url";	//用户头像地址
	String LOCAL_CONFIGKEY_REG 								= "first_reg"; 			//第一次注册
	String LOCAL_CONFIGKEY_DUIHUAN_NAME 					= "duihuan_name"; 		//兑换名字
	String LOCAL_CONFIGKEY_DUIHUAN_TEL 						= "duihuan_tel"; 		//兑换手机
	String LOCAL_CONFIGKEY_DUIHUAN_ADDR 					= "duihuan_addr"; 		//兑换地址
	String LOCAL_CONFIGKEY_SAFE_NAME 						= "safe_addr"; 			//安全信息姓名
	String LOCAL_CONFIGKEY_SAFE_NUMBER 						= "tel_addr"; 			//安全信息身份证
	String LOCAL_CONFIGKEY_BANK_ADDR 						= "bank_addr"; 			//银行卡开户行地址
	String LOCAL_CONFIGKEY_BANK_NAME 						= "bank_name"; 			//银行卡名字
	String LOCAL_CONFIGKEY_BANK_CARD	 					= "bank_card"; 			//银行卡号码
	String LOCAL_CONFIGKEY_DATEBASE_INSERT_FLG 				= "insert_once_flg";	//标识是否是第一次使用个人信息数据库
	
	String INTENT_KEY_EDIT_TYPE 							= "edit_type"; 			//编辑标识
	String INTENT_KEY_EDIT_VALUE 							= "edit_value"; 		//编辑内容
	String INTENT_KEY_ADINFO 								= "adinfo"; 			//广告信息
	String INTENT_KEY_TIXIAN_ID 							= "tixian_id";			//提现id
	String INTENT_KEY_TEL 			 						= "tel"; 				//电话号码
	String INTENT_KEY_PRODUCTID 							= "productid"; 			//商品ID 
	String INTENT_KEY_PRODUCINFO 							= "prodinfo"; 			//兑换地址
	//URL 参数key
	String POST_KEY_DEVICESID 								= "udevicesId"; 		//设备ID
	String LOCAL_CONFIG_KEY_USER_STATUS 					= "userStatus";
	
}
