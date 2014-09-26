package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.MainActivity;
import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class BindActivity_1 extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_1);
	}
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bind_done_button:
			//存储本地绑定标志
			setBooleanForKey(LOCAL_CONFIGKEY_BIND_FLG, true);
			startActivity(new Intent(this,MainActivity.class));
			break;
		case R.id.bind_jump_safe_button:
			startActivity(new Intent(this,BindActivity_Safe.class));
			break;

		default:
			break;
		}
	}
}
