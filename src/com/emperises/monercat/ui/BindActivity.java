package com.emperises.monercat.ui;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.SmsCode;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;


public class BindActivity extends OtherBaseActivity {

	private int mTimerCount = 60;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			mSendCodeButton.setText("("+mTimerCount --+")");
			if(mTimerCount == 0){
				mSendCodeButton.setClickable(true);
				mTelEditText.setEnabled(true);
				mSendCodeButton.setText(getResString(R.string.sendcode));
				mHandler.removeCallbacks(mTimerRunnable);
				mTimerCount = 60;
			}
		}
	};
	private void startTimer() {
		//开启倒计时定时器
		mTelEditText.setEnabled(false);
		mSendCodeButton.setClickable(false);
		mHandler.postDelayed(mTimerRunnable, 1000);
	}
	private Runnable mTimerRunnable = new Runnable() {
		@Override
		public void run() {
			mHandler.postDelayed(this, 1000);
			mHandler.sendEmptyMessage(0);
		}
	};
	private Button mSendCodeButton;
	private EditText mTelEditText;
	private EditText mCodeEdit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind);
		setCurrentTitle(getString(R.string.bindteltext));
	}
	@Override
	protected void initViews() {
		super.initViews();
		mSendCodeButton = (Button) findViewById(R.id.bindCheckCodeButton);
		mTelEditText = (EditText) findViewById(R.id.bindEditTel);
		mCodeEdit = (EditText) findViewById(R.id.code_text);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bind_button:
			bind();
			
			break;
		case R.id.bindCheckCodeButton:
			String tel = mTelEditText.getText().toString();
			if(!TextUtils.isEmpty(tel) && tel.length() == 11){
				//发送验证码申请
				sendSmsCode(tel);
			} else {
				showToast("请输入完整的手机号码!");
			}
			break;

		default:
			break;
		}
	}
	private void bind() {
		String currentCode = mCodeEdit.getText().toString();
		if(!TextUtils.isEmpty(currentCode) && currentCode.equals(code)){
			//如果验证成功
			final String tel = mTelEditText.getText().toString();
			AjaxParams params = new AjaxParams();
			params.put("udevicesId", Util.getDeviceId(this));
			params.put("utelephone", tel);
			params.put("smsCode", currentCode);
			getHttpClient().post(SERVER_URL_BINDPHONE, params,new AjaxCallBack<String>() {
				@Override
				public void onStart() {
					super.onStart();
				}
				@Override
				public void onSuccess(String t) {
					DomainObject d = new Gson().fromJson(t, DomainObject.class);
					if(d != null && d.getResultCode().equals(HTTP_RESULE_SUCCESS)){
						showToast(d.getResultMsg());
						Intent i = new Intent(BindActivity.this, BindActivity_1.class);
						i.putExtra(INTENT_KEY_TEL, tel);
						startActivity(i);
					}
					super.onSuccess(t);
				} 
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					showNetErrorToast(strMsg, t);
				}
			});
		} else {
			showToast("验证码错误!");
		}
	}
	private String code;
	private void sendSmsCode(String tel) {
		AjaxParams params = new AjaxParams();
		params.put("telephone", tel);
		getHttpClient().post(SERVER_URL_SMSCODE, params,new AjaxCallBack<String>() {
			@Override
			public void onStart() {
				super.onStart();
				startTimer();
			}
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				SmsCode d = new Gson().fromJson(t, SmsCode.class);
				if(d != null){
					showToast(d.getResultMsg());
					code = d.getVal();
					Logger.i("CODE", code);
				} 
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
			}
		});
	}
}
