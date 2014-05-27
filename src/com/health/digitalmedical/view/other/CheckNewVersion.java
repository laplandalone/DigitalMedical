package com.health.digitalmedical.view.other;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.webservice.IWebServiceInterface;
import com.health.digitalmedical.webservice.WebServiceInterfaceImpl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class CheckNewVersion extends Service{

	private HttpUtils mHttpUtils = new HttpUtils();
	private final static int CHECK_NEWVERSION = 1;
	protected IWebServiceInterface webInterface = new WebServiceInterfaceImpl();
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("hbgz", "version------------>onStartCommand");
		checkNewVersion();
		return super.onStartCommand(intent, flags, startId);
	}

	private void checkNewVersion() {
	try {
		JSONObject jsonObject = new JSONObject();
		String versionName = HealthUtil.getVersionName();
		jsonObject.put("applicationVersionCode", versionName);
		jsonObject.put("applicationType", "CUST");
		jsonObject.put("deviceType", "ANDROID");
		RequestParams param = webInterface.checkNewVersion(jsonObject.toString());
		connectWebServer(param,CHECK_NEWVERSION);
	} catch (JSONException e) {
		e.printStackTrace();
	}
	
	}
	
	/**
	 * ���ӷ�����
	 * 
	 * @param param
	 */
	private void connectWebServer(RequestParams param, int responseCode) {
		try {
//			RealNameUtil.LOG_D(getClass(), "connect to web server");
			MineRequestCallBack requestCallBack = new MineRequestCallBack(
					responseCode);
			mHttpUtils.send(HttpMethod.POST, HealthConstant.URL, param, requestCallBack);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ؽ������
	 */
	class MineRequestCallBack extends RequestCallBack<String> {

		private int responseCode;

		public MineRequestCallBack(int responseCode) {
			super();
			this.responseCode = responseCode;
		}


		@Override
		public void onFailure(HttpException error, String msg) {
			HealthUtil.LOG_D(getClass(), "version-------------------->msg=" + msg);
			stopSelf();
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			switch(responseCode) {
			case CHECK_NEWVERSION:
				newVersionResult(arg0.result);
				HealthUtil.LOG_D(getClass(), "version======================-->result=" + arg0.result);
				break;
			}
		}
		
	}
	
	private void newVersionResult(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONObject returnJson = jsonObject.getJSONObject("returnMsg");
			/** xjz 2014-05-21 ��returnJsonΪ�յ�ʱ��ᱨ�쳣 end*/
			if(returnJson.length() > 0)
			{
				String remark = returnJson.getString("remark");
				String applicationUrl = returnJson.getString("applicationUrl");
				String forceUpdateFlag = returnJson.getString("forceUpdateFlag");
				String applicationVersionCode = returnJson.getString("applicationVersionCode");
				String versionName = HealthUtil.getVersionName();
				if(!versionName.equals(applicationVersionCode))
				{
					Bundle bundle = new Bundle();
					bundle.putString("remark", remark);
					bundle.putString("applicationUrl", applicationUrl);
					bundle.putString("forceUpdateFlag", forceUpdateFlag);
					bundle.putString("applicationVersionCode", applicationVersionCode);
					Intent intent = new Intent(this, NewVersionActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtras(bundle);
					startActivity(intent);
				}
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		stopSelf();
	}
	
	
}
