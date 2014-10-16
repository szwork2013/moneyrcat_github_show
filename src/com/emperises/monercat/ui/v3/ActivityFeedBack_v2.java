package com.emperises.monercat.ui.v3;

import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

@SuppressLint("NewApi")
public class ActivityFeedBack_v2 extends OtherBaseActivity {
	private EditText edittext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_v2);
		setCurrentTitle("意见反馈");
	}
	public void onHttpStart() {
		setCurrentTitle("发送中..");
	};
	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		DomainObject o = new Gson().fromJson(content, DomainObject.class);
		if(o != null){
			showToast(o.getResultMsg());
		}
		setCurrentTitle("意见反馈");
	}
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		setCurrentTitle("发送失败");
	}
	
	private void postFeedBack(String content){
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(getApplicationContext()));
		params.put("fcontent", content);
		startRequest(SERVER_URL_FEEDBACK, params);
	}
	@Override
	protected void initViews() {
		super.initViews();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		edittext = (EditText) findViewById(R.id.edit_text);
	}
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_sure:
            	String content = edittext.getText().toString();
            	if(TextUtils.isEmpty(content)){
            		showToast("请输入反馈信息!");
            	} else {
            		postFeedBack(content);
            	}
                break;
            case R.id.edit_cancel:
            	finish();
            	break;
            default:
                break;
        }
    }
}
