package com.emperises.monercat.ui;

import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class TiXianDialogActivity extends OtherBaseActivity {

	private String tixianId;
	private TextView tixianTitle;
	private TextView mNameText;
	private TextView mBankIdText;
	private TextView mBankAddressText;
	private Button mCommitBt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian_dialog);
		setTitleHide(View.GONE);
	}
	@Override
	protected void initViews() {
		tixianTitle = (TextView) findViewById(R.id.tixianTitle);
		mCommitBt = (Button) findViewById(R.id.commit_bt);
		mNameText = (TextView) findViewById(R.id.tixian_name);
		mBankIdText = (TextView) findViewById(R.id.bank_id);
		mBankAddressText = (TextView) findViewById(R.id.bank_address);
		String name = getStringValueForKey(LOCAL_CONFIGKEY_BANK_NAME);
		String addr = getStringValueForKey(LOCAL_CONFIGKEY_BANK_ADDR);
		String card = getStringValueForKey(LOCAL_CONFIGKEY_BANK_CARD);
		mNameText.setText(name);
		mBankAddressText.setText(addr);
		mBankIdText.setText(card);
		tixianId = getIntent().getStringExtra(INTENT_KEY_TIXIAN_ID);
	}
	@Override
	public void onHttpStart() {
		super.onHttpStart();
		tixianTitle.setText(getString(R.string.loading_dialog));
		mCommitBt.setClickable(false);
	}
	@Override
	public void onFinished(String content) {
		mCommitBt.setClickable(true);
		DomainObject obj = new Gson().fromJson(content, DomainObject.class);
		if(obj != null){
			if(obj.getResultCode().equals(HTTP_RESULE_SUCCESS)){
				tixianTitle.setText("提交成功");
				//保存上次提交的信息
				showCommitOkToast();
				//更新余额
				updateBalance();
			}else{
				showToast(obj.getResultMsg());
			}
		}else{
			showErrorToast();
		}
		super.onFinished(content);
	}
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		showNetErrorToast(strMsg, t);
		tixianTitle.setText(R.string.errortoast);
		mCommitBt.setClickable(true);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.closeButton:
			finish();
			break;
		case R.id.commit_bt:
			tixian();
			
			break;
		default:
			break;
		}
	}
	private void tixian() {
		String bankAddr = mBankAddressText.getText().toString();//开户行
		String name = mNameText.getText().toString();
		String bankNumber = mBankIdText.getText().toString();
		setStringtForKey(LOCAL_CONFIGKEY_BANK_ADDR, bankAddr);
		setStringtForKey(LOCAL_CONFIGKEY_BANK_CARD, bankNumber);
		setStringtForKey(LOCAL_CONFIGKEY_BANK_NAME, name);
		if (!TextUtils.isEmpty(bankNumber) && !TextUtils.isEmpty(bankAddr) && !TextUtils.isEmpty(name) ) {
			AjaxParams params = new AjaxParams();
			params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
			params.put("ubankno", bankNumber);
			params.put("ubankName", bankAddr);
			params.put("uname", name);
			params.put("pId", tixianId);
			startRequest(SERVER_URL_TIXIAN, params);
			finish();
		} else {
			showToast("信息不完整");
		}
		
	}

}
