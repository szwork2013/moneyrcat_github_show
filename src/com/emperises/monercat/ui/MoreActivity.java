package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;

public class MoreActivity extends BaseActivity {
	private TextView mBindText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			changeBindText();
		}
	}
	@Override
	protected void initViews() {
		changeBindText();
	}
	private void changeBindText() {
		mBindText = (TextView) findViewById(R.id.more_bind_text);
		if(getBoleanValueForKey(LOCAL_CONFIGKEY_BIND_FLG)){
			mBindText.setText("更改绑定");
		}else{
			mBindText.setText("绑定手机");
		}
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.more_bind:
			if(getBoleanValueForKey(LOCAL_CONFIGKEY_BIND_FLG)){
				startActivity(new Intent(this , ChangeBindActivity.class));
			}else{
				startActivity(new Intent(this , BindActivity.class));
			}
			break;
		case R.id.more_recommend:
			openShareForZcm();
			break;
		case R.id.more_haoping:
			showToast("感谢您的好评!我们会继续努力");
			break;
		case R.id.moreCheckUpdate:
			showToast(R.string.more_update_text);
			break;
		case R.id.more_About:
			startActivity(new Intent(this,AboutActivity.class));
			break;
		default:
			break;
		}
	}
}
