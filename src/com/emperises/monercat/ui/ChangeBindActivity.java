package com.emperises.monercat.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emperises.monercat.MainActivity;
import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class ChangeBindActivity extends OtherBaseActivity {

	private int mTimerCount = 60;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mSendCodeButton.setText("(" + mTimerCount-- + ")");
			if (mTimerCount == 0) {
				mSendCodeButton.setClickable(true);
				mTelEditText.setEnabled(true);
				mSendCodeButton.setText(getResString(R.string.sendcode));
				mHandler.removeCallbacks(mTimerRunnable);
				mTimerCount = 60;
			}
		}
	};

	private void startTimer() {
		// 开启倒计时定时器
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changebind);
	}

	@Override
	protected void initViews() {
		super.initViews();
		mSendCodeButton = (Button) findViewById(R.id.bindCheckCodeButton);
		mTelEditText = (EditText) findViewById(R.id.bindEditTel);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.commit_bt:
			showToast("绑定成功");
			startActivity(new Intent(this , MainActivity.class));
			break;
		case R.id.bindCheckCodeButton:
			showToast("验证码已发送");
			startTimer();
			break;
		case R.id.closeButton:
			finish();
			break;
		default:
			break;
		}
	}

}
