package com.emperises.monercat.ui.v3;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.interfacesandevents.EditMyInfoInterface;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class ActivityEditMyinfo extends OtherBaseActivity implements
		EditMyInfoInterface {
	private TextView mAddressText;
	private TextView mAgeText;
	private TextView mGenderText;
	private TextView mNicknameText;
	private ZcmUser mInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editmyinfo);
		setCurrentTitle("编辑信息");

	}

	@Override
	protected void initViews() {
		super.initViews();
		ZcmUser info = getMyInfoForDatabase();
		mAddressText = (TextView) findViewById(R.id.editinfo_addresstext);
		mAgeText = (TextView) findViewById(R.id.editinfo_agetext);
		mGenderText = (TextView) findViewById(R.id.editinfo_gendertext);
		mNicknameText = (TextView) findViewById(R.id.editinfo_nicknametext);
		mInfo = getDatabaseInterface().getMyInfo();
		if (info != null) {
			mAddressText.setText(info.getUaddress());
			mAgeText.setText(info.getUage());
			mGenderText.setText(info.getUsex());
			mNicknameText.setText(info.getUname());
		}
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, ActivityEditText.class);
		// mAgeText.setText(age);
		// mNicknameText.setText(nickNmae);
		// mGenderText.setText(gender);
		// mAddressText.setText(address);
		super.onClick(v);
		switch (v.getId()) {
		case R.id.editinfo_address:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_address);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mAddressText.getText().toString());
			startActivity(i);
			break;
		case R.id.editinfo_age:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_age);
			i.putExtra(INTENT_KEY_EDIT_VALUE, replaceAgeStringEmpty(mInfo.getUage()));
			startActivity(i);
			break;
		case R.id.editinfo_gender:
			showGenderDialog();
//			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_gender);
//			i.putExtra(INTENT_KEY_EDIT_VALUE, mGenderText.getText().toString());
//			startActivity(i);
			break;
		case R.id.editinfo_nickname:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_nickname);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mNicknameText.getText()
					.toString());
			startActivity(i);
			break;
		case R.id.editinfo_done_button:
			finish();
			// //修改完成将数据插入到数据库中
			saveMyInfo();
			break;
		default:
			break;
		}

	}

	private int mCurrentGengerSelected = 0;
	private void showGenderDialog() {
		final String[] items = getResources().getStringArray(R.array.sexitem);
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("请选择");
		dialog.setSingleChoiceItems(items, mCurrentGengerSelected,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Logger.i("SELECTED", "current which = " + which);
						mCurrentGengerSelected = which;
						mInfo.setUsex(items[which]);
						mGenderText.setText(mInfo.getUsex());
						dialog.dismiss();
					}

				});
		dialog.show();
	}

	private String replaceAgeStringEmpty(String age){
		if(age.contains("岁")){
			age = age.replace("岁", "");
		} 
		return age;
	}
	private void saveMyInfo() {
		// 提交到服务器
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		params.put("action_type", "0");
		params.put("uname", mInfo.getUname());
		params.put("usex", mInfo.getUsex());
		params.put("uadress", mInfo.getUaddress());
		params.put("uage", replaceAgeStringEmpty(mInfo.getUage()));
		params.put("file", "");
		getHttpClient().post(SERVER_URL_SAVEUSERINFO, params,
				new AjaxCallBack<String>() {
					@Override
					public void onSuccess(String t) {
						Logger.i("USERINFO", t);
						DomainObject ret = new Gson().fromJson(t,
								DomainObject.class);
						if (ret.getResultCode().equals(HTTP_RESULE_SUCCESS)) {
							showCommitOkToast();
							getDatabaseInterface().saveMyInfo(mInfo, ActivityEditMyinfo.this);
						} else {
							showNetErrorToast(ret.getResultMsg(),null);
						}
						super.onSuccess(t);
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						Logger.e("ERROR", strMsg, t);
						showNetErrorToast(strMsg,t);
					}
				});
		
	}

	@Override
	public void onAgeEditAfter(String age) {
		mAgeText.setText(age + "岁");
		mInfo.setUage(age);
	}

	@Override
	public void onNickNameEditAfter(String nickNmae) {
		mNicknameText.setText(nickNmae);
		mInfo.setUname(nickNmae);
	}

	@Override
	public void onGenderEditAfter(String gender) {
		mGenderText.setText(gender);
		mInfo.setUsex(gender);
	}

	@Override
	public void onAddressEditAfter(String address) {
		mAddressText.setText(address);
		mInfo.setUaddress(address);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
