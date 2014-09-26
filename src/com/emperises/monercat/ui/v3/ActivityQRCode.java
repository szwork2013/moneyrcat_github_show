package com.emperises.monercat.ui.v3;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.ZcmUser;

public class ActivityQRCode extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode);
		setCurrentTitle(getString(R.string.myqrcodestr));
	}
	@Override
	protected void initViews() {
		super.initViews();
		ImageView headerImage = (ImageView) findViewById(R.id.headerImage);
		headerImage.setBackgroundResource(getHeadImageResId());
		TextView nickname = (TextView) findViewById(R.id.qr_nickname);
		ZcmUser  info = getMyInfoForDatabase();
		if(info != null){
			nickname.setText(info.getUname());
		}
	}
	
}
