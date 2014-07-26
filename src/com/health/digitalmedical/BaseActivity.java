package com.health.digitalmedical;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.health.digitalmedical.application.RegApplication;
import com.health.digitalmedical.webservice.IWebServiceInterface;
import com.health.digitalmedical.webservice.WebServiceInterfaceImpl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpHandler;

public abstract class BaseActivity extends FragmentActivity
{

	public HttpHandler httpHandler;
	
	public HttpUtils mHttpUtils = new HttpUtils();
	public static final int GET_LIST = 1001;
	public static final int GET_DATE_INFO = 1002;
	public static final int GET_LIST_MORE = 1003;
	public static final int ADD_REGISTER_ORDER = 1004;
	public static final int USER_LOGIN = 1005;
	public static final int ADD_QUESTION = 1006;
	public static final int ADD_USER= 1007;
	public static final int UPDATE_USER= 1008;
	public static final int GET_ORDER_NUM= 1009;
	public static final int AUTH_CODE= 10010;
	public static final int CHECK_AUTH_CODE= 10011;
	public static final int SET_PSW= 10012;
	protected ProgressDialog dialog;

	protected IWebServiceInterface webInterface = new WebServiceInterfaceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog = new ProgressDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);

	}
	
	public void addActivity(Activity activity)
	{
		RegApplication.getInstance().addActivity(activity);
	}

	public void exit()
	{
		RegApplication.getInstance().exit();
	}
	/**
	 * 初始化对象
	 */
	protected abstract void initView();

	/**
	 * 初始化赋值
	 */
	protected abstract void initValue();
	
	public void finish(View v)
	{
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
