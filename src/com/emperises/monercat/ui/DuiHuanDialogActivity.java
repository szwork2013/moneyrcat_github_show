package com.emperises.monercat.ui;

import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class DuiHuanDialogActivity extends OtherBaseActivity {

	private EditText mDuihuanAddress;
	private EditText mDuihuanName;
	private EditText mDuihuanTel;
	private String mCurrentProductId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuan_dialog_v3);
	}
	@Override
	protected void initViews() {
		super.initViews();
		mDuihuanAddress = (EditText) findViewById(R.id.duihuan_address);
		mDuihuanName = (EditText) findViewById(R.id.duihuan_name);
		mDuihuanTel = (EditText) findViewById(R.id.duihuan_tel_edit);
		String localName = getStringValueForKey(LOCAL_CONFIGKEY_DUIHUAN_NAME);
		String localAddr = getStringValueForKey(LOCAL_CONFIGKEY_DUIHUAN_ADDR);
		String localTel = getStringValueForKey(LOCAL_CONFIGKEY_DUIHUAN_TEL);
		mDuihuanAddress.setText(localAddr);
		mDuihuanName.setText(localName);
		mDuihuanTel.setText(localTel);
		mCurrentProductId = getIntent().getStringExtra(INTENT_KEY_PRODUCTID);
	}
	@SuppressLint("NewApi")
	private void duihuan(){
		String name = mDuihuanName.getText().toString();
		String address = mDuihuanAddress.getText().toString();
		String tel = mDuihuanTel.getText().toString();
		if(name.isEmpty() || tel.isEmpty()){
			showToast("您的信息不完整");
		} else {
			
			AjaxParams params = new AjaxParams();
			setStringtForKey(LOCAL_CONFIGKEY_DUIHUAN_NAME, name);
			setStringtForKey(LOCAL_CONFIGKEY_DUIHUAN_TEL, tel);
			setStringtForKey(LOCAL_CONFIGKEY_DUIHUAN_ADDR, address);
			params.put("udevicesId", Util.getDeviceId(getApplicationContext()));
			params.put("productId", mCurrentProductId);
			params.put("telephone", tel);
			params.put("uname", name);
			params.put("uaddress", address);
			startRequest(SERVER_URL_DUIHUAN_DEFAULT_INFO, params);
		}
	}
	@Override
	public void onHttpStart() {
		super.onHttpStart();
	}
	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		DomainObject obj = new Gson().fromJson(content, DomainObject.class);
		if(obj != null  && obj.getResultCode().equals(HTTP_RESULE_SUCCESS)){
			//如果兑换成功
			finish();
			//重新加载余额
			
		}
		showToast(obj.getResultMsg());
	}
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		showNetErrorToast(strMsg, t);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.commit_bt:
			duihuan();
			break;
		case R.id.closeButton:
			finish();
			break;
		default:
			break;
		}
	}
}
