package com.health.digitalmedical.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
/**
 * 
 * 挂号预约
 *
 */
public class ConfirmOrderActivity extends BaseActivity
{

	@ViewInject(R.id.title)
	private TextView title;

	@ViewInject(R.id.faculty_name)  
	private TextView falcultyNameT;
	
	@ViewInject(R.id.doctor_name) 
	private TextView doctorNameT;
	
	@ViewInject(R.id.date)     
	private TextView dateT;
	
	@ViewInject(R.id.date_time)   
	private TextView dateTimeT;
	
	@ViewInject(R.id.register_num)
	private TextView registerNumT;
	
	@ViewInject(R.id.user_name)   
	private TextView userNameT;
	
	@ViewInject(R.id.sex)         
	private TextView sexT;
	
	@ViewInject(R.id.confirm_num) 
	private TextView comfirmNumT;
	
	@ViewInject(R.id.confirm_price)
	private TextView confirmPriceT;
	
	@ViewInject(R.id.sex)
	private TextView sexTV;
	
	@ViewInject(R.id.confirm_num)
	private TextView confirmNumTV;
	
	@ViewInject(R.id.userOrderNum)
	private LinearLayout linearLayout;
	
	@ViewInject(R.id.teamDoctorId)
	private LinearLayout linearLayout1;
	
	@ViewInject(R.id.feelayout)
	private LinearLayout linearLayout2;
	
	private String orderId="";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expert_register_proof);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(ConfirmOrderActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	
	@OnClick(R.id.taobao)
	public void pay(View v)
	{
		String payState="101";
		RequestParams param = webInterface.orderPay(this.orderId,payState);
		invokeWebServer(param, PAY_STATE);
	}
	
	@Override
	protected void initView()
	{
		title.setText("预约详情");
		// TODO Auto-generated method stub
		this.orderId=getIntent().getStringExtra("orderId");
		String registerNum=getIntent().getStringExtra("userOrderNum"  );
		String fee=getIntent().getStringExtra("fee"           );
		String doctorName=getIntent().getStringExtra("doctorName"    );
		if("0".equals(registerNum) || "".equals(registerNum))
		{
			linearLayout.setVisibility(View.GONE);
		}
		if("0".equals(fee) || "".equals(fee))
		{
			linearLayout1.setVisibility(View.GONE);
		}
		if("0".equals(doctorName) || "".equals(doctorName))
		{
			linearLayout2.setVisibility(View.GONE);
		}
		doctorNameT.setText(doctorName); 
		dateT.setText(getIntent().getStringExtra("registerTime"  )); 
		confirmPriceT.setText(fee); 
		registerNumT.setText(registerNum); 
		falcultyNameT.setText(	getIntent().getStringExtra("teamName"      )); 
		userNameT.setText(getIntent().getStringExtra("userName"      )); 
		dateTimeT.setText(getIntent().getStringExtra("userNo"        )); 
		sexTV.setText(getIntent().getStringExtra("sex" )); 
		confirmNumTV.setText(getIntent().getStringExtra("userTelephone" ));

	}

	@Override
	protected void initValue()
	{
		
	}
	
	/**
	 * 链接web服务
	 * 
	 * @param param
	 */
	private void invokeWebServer(RequestParams param, int responseCode)
	{
		HealthUtil.LOG_D(getClass(), "connect to web server");
		MineRequestCallBack requestCallBack = new MineRequestCallBack(responseCode);
		if (httpHandler != null)
		{
			httpHandler.stop();
		}
		httpHandler = mHttpUtils.send(HttpMethod.POST, HealthConstant.URL, param, requestCallBack);
	}

	/**
	 * 获取后台返回的数据 
	 */
	class MineRequestCallBack extends RequestCallBack<String>
	{

		private int responseCode;

		public MineRequestCallBack(int responseCode)
		{
			super();
			this.responseCode = responseCode;
		}

		@Override
		public void onFailure(HttpException error, String msg)
		{
			HealthUtil.LOG_D(getClass(), "onFailure-->msg=" + msg);
			if (dialog.isShowing())
			{
				dialog.cancel();
			}
			HealthUtil.infoAlert(ConfirmOrderActivity.this, "信息加载失败，请检查网络后重试");
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0)
		{
			// TODO Auto-generated method stub
			HealthUtil.LOG_D(getClass(), "result=" + arg0.result);
			if (dialog.isShowing())
			{
				dialog.cancel();
			}
			switch (responseCode)
			{
			case PAY_STATE:
				returnMsg(arg0.result, PAY_STATE);
				break;
			}
		}

	}

	/*
	 * 处理返回结果数据
	 */
	private void returnMsg(String json, int code)
	{
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonObject returnObj = jsonObject.getAsJsonObject("returnMsg");
	}
}
