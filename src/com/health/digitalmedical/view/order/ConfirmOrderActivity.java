package com.health.digitalmedical.view.order;

import java.net.URLEncoder;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.android.app.sdk.AliPay;
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
	
	@ViewInject(R.id.taobao)
	private Button taobaoBtn;
	
	private String orderId="";
	
	private String payState="100";/*初始未支付*/
	
	private String orderHospitalId="";
	
	private static final int RQF_PAY = 1;   //支付宝支付
	private static final int GET_ORDER_INFO = 2;   //加密
	private static final int RECHARGE = 3;   //充值
	private Map<String,String> map;  //解析支付宝返回结果后的map
	
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
		RequestParams param = webInterface.getRsaSign(this.orderId);
		invokeWebServer(param, RSA_SIGN);
	}
	
   
	/**
	 * 调用手机支付宝进行支付，支付成功后会返回结果信息
	 * @param orderInfo
	 */
	public void alipay(final String orderInfo) {
		new Thread() {
			public void run() {
				AliPay alipay = new AliPay(ConfirmOrderActivity.this, handler);
				//设置为沙箱模式，不设置默认为线上环境
//				alipay.setSandBox(true);
				String result = alipay.pay(orderInfo);
				Message msg = new Message();
				msg.what = RQF_PAY;
				msg.obj = result;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			map = HealthUtil.parserAliResult(msg.obj.toString());
			String message = map.get("memo");
			if(map.containsKey("resultStatus"))
			{
				if("9000".equals(map.get("resultStatus")))/*支付成功*/
				{
					RequestParams param = webInterface.orderPay(orderId, "101");
					invokeWebServer(param, PAY_STATE);
				}else
				{
//					 System.out.println("2");
				}
			}
			super.handleMessage(msg);
		}
	};

	
	@Override
	protected void initView()
	{
		title.setText("预约详情");
		// TODO Auto-generated method stub
		this.payState= getIntent().getStringExtra("payState");
		this.orderHospitalId=getIntent().getStringExtra("hospitalId");
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
		/**
		 * 什么时候可以支付
		 * 1.预约成功后，hospitalId为订单医院ID，这时根据医院ID判断是否支持支付宝支付；
		 * 2.通过我的预约，查看预约记录，根据订单归属orderHospitalId判断是否支持支付宝支付；
		 * 3.
		 * */
		String hospitalId=HealthUtil.readHospitalId();
		
		if(("102".equals(orderHospitalId) || "102".equals(hospitalId)) && "100".equals(payState) )/*亚心医院未支付状态支持支付宝支付*/
		{
			taobaoBtn.setVisibility(View.VISIBLE);
			taobaoBtn.setText("支付宝");
		}
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
			case RSA_SIGN:
				returnMsg(arg0.result, RSA_SIGN);
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
		
		switch (code)
		{
		    case RSA_SIGN:
		    	JsonObject rtn = jsonObject.getAsJsonObject("returnMsg");	
		    	String sign=rtn.get("sign").getAsString();
		    	System.out.println(sign);
		    	alipay(sign);
		    case PAY_STATE:	
		      String payRst=jsonObject.get("returnMsg").getAsString();
		      if("true".equals(payRst))
		      {
		    	  HealthUtil.infoAlert(ConfirmOrderActivity.this, "支付成功");
		    	  taobaoBtn.setVisibility(View.GONE);
		      }
		      default:
		}
		
	}
}
