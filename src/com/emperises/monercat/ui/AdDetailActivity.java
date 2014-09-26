package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class AdDetailActivity extends  OtherBaseActivity{

	private Button mShenLing;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addetail);
		setCurrentTitle(getString(R.string.guanggaoxiangqing));
	}
	@Override
	protected void initViews() {
		mShenLing = (Button) findViewById(R.id.shenling_but);
		mShenLing.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shenling_but:
			startActivity(new Intent(this , ShenLingActivity.class));
			break;
		case R.id.woyaocanjia_button:
			startActivity(new Intent(this , WYCJDialogActivity.class));
			break;

		default:
			break;
		}
	}
}
