package com.emperises.monercat.ui;

import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class RecommendActivity extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
		setCurrentTitle(getString(R.string.recommend_title));
	}
	@Override
	protected void initViews() {
		
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.copy_link_but:
			showToast(R.string.copylikestr);
			break;
		case R.id.copy_code_bt:
			showToast(R.string.copylikestr);
			break;
		case R.id.wxpengyouquan:
			openShare();
			break;

		default:
			break;
		}
	}

	
}
