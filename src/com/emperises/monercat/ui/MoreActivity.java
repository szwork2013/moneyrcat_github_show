package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.ui.v3.ActivityFeedBack_v2;
import com.emperises.monercat.ui.v3.ActivityMessageList;
import com.emperises.monercat.ui.v3.CopyRightActivity;
import com.emperises.monercat.ui.v3.ShangWuHeZuoActivity;
import com.emperises.monercat.ui.v3.WelcomeActivity;

public class MoreActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
	}

	@Override
	protected void initViews() {
		
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.more_navication:
			startActivity(new Intent(this, WelcomeActivity.class));
			break;
		case R.id.more_About:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case R.id.more_shangwuhezuo:
			startActivity(new Intent(this, ShangWuHeZuoActivity.class));
			break;
		case R.id.more_feedback:
			startActivity(new Intent(this, ActivityFeedBack_v2.class));
			break;
		case R.id.more_messagecenter:
			startActivity(new Intent(this, ActivityMessageList.class));
			break;
		case R.id.copyright_info:
			startActivity(new Intent(this, CopyRightActivity.class));
			break;
		default:
			break;
		}
	}
}
