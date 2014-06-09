package com.health.digitalmedical.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.adapter.ExpertDetailAdapter;
import com.health.digitalmedical.model.OrderExpert;
import com.health.digitalmedical.model.OrderExpertList;
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
 * 专家详情
 *
 */
public class ExpertDetailActivity extends BaseActivity implements OnItemClickListener
{
	@ViewInject(R.id.doctorName)
	private TextView doctorName;
	@ViewInject(R.id.facultyName)
	private TextView facultyName;
	@ViewInject(R.id.position)
	private TextView position;
	@ViewInject(R.id.departNameDec)
	private TextView departNameDec;

	private ListView list;
	
	private OrderExpert expert; 
	private OrderExpertList expertList;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expert_doctor_detail);
		this.list=(ListView) findViewById(R.id.list_view);
		this.expert=(OrderExpert) getIntent().getSerializableExtra("expert");
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(ExpertDetailActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void initValue()
	{
		
		dialog.setMessage("正在加载,请稍后...");
		dialog.show();
		
		this.doctorName.setText(expert.getDoctorName());
		this.facultyName.setText(expert.getTeamName());
		this.position.setText(expert.getPost());
		this.departNameDec.setText(expert.getIntroduce());
		
		// TODO Auto-generated method stub
		RequestParams param = webInterface.queryOrderByDoctorIdList(expert.getDoctorId());
		//RequestParams param = webInterface.queryOrderDoctorList("10001");
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
			HealthUtil.infoAlert(ExpertDetailActivity.this, "信息加载失败，请检查网络后重试");
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
		JsonObject returnObj = jsonObject.getAsJsonObject("returnMsg");
		this.expertList = HealthUtil.json2Object(returnObj.toString(), OrderExpertList.class);
		ExpertDetailAdapter adapter = new ExpertDetailAdapter(ExpertDetailActivity.this, expertList);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent(ExpertDetailActivity.this,ExpertRegisterActivity.class);
		
		OrderExpert orderExpert=expertList.getOrders().get(position);
		String doctorName=expert.getDoctorName();
		String registerTime=orderExpert.getWorkTime();
		String fee=orderExpert.getFee();
		String registerId=orderExpert.getRegisterId();
		String userOrderNum=orderExpert.getUserOrderNum();
		String doctorId=orderExpert.getDoctorId();
		String teamId=orderExpert.getTeamId();
		String teamName=orderExpert.getTeamName();
		String day=orderExpert.getDay();
		intent.putExtra("doctorName", doctorName);
		intent.putExtra("registerTime", day+" "+registerTime);
		intent.putExtra("fee", fee);
		intent.putExtra("registerId", registerId);
		intent.putExtra("userOrderNum", userOrderNum);
		
		intent.putExtra("doctorId", doctorId);
		intent.putExtra("teamId", teamId);
		intent.putExtra("teamName", teamName);
		startActivity(intent);
	}

}
