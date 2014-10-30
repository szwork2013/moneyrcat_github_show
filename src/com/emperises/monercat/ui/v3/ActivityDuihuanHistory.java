package com.emperises.monercat.ui.v3;

import android.os.Bundle;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class ActivityDuihuanHistory extends OtherBaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuanhistory);
		setCurrentTitle(R.string.loading);
	}
	@Override
	protected void initViews() {
		//TODO:获取记录列表，隐藏兑换数量。
	}

}
