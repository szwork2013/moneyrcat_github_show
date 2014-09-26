package com.emperises.monercat.interfacesandevents;

import java.util.ArrayList;
import java.util.List;

import com.emperises.monercat.utils.Logger;

public class BalanceEvent {

	private static BalanceEvent mBalanceEvent;
	private List<BalanceInterface> mListeners =  new ArrayList<BalanceInterface>();
	private BalanceEvent() {
	}
	public static BalanceEvent getInstance(){
		if(mBalanceEvent == null){
			mBalanceEvent = new BalanceEvent();
		}
		return mBalanceEvent;
	}
	public void removeListener(BalanceInterface listener){
		if(listener != null){
			mListeners.remove(listener);
			
		}
	}
	public void removeAllListener(){
		mListeners.clear();
	}
	public void addBalanceListener(BalanceInterface listener){
		mListeners.add(listener);
	}
	public void fireBalanceChange(String currentBalance){
		Logger.i("BALANCE", "余额增加接口数量:"+mListeners.size());
		for(BalanceInterface listener : mListeners){
			if(listener != null){
				listener.onBalanceChange(currentBalance);
			}
		}
	}
}
