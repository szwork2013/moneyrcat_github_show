package com.emperises.monercat.ui.v3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.interfacesandevents.HeaderImageEvent;
import com.emperises.monercat.ui.MingXiActivity;
import com.emperises.monercat.utils.Util;

@SuppressLint("NewApi")
public class ActivityMyInfo extends OtherBaseActivity {
	private ImageView mHeadImage;
	private TextView mInfoNicknameText;
	private TextView mCurrentBalance;
	private TextView mGenderAgeAddr;
	private TextView mRecommendCode;
	private int wh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HeaderImageEvent.getInstance().addHeaderImageListener(this);
		setContentView(R.layout.activity_myinfo);
		setCurrentTitle(getString(R.string._myinfo_title));
	}

	@Override
	public void onHeaderImageChange(String path) {
		super.onHeaderImageChange(path);
		displayHeaderImage(mHeadImage, wh, wh);
	}
	@Override
	protected void initViews() {
		super.initViews();
		mHeadImage = (ImageView) findViewById(R.id.headerImage);
		wh = Util.dip2px(70, this);
		displayHeaderImage(mHeadImage, wh, wh);
		mInfoNicknameText = (TextView) findViewById(R.id.myinfo_nickname);
		mCurrentBalance = (TextView) findViewById(R.id.myinfo_balance);
		mCurrentBalance.setText(queryLocalBalance()+getString(R.string.m_gold));
		mGenderAgeAddr = (TextView) findViewById(R.id.myinfo_normalinfo);
		mRecommendCode = (TextView) findViewById(R.id.myinfo_recommend_code);
		setMyInfo();
	}

	private void setMyInfo() {
		ZcmUser info = getMyInfoForDatabase();
		if(info != null){
			String gender = info.getUsex() + " ";
			String age = info.getUage() + " ";
			String addr = info.getUaddress();
			mRecommendCode.setText(info.getUtgm());
			mGenderAgeAddr.setText(gender+age+addr);
			mInfoNicknameText.setText(info.getUname());
			
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.headerImage:
			startActivity(new Intent(this , ActivityEditMyinfo.class));
			break;
		case R.id.myinfo_yue:
			startActivity(new Intent(this , MingXiActivity.class));
			break;
		case R.id.myinfo_erweima:
			startActivity(new Intent(this , ActivityQRCode.class));
			break;
		case R.id.myinfo_edit:
			startActivity(new Intent(this , ActivityEditMyinfo.class));
			break;
//		case R.id.myinfo_tel:
//				startActivity(new Intent(this , BindActivity.class));
//			break;
		default:
			break;
		}
	}
	@Override
	public void onMyInfoChange(ZcmUser info) {
		setMyInfo();
	}
}
