package com.emperises.monercat.ui.v3;

import java.util.Locale;
import java.util.Random;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;

@SuppressLint("DefaultLocale")
public class CheckCodeActivity extends BaseActivity {

	private EditText mCodeEdit;
	private TextView mCodeText;
	private String mCurrentCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkcode);
	}
	private String[] str = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	@Override
	protected void initViews() {
		TextView title = (TextView) findViewById(R.id.titleText);
		title.setText("验证信息");
		findViewById(R.id.rightItem).setVisibility(View.GONE);
		findViewById(R.id.leftItem).setVisibility(View.GONE);
		mCodeEdit = (EditText) findViewById(R.id.checkCodeEdit);
		mCodeText = (TextView) findViewById(R.id.chechCodeTextView);
		//生成验证码
		StringBuffer code = new StringBuffer();
        for (int i = 0; i < 2; i++) {
        	int max_n=10;
        	int min_n=0;
        	int max_s = str.length;
        	Random random = new Random();
        	int numRan = random.nextInt(max_n)%(max_n-min_n+1) + min_n;//0 - 10
        	int strRan = random.nextInt(max_s)%(max_s-min_n+1) + min_n;//0 - 26
        	code.append(numRan);
        	code.append(str[strRan]);
		}
        mCodeText.setText("验证码:"+code.toString());
        mCurrentCode = code.toString();
	}
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    switch (keyCode) {  
	        case KeyEvent.KEYCODE_BACK:  
	        return true;  
	    }  
	    return super.onKeyDown(keyCode, event);  
	}  
	@Override
	public void onClick(View v) {
		String eCode = mCodeEdit.getText().toString();
		if(!TextUtils.isEmpty(eCode) && (eCode.equals(mCurrentCode.toLowerCase(Locale.getDefault())) || eCode.equals(mCurrentCode.toUpperCase(Locale.getDefault())))){
			finish();
		}else {
			showToast("请输入验证码!");
		}
		super.onClick(v);
	}

}
