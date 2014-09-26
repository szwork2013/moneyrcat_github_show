package com.emperises.monercat.interfacesandevents;

public interface HttpInterface {
	void onFinished(String content);
	void onFail(Throwable t, int errorNo, String strMsg);
	void onHttpStart();
	void onLoading(long count, long current);
}
