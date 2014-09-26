package com.emperises.monercat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emperises.monercat.ui.MingXiActivity;

public class OtherBaseActivity extends BaseActivity {

	private TextView titleText;
	private Button backButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		titleText = (TextView) findViewById(R.id.titleText);
		backButton = (Button) findViewById(R.id.backbutton);
		if(backButton != null){
			backButton.setOnClickListener(this);
			RelativeLayout p = (RelativeLayout) backButton.getParent();
			p.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		Button minxi = (Button) findViewById(R.id.mingxi_button);
		if(minxi != null){
			minxi.setOnClickListener(this);
		}
	}
	@Override
	protected void initViews() {
	}
	protected void setCurrentTitle(String title) {
		if(titleText != null){
			titleText.setText(title);
		}
	}
	protected void setTitleHide(int hide) {
		if(backButton != null){
			RelativeLayout p = (RelativeLayout) backButton.getParent();
			p.setVisibility(hide);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backbutton:
			finish();
			break;
		case R.id.mingxi_button:
			startActivity(new Intent(this , MingXiActivity.class));
			break;
			
		default:
			break;
		}
	}
	

}
