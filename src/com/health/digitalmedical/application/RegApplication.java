package com.health.digitalmedical.application;

import android.app.Application;

public class RegApplication extends Application{
	
	private static RegApplication applicationContext;

	public RegApplication() {
		super();
		applicationContext = this;
	}

	public static RegApplication getInstance() {
		return applicationContext;
	}
	
	
	
	

}
