package com.emperises.monercat.customview;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.interfacesandevents.CustomDialogClickEvent.CustomDialogClickEventInterface;

public class CustomDialog extends OtherBaseActivity implements CustomDialogClickEventInterface{

	private TextView mDialogTitle;
	private TextView mDialogMessage;
	private Button mDialogCancleButton;
	private Button mDialogSureButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_dialog);
	}

	@Override
	protected void initViews() {
		
		mDialogTitle = (TextView) findViewById(R.id.dialogTitle);
		mDialogMessage = (TextView) findViewById(R.id.dialogMessage);
		mDialogCancleButton = (Button) findViewById(R.id.dialogCancleButton);
		mDialogSureButton = (Button) findViewById(R.id.dialogSureButton);
		DialogManager dialog = DialogManager.getInstance();
		dialog.setDialogClickInterfaceListener(this);
		CustomDialogConfig config = dialog.getDialogConfig();
		if(config != null){
			String title = config.getTitle();
			String message = config.getMessage();
			mDialogTitle.setText(title);
			mDialogMessage.setText(message);
			View.OnClickListener sure = config.getSureListener();
			View.OnClickListener cancel = config.getCancelListener();
			mDialogSureButton.setOnClickListener(sure);
			mDialogCancleButton.setOnClickListener(cancel);
			if(!TextUtils.isEmpty(config.getSureButtonText())){
				mDialogSureButton.setText(config.getSureButtonText());
			}
			if(!TextUtils.isEmpty(config.getCancleButtonText())){
				mDialogSureButton.setText(config.getCancleButtonText());
			}
		}
	}

	@Override
	public void onSureClick() {
	}

	@Override
	public void onCancleClick() {
	}
	public static abstract class DialogClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			DialogManager.getInstance().fireFinish();
		}
	}
	@Override
	public void finishClick() {
		finish();
	}
}
