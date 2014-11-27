package com.emperises.monercat.ui;

import java.io.File;
import java.util.UUID;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.emperises.monercat.BuildConfig;
import com.emperises.monercat.MainActivity;
import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.customview.CustomDialog.DialogClick;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.RegResult;
import com.emperises.monercat.interfacesandevents.UrlPostInterface;
import com.emperises.monercat.services.MoneyCatService;
import com.emperises.monercat.ui.v3.CheckCodeActivity;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getHttpClient().post(SERVER_URL_CHECKCODE_FLG, new AjaxCallBack<String>(){
			@Override
			public void onSuccess(String t) {
				Logger.i("CHECK", t);
				super.onSuccess(t);
				if(!TextUtils.isEmpty(t)){
					DomainObject o = new Gson().fromJson(t, DomainObject.class);
					if(o != null && o.getResultCode().equals("01")){
						Intent i = new Intent(SplashActivity.this, CheckCodeActivity.class);
						startActivityForResult(i, 0);						
					} else {
						init();
					}
				} else {
					init();
				}
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				init();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		init();
	}
	private void init() {
		Logger.i("DEBUG",BuildConfig.DEBUG+"");
		// 初始化异常捕获模块
		if (!Logger.DEBUG) {
			CrashHandler.getInstance().init(getApplicationContext());
		}
		startService(new Intent(this, MoneyCatService.class));
		File file = new File(Environment.getExternalStorageDirectory(),
				"moneycat");
		if (!file.exists()) {
			file.mkdir();
		}
		// 创建一个SD卡文件夹
		// 检测是否是真机
		String deviceId = Util.getDeviceId(this);
		if (TextUtils.isEmpty(deviceId) || deviceId.equals("000000000000000")) {
			// 如果不是真机设备，直接返回
			showToast("设备不支持!");
			finish();
		} else {
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
			showDialog(getString(R.string.hit), getString(R.string.NET_ERROR),
					new DialogClick() {
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
			params.put(POST_KEY_DEVICESID,
					Util.getDeviceId(SplashActivity.this));
			String channelId = "0";
			try {
				ApplicationInfo appInfo = SplashActivity.this.getPackageManager()
                        .getApplicationInfo(getPackageName(),
                PackageManager.GET_META_DATA);
				channelId = appInfo.metaData.getInt("channel_id", 0) + "";
				
				Logger.i("CHANNEL", "当前渠道:"+channelId);
			} catch (NameNotFoundException e) {
				e.printStackTrace(); 
			} 
			params.put("uqdSrc", channelId);
			startRequest(SERVER_URL_REG, params);
			
		}
		randomReg();
	}

	private void randomReg(){
		AjaxParams params = new AjaxParams();
		final String uid = UUID.randomUUID().toString()+"_ios";
		params.put("udevicesId", uid); 
		getHttpClient().post(SERVER_URL_ROANDOM_REG,params, new AjaxCallBack<String>(){
			@Override
			public void onSuccess(String t) {
				Logger.i("RG",t+"DEVICEID:"+uid);
				super.onSuccess(t);
			}
		});
	}
	private void startHome() {
		float oldVersion = getFloatValueForKey(VERSION);
		float currentVersion = Util.getLocalVersionCode(this);
		if (currentVersion > oldVersion) {
			// 判断版本号
			setFloatForKey(VERSION, currentVersion);
			// 如果有新版本让第一次运行生效
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
			Toast.makeText(this, "恭喜，你已加入喵星人行列，代号No." + obj.getVal(),
					Toast.LENGTH_LONG).show();
			;
		}
		startHome();
		Logger.i(TAG_HTTP, content);
	}

	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		showNetErrorToast(strMsg, t);
		Logger.e(TAG_HTTP, strMsg, t);
		setBooleanForKey(LOCAL_CONFIGKEY_REG, false);
		finish();
	}

	@Override
	protected void initViews() {

	}

}
