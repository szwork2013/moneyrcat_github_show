package com.emperises.monercat.ui;

import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.utils.Util;

public class DuiHuanDialogActivity extends OtherBaseActivity {

	private EditText mDuihuanAddress;
	private EditText mDuihuanName;
	private EditText mDuihuanTel;
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
	}
	@SuppressLint("NewApi")
	private void duihuan(){
		String name = mDuihuanName.getText().toString();
		String address = mDuihuanAddress.getText().toString();
		String tel = mDuihuanTel.getText().toString();
		if(name.isEmpty() || address.isEmpty() || tel.isEmpty()){
			showToast("您的信息不完整");
		} else {
			
			AjaxParams params = new AjaxParams();
			params.put("udevicesId", Util.getDeviceId(getApplicationContext()));
			startRequest("", params);
			
		}
	}
	@Override
	public void onHttpStart() {
		super.onHttpStart();
	}
	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		//如果兑换成功
		finish();
	}
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
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
