package com.emperises.monercat.services;

import java.io.File;
import java.io.FileNotFoundException;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.emperises.monercat.interfacesandevents.LocalConfigKey;
import com.emperises.monercat.interfacesandevents.UrlPostInterface;
import com.emperises.monercat.utils.Logger;

public class MoneyCatService extends Service implements LocalConfigKey , UrlPostInterface{

	@Override
	public void onCreate() {
		super.onCreate();
		Logger.i("SERVICE", "服务启动");
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		String flg = intent.getStringExtra(INTENT_SERVICE_FLG);
		if(LOCAL_CONFIG_KEY_UPLOAD_LOGS.equals(flg)){
			//上传LOG
			String logPath = intent.getStringExtra(INTENT_KEY_LOG_PATH);
			File path = new File(logPath);
			if(!TextUtils.isEmpty(logPath) && path.exists()){
				uploadLogs(path);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
	private void uploadLogs(File path){
		Logger.i("LOGS", "准备发送LOG");
		AjaxParams params = new AjaxParams();
		try {
			params.put("file", path);
		} catch (FileNotFoundException e) {
			Logger.i("LOG", "日志文件不存在!不能上传!");
			e.printStackTrace();
		}
		new FinalHttp().post(SERVER_URL_UPLOAD_LOG_FILE, params, new AjaxCallBack<String>() {
			@Override
			public void onStart() {
				super.onStart();
				Logger.i("LOG", "开始上传...");
			}
			@Override
			public void onLoading(long count, long current) {
				Logger.i("LOG", "正在上传......");
				super.onLoading(count, current);
			}
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Logger.i("LOG", "上传成功...");
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Logger.e("LOG", "出错:上传失败...:"+strMsg,t);
				super.onFailure(t, errorNo, strMsg);
			}
		});
	}
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
