package com.emperises.monercat.ui;

import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.ZcmProduct;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class DuiHuanDialogActivity extends OtherBaseActivity {

	private EditText mDuihuanAddress;
	private EditText mDuihuanName;
	private EditText mDuihuanTel;
	private ZcmProduct mProdInfo;
	private EditText mDuihuanCountText;
	private int mProductCount = 1;
//	private Button mAddButton;
//	private Button mDecButton;
	private TextView mDuihuanTitle;
	private Button mCommitBt;
	private float mCurrentBalance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuan_dialog_v3);
	}
	@Override
	protected void initViews() {
		super.initViews();
		mCurrentBalance = Float.parseFloat(queryLocalBalance());
		mDuihuanTitle = (TextView) findViewById(R.id.duihuanTitle);
		mDuihuanAddress = (EditText) findViewById(R.id.duihuan_address);
		mDuihuanName = (EditText) findViewById(R.id.duihuan_name);
		mDuihuanTel = (EditText) findViewById(R.id.duihuan_tel_edit);
		mCommitBt = (Button) findViewById(R.id.commit_bt);
		mDuihuanCountText = (EditText) findViewById(R.id.duihuan_count);
//		mAddButton = (Button) findViewById(R.id.add);
//		mAddButton.setOnLongClickListener(this);
//		mDecButton = (Button) findViewById(R.id.dec);
//		mDecButton .setOnLongClickListener(this);
		mDuihuanCountText.setEnabled(false);
		String localName = getStringValueForKey(LOCAL_CONFIGKEY_DUIHUAN_NAME);
		String localAddr = getStringValueForKey(LOCAL_CONFIGKEY_DUIHUAN_ADDR);
		String localTel = getStringValueForKey(LOCAL_CONFIGKEY_DUIHUAN_TEL);
		if(TextUtils.isEmpty(localName)){
			//用户第一次兑换
			ZcmUser mInfo = getDatabaseInterface().getMyInfo();
			localTel = mInfo.getUtelephone();
			localAddr = mInfo.getUaddress();
			String n = getStringValueForKey(LOCAL_CONFIGKEY_SAFE_NAME);
			if(!TextUtils.isEmpty(n)){
				//如果绑定了安全信息,直接用姓名
				localName = n;
			}
		}
		mDuihuanAddress.setText(localAddr);
		mDuihuanName.setText(localName);
		mDuihuanTel.setText(localTel);
		mProdInfo = (ZcmProduct) getIntent().getSerializableExtra(INTENT_KEY_PRODUCINFO);
		String m = mProdInfo.getP_max_dh_num();
		int max = Integer.parseInt(m);
		if(max == 0){
			mProdInfo.setP_max_dh_num(mProdInfo.getPnum());
		}
		
	}
	@SuppressLint("NewApi")
	private void duihuan(){
		String name = mDuihuanName.getText().toString();
		String address = mDuihuanAddress.getText().toString();
		String tel = mDuihuanTel.getText().toString();
		String count = mDuihuanCountText.getText().toString();
		
		if(TextUtils.isEmpty(name) || TextUtils.isEmpty(tel)){
			showToast("您的信息不完整");
		} else {
			
			AjaxParams params = new AjaxParams();
			setStringtForKey(LOCAL_CONFIGKEY_DUIHUAN_NAME, name);
			setStringtForKey(LOCAL_CONFIGKEY_DUIHUAN_TEL, tel);
			setStringtForKey(LOCAL_CONFIGKEY_DUIHUAN_ADDR, address);
			params.put(POST_KEY_DEVICESID, Util.getDeviceId(getApplicationContext()));
			params.put("productId", mProdInfo.getPid());
			params.put("telephone", tel);
			params.put("uname", name);
			params.put("uaddress", address);
			params.put("num", count);
			startRequest(SERVER_URL_DUIHUAN_DEFAULT_INFO, params);
		}
	}
	@Override
	public void onHttpStart() {
		super.onHttpStart();
		mDuihuanTitle.setText(R.string.loading_dialog);
		mCommitBt.setClickable(false);
	}
	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		mCommitBt.setClickable(true);
		DomainObject obj = new Gson().fromJson(content, DomainObject.class);
		if(obj != null  && obj.getResultCode().equals(HTTP_RESULE_SUCCESS)){
			//如果兑换成功
			finish();
			//重新加载余额
			updateBalance();
		}
		showToast(obj.getResultMsg());
		mDuihuanTitle.setText(R.string.duihuan_str);
	}
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		showNetErrorToast(strMsg, t);
		mDuihuanTitle.setText(R.string.errortoast);
		mCommitBt.setClickable(true);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.add:
			addProdCount();
			break;
		case R.id.dec:
			resetProdCount();
			break;
			
		case R.id.commit_bt:
				duihuan();
			break;
		case R.id.closeButton:
			finish();
			break;
		default:
			break;
		}
	}
	private void resetProdCount() {
		if(mProductCount != 1){
			mProductCount --;
			mDuihuanCountText.setText(mProductCount+"");
		}
	}
//	@Override
//	public boolean onLongClick(View view) {
//		switch (view.getId()) {
//		case R.id.add:
//			//获得商品可兑换最大数量
//			mDuihuanCountText.setText();
//			mProductCount = Integer.parseInt(mProdInfo.getP_max_dh_num());
//			break;
//		case R.id.dec:
//			//1
//			mProductCount = 1;
//			mDuihuanCountText.setText("1");
//			break;
//
//		default:
//			break;
//		}
//		return true ;
//	}
	private void addProdCount() {
		int max = Integer.parseInt(mProdInfo.getP_max_dh_num());
		if(mProductCount != max){
			//如果总数超过余额总数
			mProductCount ++; 
			if((mProductCount * Float.parseFloat(mProdInfo.getPprice())) < mCurrentBalance){
				mDuihuanCountText.setText(mProductCount+"");
			}
		}
	}
}
