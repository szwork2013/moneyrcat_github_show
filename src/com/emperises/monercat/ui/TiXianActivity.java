package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class TiXianActivity extends OtherBaseActivity {

	private Button mMXButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian);
		setCurrentTitle(getString(R.string.woyaotixian));
	}

	@Override
	protected void initViews() {
		super.initViews();
		mMXButton = (Button) findViewById(R.id.mingxi_button);
		mMXButton.setText("提现记录");
		mMXButton.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		//如果没有完善过个人资料
		float currentBanlnce = Float.parseFloat(queryLocalBalance());
		Intent i = new Intent(this , TiXianDialogActivity.class);
		switch (v.getId()) {
		case R.id.tixian_rel_one:
			if(currentBanlnce < 100){
				showToast("您的余额还不够提现10元哦!赶快点击广告赚取瞄币吧!");
				return;
			}
			i.putExtra(INTENT_KEY_TIXIAN_TYPE, "10");
			break; 
		case R.id.tixian_rel_two:
			if(currentBanlnce < 500){
				showToast("您的余额还不够提现50元哦!赶快点击广告赚取瞄币吧!");
				return;
			}
			i.putExtra(INTENT_KEY_TIXIAN_TYPE, "50");
			break;
		case R.id.tixian_rel_three:
			if(currentBanlnce < 1000){
				showToast("您的余额还不够提现100元哦!赶快点击广告赚取瞄币吧!");
				return;
			}
			i.putExtra(INTENT_KEY_TIXIAN_TYPE, "100");
			break;
		default:
			break;
		}
		startActivity(i);
	}

}
