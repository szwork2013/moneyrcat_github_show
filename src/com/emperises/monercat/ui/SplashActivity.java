package com.emperises.monercat.ui;

import java.io.File;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.emperises.monercat.MainActivity;
import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.customview.CustomDialog.DialogClick;
import com.emperises.monercat.domain.model.RegResult;
import com.emperises.monercat.services.MoneyCatService;
import com.emperises.monercat.ui.v3.WelcomeActivity;
import com.emperises.monercat.utils.CrashHandler;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class SplashActivity extends OtherBaseActivity {

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			reg();
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//初始化异常捕获模块
		CrashHandler.getInstance().init(getApplicationContext());
		startService(new Intent(this,MoneyCatService.class));
		super.onCreate(savedInstanceState);
		File file = new File(Environment.getExternalStorageDirectory(),"moneycat");
		if(!file.exists()){
				file.mkdir();
		}
		setContentView(R.layout.activity_splash);
		//创建一个SD卡文件夹
		//检测是否是真机
		String deviceId = Util.getDeviceId(this);
		if(TextUtils.isEmpty(deviceId) || deviceId.equals("00000000000000")){
			//如果不是真机设备，直接返回
			showToast("设备不支持!");
			finish();
		}else{
			// 注册ID
			new Thread(new Runnable() {
				@Override
				public void run() {
					SystemClock.sleep(100);
					mHandler.sendEmptyMessage(0);
				}
			}).start();
		}
		
	}

	private void reg() {
		if (!Util.checkNetWorkStatus(this)) {
			showDialog(getString(R.string.hit), getString(R.string.NET_ERROR), new DialogClick() {
				@Override
				public void onClick(View v) {
					finish();
					super.onClick(v);
				}
			}, new DialogClick() {
				@Override
				public void onClick(View v) {
					finish();
					super.onClick(v);
				}
			});
			return;
		}
		if (getBoleanValueForKey(LOCAL_CONFIGKEY_REG)) {
			// 如果注册过
			startHome();
			
		} else {
			AjaxParams params = new AjaxParams();
			params.put(POST_KEY_DEVICESID, Util.getDeviceId(SplashActivity.this));
			startRequest(SERVER_URL_REG, params);
		}
	}

	
	private void startHome() { 
		float oldVersion = getFloatValueForKey(VERSION); 
		float currentVersion = Util.getLocalVersionCode(this); 
		if(currentVersion > oldVersion){
			//判断版本号
			setFloatForKey(VERSION, currentVersion);
			//如果有新版本让第一次运行生效
			setBooleanForKey(LOCAL_CONFIGKEY_FIRSTRUN, false);
			startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
		} else {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
		
		finish();
	}

	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		RegResult obj = new Gson().fromJson(content, RegResult.class);
		if (obj != null && obj.getResultCode().equals(HTTP_RESULE_SUCCESS)) {
			// 如果注册成功
			setBooleanForKey(LOCAL_CONFIGKEY_REG, true);
			Toast.makeText(this, "恭喜，你已加入喵星人行列，代号No."+obj.getVal(), Toast.LENGTH_LONG).show();;
		} 
		startHome();
		Logger.i(TAG_HTTP, content);
	}

	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		showNetErrorToast(strMsg,t);
		Logger.e(TAG_HTTP, strMsg, t);
		setBooleanForKey(LOCAL_CONFIGKEY_REG, false);
		finish();
	}

	@Override
	protected void initViews() {

	}

}
