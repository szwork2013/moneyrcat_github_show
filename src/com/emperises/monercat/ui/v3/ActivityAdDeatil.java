package com.emperises.monercat.ui.v3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.ui.WoDeAdDetailForList;

public class ActivityAdDeatil extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addetail_v3);
		setCurrentTitle("广告详细");
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		startActivity(new Intent(this , WoDeAdDetailForList.class));
	}
}
