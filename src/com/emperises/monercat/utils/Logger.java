package com.emperises.monercat.utils;

import android.util.Log;
public class Logger
{
	public static boolean DEBUG = true;
	public static void i(String tag,String msg) {
		if (DEBUG) {
			Log.i(tag, msg);
		}
	}
	public static void e(String tag,String msg,Throwable tr) {
		if (DEBUG) {
			Log.e(tag, msg, tr);
		}
	}
}

