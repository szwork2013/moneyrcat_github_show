package com.emperises.monercat.customview;

import android.content.Context;
import android.content.Intent;

import com.emperises.monercat.interfacesandevents.CustomDialogClickEvent;
import com.emperises.monercat.interfacesandevents.CustomDialogClickEvent.CustomDialogClickEventInterface;

public class DialogManager {
	private static CustomDialogConfig mCustomDialogConfig;
	private static DialogManager mDialogManager;
	private static Context mContext;
	private static CustomDialogClickEvent mCustomDialogClickEvent; 
	private DialogManager() {
	}
	public static DialogManager getInstance(Context context ,CustomDialogConfig config){
		return init(context,config);
	}
	private static DialogManager init(Context context ,CustomDialogConfig config){
		mCustomDialogConfig = config;
		mContext = context;
		mCustomDialogClickEvent = CustomDialogClickEvent.getInstance();
		if(mDialogManager == null){
			mDialogManager = new DialogManager();
		}
		return mDialogManager;
	}
	public static DialogManager getInstance(){
		if(mDialogManager == null){
			mDialogManager = new DialogManager();
		}
		return mDialogManager;
	}
	public CustomDialogConfig getDialogConfig(){
		return mCustomDialogConfig;
	}
	public void setDialogConfig(CustomDialogConfig config){
		mCustomDialogConfig = config;
	}
	public void setDialogClickInterfaceListener(CustomDialogClickEventInterface listener){
		if(mCustomDialogClickEvent!= null){			
			mCustomDialogClickEvent.setOnDialogClickListener(listener);
		}
	}
	public void show(){
		mContext.startActivity(new Intent(mContext , CustomDialog.class));
	}
	public void fireOnSureClick(){
		mCustomDialogClickEvent.fireOnSureClick();
	}
	public void fireFinish(){
		mCustomDialogClickEvent.fireFinishClick();
	}
	public void fireOnCancleClick(){
		mCustomDialogClickEvent.fireOnCancleClick();
	}
}
