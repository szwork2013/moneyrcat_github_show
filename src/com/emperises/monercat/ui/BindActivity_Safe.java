package com.emperises.monercat.ui;

import java.io.File;
import java.io.FileNotFoundException;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class BindActivity_Safe extends OtherBaseActivity {

	private EditText mSafeName;
	private EditText mSafeNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_safe);
	}

	@Override
	protected void initViews() {
		super.initViews();
		mSafeName = (EditText) findViewById(R.id.safe_name);
		mSafeNumber = (EditText) findViewById(R.id.safe_number);
	}

	private void saveInfo() {
		final String name = mSafeName.getText().toString();
		final String number = mSafeNumber.getText().toString();
		if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
			AjaxParams params = new AjaxParams();
			ZcmUser mInfo = getDatabaseInterface().getMyInfo();
			params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
			params.put("action_type", "0");
			params.put("uidentity", number);
			params.put("uname", name);
			params.put("usex", mInfo.getUsex());
			params.put("uadress", mInfo.getUaddress());
			params.put("uage", replaceAgeStringEmpty(mInfo.getUage()));
			try {
				params.put("file", new File(""));
			} catch (FileNotFoundException e) { 
				e.printStackTrace();
			}
			getHttpClient().post(SERVER_URL_SAVEUSERINFO, params,
					new AjaxCallBack<String>() {
						@Override
						public void onStart() {
							super.onStart();
						}

						@Override
						public void onSuccess(String t) {
							Logger.i("USERINFO", t);
							DomainObject ret = new Gson().fromJson(t,
									DomainObject.class);
							if (ret.getResultCode().equals(HTTP_RESULE_SUCCESS)) {
								//保存本地信息
								setStringtForKey(LOCAL_CONFIGKEY_SAFE_NAME, name);
								setStringtForKey(LOCAL_CONFIGKEY_SAFE_NUMBER, number);
								showCommitOkToast();
								finish();
							} else {
								showNetErrorToast(ret.getResultMsg(), null);
							}
							super.onSuccess(t);
						}

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							super.onFailure(t, errorNo, strMsg);
							Logger.e("ERROR", strMsg, t);
							showNetErrorToast(strMsg, t);
						}
					});

		} else {
			showToast("信息不完整!");
		}

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.commit_bt:
			saveInfo();
			break;
		case R.id.closeButton:
			finish();
			break;
		default:
			break;
		}
	}
}
