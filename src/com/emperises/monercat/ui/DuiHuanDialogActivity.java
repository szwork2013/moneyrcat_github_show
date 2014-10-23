package com.emperises.monercat.ui;

import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.ZcmProduct;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class DuiHuanDialogActivity extends OtherBaseActivity {

	private EditText mDuihuanAddress;
	private EditText mDuihuanName;
	private EditText mDuihuanTel;
	private ZcmProduct mProdInfo;
	private EditText mDuihuanCountText;
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
		mDuihuanCountText = (EditText) findViewById(R.id.duihuan_count);
		String localName = getStringValueForKey(LOCAL_CONFIGKEY_DUIHUAN_NAME);
		String localAddr = getStringValueForKey(LOCAL_CONFIGKEY_DUIHUAN_ADDR);
		String localTel = getStringValueForKey(LOCAL_CONFIGKEY_DUIHUAN_TEL);
		mDuihuanAddress.setText(localAddr);
		mDuihuanName.setText(localName);
		mDuihuanTel.setText(localTel);
		mProdInfo = (ZcmProduct) getIntent().getSerializableExtra(INTENT_KEY_PRODUCINFO);
	}
	@SuppressLint("NewApi")
	private void duihuan(){
		String name = mDuihuanName.getText().toString();
		String address = mDuihuanAddress.getText().toString();
		String tel = mDuihuanTel.getText().toString();
		String count = mDuihuanCountText.getText().toString();
		
		if(TextUtils.isEmpty(name) || TextUtils.isEmpty(tel)){
			showToast("您的信息不完整");
		} else {
			
			AjaxParams params = new AjaxParams();
			setStringtForKey(LOCAL_CONFIGKEY_DUIHUAN_NAME, name);
			setStringtForKey(LOCAL_CONFIGKEY_DUIHUAN_TEL, tel);
			setStringtForKey(LOCAL_CONFIGKEY_DUIHUAN_ADDR, address);
			params.put(POST_KEY_DEVICESID, Util.getDeviceId(getApplicationContext()));
			params.put("productId", mProdInfo.getPid());
			params.put("telephone", tel);
			params.put("uname", name);
			params.put("uaddress", address);
//			params.put("ucont", address);//TODO:增加兑换数量。判断兑换上限
			
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
			updateBalance();
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
