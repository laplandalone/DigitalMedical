package com.health.digitalmedical.view.order;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.adapter.UserOrderListAdapter;
import com.health.digitalmedical.model.RegisterOrderT;
import com.health.digitalmedical.model.User;
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

public class UserOrderActivity extends BaseActivity implements OnItemClickListener
{
	@ViewInject(R.id.title)
	private TextView title;
	
	private User user;
	private ListView list;
	private List<RegisterOrderT> registerOrderTs;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register_order);
		this.list=(ListView) findViewById(R.id.list_view);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText("我的预约");
	}


	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(UserOrderActivity.this, MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initView();
		initValue();
	}
	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		dialog.setMessage("正在加载,请稍后...");
		dialog.show();
		this.user=HealthUtil.getUserInfo();
		String userId=user.getUserId();
		RequestParams param = webInterface.getUserOrderById(userId);
		invokeWebServer(param, GET_LIST);
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
			if (list != null)
			{
				// list.stopLoadMore();
			}
			HealthUtil.infoAlert(UserOrderActivity.this, "信息加载失败，请检查网络后重试");
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
			case GET_LIST:
				returnMsg(arg0.result, GET_LIST);
				break;
			case GET_LIST_MORE:
				returnMsg(arg0.result, GET_LIST_MORE);
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
		JsonArray jsonArray = jsonObject.getAsJsonArray("returnMsg");
		Gson gson = new Gson();
		this.registerOrderTs =gson.fromJson(jsonArray, new TypeToken<List<RegisterOrderT>>(){}.getType());
		UserOrderListAdapter adapter = new UserOrderListAdapter(UserOrderActivity.this, registerOrderTs);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent(UserOrderActivity.this,ConfirmOrderActivity.class);
		RegisterOrderT registerOrderT =registerOrderTs.get(position);
		intent.putExtra("orderType", "old");
		intent.putExtra("hospitalId", registerOrderT.getHospitalId());
		intent.putExtra("payState", registerOrderT.getPayState());
		intent.putExtra("orderId", registerOrderT.getOrderId());
		intent.putExtra("doctorName", registerOrderT.getDoctorName());
		intent.putExtra("registerTime", registerOrderT.getRegisterTime());
		intent.putExtra("fee", registerOrderT.getOrderFee());
		intent.putExtra("registerId", registerOrderT.getRegisterId());
		intent.putExtra("userOrderNum", registerOrderT.getOrderNum());
		
		intent.putExtra("doctorId", registerOrderT.getDoctorId());
		intent.putExtra("teamId", registerOrderT.getTeamId());
		intent.putExtra("teamName", registerOrderT.getTeamName());
		intent.putExtra("userName", registerOrderT.getUserName());
		intent.putExtra("userNo", registerOrderT.getUserNo());
		intent.putExtra("userTelephone", registerOrderT.getUserTelephone());
		intent.putExtra("sex", registerOrderT.getSex());
		startActivity(intent);
	}
}
