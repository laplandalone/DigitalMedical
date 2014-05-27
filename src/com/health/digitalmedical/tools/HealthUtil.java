package com.health.digitalmedical.tools;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.health.digitalmedical.application.RegApplication;
import com.health.digitalmedical.model.User;
import com.lidroid.xutils.http.RequestParams;

/**
 * 
 * 工具类
 *
 */
public class HealthUtil {
	
	 public static final boolean DEBUG = true;
	  private static final int LOG_SIZE_LIMIT = 3500;
	  public static final String LOG_TAG = "itzc_realname";
	  private static SharedPreferences userPreferences;

	  private static Context mContext = RegApplication.getInstance();
	  
		static 
		{
			if (userPreferences == null)
			{
			  userPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
			}
		 }
		
		public static void writeUserId(String info)
		{
			userPreferences.edit().putString("userId", info).commit();
		}
		
		public static String readUserId() 
		{
			return userPreferences.getString("userId", "");
		}
		
		public static void writeUserInfo(String info)
		{
			userPreferences.edit().putString("userInfo", info).commit();
		}
		
		public static String readUserInfo() 
		{
			return userPreferences.getString("userInfo", "");
		}
		
		public static User getUserInfo() 
		{
			String userJson = readUserInfo();
			User userInfo = json2Object(userJson, User.class);
			if (userInfo == null)
			{
				return null;
			}
			return userInfo;
		}
	
		public static String getVersionName() {
			  PackageManager pm = mContext.getPackageManager();
				try {
					PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
					return pi.versionName;  
					
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				} 
				return "";
		  }
	  public static void LOG_D(Class<?> paramClass, String paramString)
	  {
		  if (DEBUG) {
			  String str = paramClass.getName();
			    if (str != null)
			    {
			      str = str.substring(1 + str.lastIndexOf("."));
			    }
			    int i = paramString.length();
			    if (i > LOG_SIZE_LIMIT)
			    {
			      int j = 0;
			      int k = 1 + i / LOG_SIZE_LIMIT;
			      while (j < k + -1)
			      {
			        Log.d(LOG_TAG, paramString.substring(j * LOG_SIZE_LIMIT, LOG_SIZE_LIMIT * (j + 1)));
			        j++;
			      }
			      Log.d(LOG_TAG, paramString.substring(j * LOG_SIZE_LIMIT, i));
			    }
			    else {
			      Log.d(LOG_TAG, str + " -> " + paramString);
			    }
		  }
	    
	  }
	  
	  
	  public static void LOG_E(Class<?> paramClass, String paramString)
	  {
			  String str = paramClass.getName();
			  Log.e(LOG_TAG, str + " -> " + paramString);
	  }
	  
	  /**
	   * 调用web服务器的入参，转换成json
	   * @param methodName web服务的接口名称
	   * @param keys 参数的名称
	   * @param values 参数值
	   * @return
	   */
	  	public static String convert2Json (String methodName, String[] keys, Object[] values) {
  		    StringBuilder builder = new StringBuilder();
  			builder.append("{");
  			builder.append("channel:'Q',");
  			builder.append("channelType:'ANDROID',");
  			builder.append("securityCode:'0000000000',");
  			builder.append("serviceType:"+ "'" + methodName + "',");
  			builder.append("params:{");
  			if (keys != null && values != null) {
  		    int keyLen = keys.length ;
  		    int valueLen = values.length;
  		    if (keyLen == valueLen) {
	  			for (int i=0; i < keyLen; i++) 
	  			{
	  				if (i == keyLen-1) {
	  					if (values[i] instanceof String) {
		  					builder.append("'"+keys[i]+"':'" + values[i] + "'");
		  				} else {
		  					builder.append("'"+keys[i]+"':" + values[i] + "");
		  				}
	  					break;
	  				}
	  				if (values[i] instanceof String) {
	  					builder.append("'"+keys[i]+"':'" + values[i] + "',");
	  				} else {
	  					builder.append("'"+keys[i]+"':" + values[i] + ",");
	  				}
	  			}
  		    }
  		    else {
  		    	HealthUtil.LOG_D(HealthUtil.class, "convert2Json--http--->keys.lenght != values.lenght");
  		    }
  			}
  		    builder.append("},");
  			builder.append("rtnDataFormatType:'json'");
  			builder.append("}");
  			Log.d("convert2Json", builder.toString());
		return builder.toString();
	}
	  	
	 public static RequestParams getRequestParams(String methodName, String[] keys, Object[] values) {
		 String paramValue =  convert2Json(methodName, keys, values);
		 RequestParams requestParams = new RequestParams("UTF-8");
		 BasicNameValuePair nameValuePair = new BasicNameValuePair("param", paramValue);
		 requestParams.addBodyParameter(nameValuePair);
		 HealthUtil.LOG_D(HealthUtil.class, "url param=" + paramValue);
		 return requestParams;
	 }
	  	
	  	/*JSON解析*/
	  public static <T> T json2Object(String json,Class<T> clazz) {
		  if (json == null) {
			  return null;
		  }
		 String[] tempStr = json.split("\n");
		 if (tempStr != null && tempStr.length > 0) {
			 json = tempStr[tempStr.length - 1];
		 }
		  GsonBuilder builder = new GsonBuilder();   
        // 不转换没有 @Expose 注解的字段   
        builder.excludeFieldsWithoutExposeAnnotation();  
        Gson gson = builder.create();
        T object = null;
		try {
			object = gson.fromJson(json, clazz);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
        return object;

	  }
	  
	  //根据jsonobject的key获取value ，返回类型为String
	  public static String getJSONObjectKeyVal(JSONObject object, String key)
	  {
		if(object == null)
		{
		    return "";
		}
		if(key == null)
		{
		    return "";
		}
		String result = null;
		Object obj;
		try {
			obj = object.get(key);
			if (obj == null)
			{
				result = "";
			}
			else
			{
				result = obj.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	  }
	  
	  /**  
      * 验证手机号码  
      * @param mobiles 手机号
      * @return  [0-9]{5,9}  
      */  
     public static boolean isMobileNum(String phoneNum){
    	 boolean flag = false;
    	 try{
    		 Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
    		 Matcher m = p.matcher(phoneNum);
    		 flag = m.matches();
    	 }catch(Exception e){
    		 flag = false;
    	 }
    	 return flag;
     }
    /**
     * 自定义Toast
     * @param activity
     * @param msg
     */
	public static void infoAlert(Activity activity,String msg) {
		Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	/**
	 * 获取sd卡的路径
	 * 
	 * @return 路径的字符串
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取外存目录
		} else {
			sdDir = Environment.getDownloadCacheDirectory();
		}
		if (sdDir == null) {
			return "";
		}
		return sdDir.toString();
	}
	
	public static String getDownloadPath() {
		String sdPath = getSDPath();
		if (!sdPath.endsWith(File.separator)) {
			sdPath += File.separator;
		}
		
		File tessdata = new File(sdPath + "tessdata");
		if (!tessdata.exists() || !tessdata.isDirectory()) {
			boolean b = tessdata.mkdirs();
			LOG_D(HealthUtil.class, "----b=" + b);
		}
		
		return tessdata.getAbsolutePath().endsWith(File.separator)?tessdata.getAbsolutePath():tessdata.getAbsolutePath() + File.separator;
	}
	
	

}
