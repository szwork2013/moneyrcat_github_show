package com.emperises.monercat.ui;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;

import com.emperises.monercat.MainActivity;
import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.customview.CustomDialog.DialogClick;
import com.emperises.monercat.domain.DomainObject;
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
		super.onCreate(savedInstanceState);
		Logger.i("INTENT", getIntent().toString());
		setContentView(R.layout.activity_splash);
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
		startActivity(new Intent(SplashActivity.this, MainActivity.class));
		finish();
	}

	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		DomainObject obj = new Gson().fromJson(content, DomainObject.class);
		if (obj != null && obj.getResultCode().equals(HTTP_RESULE_SUCCESS)) {
			// 如果注册成功
			setBooleanForKey(LOCAL_CONFIGKEY_REG, true);
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
