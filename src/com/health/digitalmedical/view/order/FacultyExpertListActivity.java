package com.health.digitalmedical.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.adapter.FacultyListAdapter;
import com.health.digitalmedical.model.Team;
import com.health.digitalmedical.model.TeamList;
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
 * 专家科室列表
 * 
 */
public class FacultyExpertListActivity extends BaseActivity implements OnItemClickListener
{

	@ViewInject(R.id.title)
	private TextView title;
	
	private ListView list;

	private TeamList teamList;

	private String hospitalId;
	
	private String orderType="expert";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_list);
		this.list = (ListView) findViewById(R.id.comlist);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();

	}
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(FacultyExpertListActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText("科室列表");
		
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		// getListRst();
		String orderTypeT=getIntent().getStringExtra("orderType");
		this.hospitalId=HealthUtil.readHospitalId();
		if(orderTypeT!=null && !"".equals(orderTypeT))
		{
			this.orderType=orderTypeT;
		}
		dialog.setMessage("正在加载,请稍后...");
		dialog.show();
		RequestParams param = webInterface.queryTeamList(this.hospitalId,null);
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
			HealthUtil.infoAlert(FacultyExpertListActivity.this, "信息加载失败，请检查网络后重试");
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
		this.teamList = HealthUtil.json2Object(returnObj.toString(), TeamList.class);
		FacultyListAdapter adapter = new FacultyListAdapter(FacultyExpertListActivity.this, teamList);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
		if("expert".equals(orderType))
		{
			Intent intent = new Intent(FacultyExpertListActivity.this, ExpertListActivity.class);
			Team team = teamList.getTeams().get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("team", team);
			intent.putExtras(bundle);
			startActivity(intent);
		}else if("normal".equals(orderType))
		{
			Intent intent = new Intent(FacultyExpertListActivity.this, CommonOrderRegisterActivity.class);
			Team team = teamList.getTeams().get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("team", team);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

}
