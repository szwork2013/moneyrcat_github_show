package com.emperises.monercat.customview;

import android.view.View.OnClickListener;

import com.emperises.monercat.customview.CustomDialog.DialogClick;

public class CustomDialogConfig {

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public OnClickListener getSureListener() {
		return sureListener;
	}
	public void setSureListener(DialogClick sureListener) {
		this.sureListener = sureListener;
	}
	public OnClickListener getCancelListener() {
		return cancelListener;
	}
	public void setCancelListener(DialogClick cancelListener) {
		this.cancelListener = cancelListener;
	}
	public String getSureButtonText() {
		return sureButtonText;
	}
	public void setSureButtonText(String sureButtonText) {
		this.sureButtonText = sureButtonText;
	}
	public String getCancleButtonText() {
		return cancleButtonText;
	}
	public void setCancleButtonText(String cancleButtonText) {
		this.cancleButtonText = cancleButtonText;
	}
	private String title;
	private String message;
	private DialogClick sureListener;
	private DialogClick cancelListener;
	private String sureButtonText;
	private String cancleButtonText;
	
}
