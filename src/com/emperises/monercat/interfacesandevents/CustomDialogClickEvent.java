package com.emperises.monercat.interfacesandevents;

public class CustomDialogClickEvent {

	private static CustomDialogClickEvent mCustomDialogClickEvent;
	private CustomDialogClickEventInterface mCustomDialogClickEventInterface;
	private CustomDialogClickEvent() {
	}
	public static CustomDialogClickEvent getInstance(){
		
		if(mCustomDialogClickEvent == null){
			mCustomDialogClickEvent  =  new CustomDialogClickEvent();
		}
		return mCustomDialogClickEvent;
	}
	public void fireOnSureClick(){
		if(mCustomDialogClickEventInterface != null){
			mCustomDialogClickEventInterface.onSureClick();
		}
	}
	public void fireOnCancleClick(){
		if(mCustomDialogClickEventInterface != null){
			mCustomDialogClickEventInterface.onCancleClick();
		}
	}
	public void fireFinishClick(){
		if(mCustomDialogClickEventInterface != null){
			mCustomDialogClickEventInterface.finishClick();
		}
	}
	public void setOnDialogClickListener(CustomDialogClickEventInterface mCustomDialogClickEventInterface){
		this.mCustomDialogClickEventInterface = mCustomDialogClickEventInterface;
	}
	public interface CustomDialogClickEventInterface{
		void onSureClick();//确定事件
		void onCancleClick();//取消事件
		void finishClick();//回退事件
	}
}
