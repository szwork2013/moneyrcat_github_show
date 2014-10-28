package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.ZcmUser;

public class BindActivity_1 extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_1);
	}
	@Override
	protected void initViews() {
		super.initViews();
		TextView telMsg = (TextView) findViewById(R.id.telBindMsg);
		String tel = getIntent().getStringExtra(INTENT_KEY_TEL);
		telMsg.setText("已经绑定手机号:"+tel);
		//将手机号码添加到数据库
		ZcmUser user = new ZcmUser();
		user.setUtelephone(tel);
		getDatabaseInterface().saveMyInfo(user, this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bind_done_button:
			//存储本地绑定标志
			finish();
			break;
		case R.id.bind_jump_safe_button:
			String number = getStringValueForKey(LOCAL_CONFIGKEY_SAFE_NUMBER);
			if(TextUtils.isEmpty(number)){
				startActivity(new Intent(this,BindActivity_Safe.class));
			} else {
				showToast("您已经完善过安全信息。");
			}
			break;

		default:
			break;
		}
	}
}
