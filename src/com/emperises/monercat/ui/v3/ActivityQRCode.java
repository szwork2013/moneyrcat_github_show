package com.emperises.monercat.ui.v3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.utils.Util;

@SuppressLint("NewApi")
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
		int wh = Util.dip2px(70, this);
		displayHeaderImage(headerImage, wh, wh);
		TextView nickname = (TextView) findViewById(R.id.qr_nickname);
		ZcmUser  info = getMyInfoForDatabase();
		if(info != null){
			nickname.setText(info.getUname());
		}
	}
	
}
