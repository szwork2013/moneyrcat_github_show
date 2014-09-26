package com.emperises.monercat.ui;

import net.tsz.afinal.http.AjaxParams;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class TiXianDialogActivity extends OtherBaseActivity {

	private Button mCloseButton;
	private EditText mTiXianEditText;
	private String tixianBalance;
	private TextView tixianTitle;
	private TextView mNameText;
	private TextView mBankIdText;
	private TextView mBankAddressText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian_dialog);
		setTitleHide(View.GONE);
	}
	@Override
	protected void initViews() {
		tixianTitle = (TextView) findViewById(R.id.tixianTitle);
		mCloseButton = (Button) findViewById(R.id.closeButton);
		mTiXianEditText = (EditText) findViewById(R.id.tixian_balance_edittext);
		mNameText = (TextView) findViewById(R.id.tixian_name);
		mBankIdText = (TextView) findViewById(R.id.bank_id);
		mBankAddressText = (TextView) findViewById(R.id.bank_address);
		tixianBalance = getIntent().getStringExtra(INTENT_KEY_TIXIAN_TYPE);
		mTiXianEditText.setEnabled(false);
		mTiXianEditText.setText(tixianBalance+"元");
	}
	@Override
	public void onHttpStart() {
		super.onHttpStart();
		tixianTitle.setText(getString(R.string.loading));
	}
	@Override
	public void onFinished(String content) {
		DomainObject obj = new Gson().fromJson(content, DomainObject.class);
		if(obj != null){
			if(obj.getResultCode().equals(HTTP_RESULE_SUCCESS)){
				tixianTitle.setText("提交成功");
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
		tixianTitle.setTextColor(Color.parseColor("#770A13"));
		tixianTitle.setText("提交失败");
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
		if (!TextUtils.isEmpty(bankNumber) && !TextUtils.isEmpty(bankAddr) && !TextUtils.isEmpty(name) ) {
			AjaxParams params = new AjaxParams();
			params.put("udevicesId", Util.getDeviceId(this));
			params.put("ubankno", bankNumber);
			params.put("ubankName", bankAddr);
			params.put("uname", name);
			params.put("money", tixianBalance);
			startRequest(SERVER_URL_TIXIAN, params);
			showToast("提现时错误,请检查输入的金额是否正确");
			finish();
		} else {
			showToast("信息不完整");
		}
		
	}

}
