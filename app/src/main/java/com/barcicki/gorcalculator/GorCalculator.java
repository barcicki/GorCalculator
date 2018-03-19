package com.barcicki.gorcalculator;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class GorCalculator extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
	}
	
}
