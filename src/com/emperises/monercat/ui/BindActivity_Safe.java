package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.MainActivity;
import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;


public class BindActivity_Safe extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_safe);
	}
	@Override
	protected void initViews() {
		super.initViews();
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.commit_bt:
			showToast("提交成功");
			setBooleanForKey(LOCAL_CONFIGKEY_BIND_FLG, true);
			startActivity(new Intent(this , MainActivity.class));
			break;
		case R.id.closeButton:
			finish();
			break;
		default:
			break;
		}
	}
}
