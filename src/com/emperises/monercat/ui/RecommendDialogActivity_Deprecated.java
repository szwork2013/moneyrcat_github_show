package com.emperises.monercat.ui;

import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class RecommendDialogActivity_Deprecated extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend_dialog);
		setTitleHide(View.GONE);
	}
	@Override
	protected void initViews() {
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.closeButton:
			finish();
			break;
		case R.id.commit_bt:
			showCommitOkToast();
			finish();
			break;
		default:
			break;
		}
	}

}
