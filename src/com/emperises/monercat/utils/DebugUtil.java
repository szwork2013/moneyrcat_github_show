package com.emperises.monercat.utils;

public class DebugUtil {

	public static void throwDebugException(String msg){
		if(Logger.DEBUG){
			throw new RuntimeException(msg);
		}
	}
}
