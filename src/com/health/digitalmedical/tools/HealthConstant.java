package com.health.digitalmedical.tools;

import android.os.Environment;

public class HealthConstant
{
	//133.0.179.151   61.183.0.35:7110  61.183.0.37:7170  
//	public static final String URL ="http://192.168.137.1:7001/mobile.htm?method=axis";
	public static final String URL ="http://58.53.209.120:9100/mobile.htm?method=axis";
	
	//public static final String URL ="http://61.183.0.35:7100/realNameRegister/mobile.htm?method=axis";
//	public static final String UPLOAD_URL = "http://192.168.137.1:7001/mobile.htm";
//	public static final String UPLOAD_URL = "http://192.168.137.1:7001/fileUpload";
	public static final String UPLOAD_URL = "http://58.53.209.120:9100/fileUpload";
//	public static final String LAUGUAGE_PACKAGE = "http://61.183.0.37:7170/apkpackages/chi_sim.traineddata";
	public static final String IMG_PATH = Environment.getExternalStorageDirectory().getPath()+"/hbgzocr/";
	
	public static final String Download_path = Environment.getExternalStorageDirectory().getPath() + "/health/download/";
}
