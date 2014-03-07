package com.prokarma.loggers;

import android.util.Log;

public class FifaLog implements Loggable{
    public static void i(Severity severe,String message){
    	if(severe == Severity.HIGH){
    		Log.i(TAG,message);
    		return;
    	}
    	if(severe == Severity.LOW){
    		if(ISDEBUGGABLE){
    			Log.i(TAG,message);
    		}
    	}
    }
}
