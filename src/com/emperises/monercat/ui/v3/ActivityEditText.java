package com.emperises.monercat.ui.v3;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.interfacesandevents.EditMyInfoEvent;

public class ActivityEditText extends OtherBaseActivity {

	private EditText mEditText;
	private int editType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edittext);
		setCurrentTitle("编辑信息");
	}
	@Override
	protected void initViews() {
		super.initViews();
		mEditText = (EditText) findViewById(R.id.edit_text);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		String defaultValue = getIntent().getStringExtra(INTENT_KEY_EDIT_VALUE);
		mEditText.setText(defaultValue);
		editType = getIntent().getIntExtra(INTENT_KEY_EDIT_TYPE, 0);
		if(editType == R.id.editinfo_age){
			mEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		} else if (editType == R.id.editinfo_nickname) {
			mEditText.setFilters(new  InputFilter[]{ new  InputFilter.LengthFilter(10)});
		} 
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.edit_sure:
			// 编辑确定
			String value = mEditText.getText().toString();
			postEditType(editType, value);
			break;
		case R.id.edit_cancel:
			// 编辑取消
			finish();
			break;

		default:
			break;
		}
	}

	private void postEditType(int editType, String value) {
		if (TextUtils.isEmpty(value)) {
			return;
		}
		switch (editType) {
		case R.id.editinfo_address:
			EditMyInfoEvent.getInstance().fireAddressEditEvent(value);
			finish();
			break;
		case R.id.editinfo_age:
			String age = mEditText.getText().toString();
			int iAge = Integer.parseInt(age);
			if( iAge > 100 || iAge < 1){
				showToast("请输入正确的年龄");
			}else{
				EditMyInfoEvent.getInstance().fireAgeEditEvent(value);
				finish();
			}
			break;
		case R.id.editinfo_gender:
			EditMyInfoEvent.getInstance().fireGenderEditEvent(value);
			finish();
			break;
		case R.id.editinfo_nickname:
			EditMyInfoEvent.getInstance().fireNickNameEditEvent(value);
			finish();
			break;
		default:
			break;

		}
	}
}
