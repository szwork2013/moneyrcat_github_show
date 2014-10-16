package com.emperises.monercat.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.interfacesandevents.HeaderImageEvent;
import com.emperises.monercat.ui.v3.ActivityMyInfo;
import com.emperises.monercat.utils.Util;

public class WoDeTabActivity extends BaseActivity {

	private ImageView mHeaderImage;
	private TextView genderAgeAddr;
	private TextView mNicknameText;
	private TextView mTelText;
	private int wh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wodetable);
	}

	@SuppressLint("NewApi")
	@Override
	protected void initViews() {
		HeaderImageEvent.getInstance().addHeaderImageListener(this);
		wh = Util.dip2px(70, this);
		mHeaderImage = (ImageView) findViewById(R.id.headerImage);
		displayHeaderImage(mHeaderImage, wh, wh);
		genderAgeAddr = (TextView) findViewById(R.id.mytab_genderageaddr);
		mNicknameText = (TextView) findViewById(R.id.mytab_nickname);
		mTelText = (TextView) findViewById(R.id.mytab_tel);
		setMyInfo();
	}

	@Override
	public void onHeaderImageChange(String path) {
		super.onHeaderImageChange(path);
		displayHeaderImage(mHeaderImage, wh, wh);
	}
	@SuppressLint("NewApi")
	private void setMyInfo() {
		ZcmUser info = getMyInfoForDatabase();
		if(info != null){
			String gender = info.getUsex() + " ";
			String age = info.getUage();
			String addr = " "+info.getUaddress();
			String append = gender+age+addr;
			genderAgeAddr.setText(append);
			mNicknameText.setText(info.getUname());
			mTelText.setText(info.getUtelephone());
		}
	}

	@Override
	public void onMyInfoChange(ZcmUser info) {
		super.onMyInfoChange(info);
		setMyInfo();
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.wodeshenling:
			startActivityWithAnimation(new Intent(this, WoDebActivity.class));
			break;
		case R.id.woyaotixian:
			startActivityWithAnimation(new Intent(this, TiXianActivity.class));
			break;
		case R.id.wodeinfo:
			startActivityWithAnimation(new Intent(this, ActivityMyInfo.class));
			break;
		case R.id.chaozhiduihuan:
			startActivityWithAnimation(new Intent(this, DuiHuanActivity.class));
			break;

		default:
			break;
		}
	}
}
