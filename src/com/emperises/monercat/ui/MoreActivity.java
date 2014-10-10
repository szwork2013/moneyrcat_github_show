package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.ui.v3.ShangWuHeZuoActivity;
import com.emperises.monercat.utils.Util;

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
			//TODO:进入引导界面
			break;
		case R.id.moreCheckUpdate:
			Util.checkUpdateVersion(this, SERVER_URL_UPDATE_VERSION);
			break;
		case R.id.more_About:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case R.id.more_shangwuhezuo:
			startActivity(new Intent(this, ShangWuHeZuoActivity.class));
			break;
		default:
			break;
		}
	}
}
