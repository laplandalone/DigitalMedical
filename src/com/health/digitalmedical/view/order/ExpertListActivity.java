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
import com.health.digitalmedical.XListView;
import com.health.digitalmedical.XListView.IXListViewListener;
import com.health.digitalmedical.adapter.OrderExpertAdapter;
import com.health.digitalmedical.model.OrderExpert;
import com.health.digitalmedical.model.OrderExpertList;
import com.health.digitalmedical.model.Team;
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


public class ExpertListActivity extends BaseActivity  implements IXListViewListener,OnItemClickListener
{

	@ViewInject(R.id.title)
	private TextView title;

	@ViewInject(R.id.textTitle)
	private TextView textTitle;
	
	
	private XListView list;

	private Team team;
	private OrderExpertList expertList;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_faculty_list);
		this.list=(XListView) findViewById(R.id.asklist);
		this.team=(Team) getIntent().getSerializableExtra("team");
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(ExpertListActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stubtotal_count
		textTitle.setVisibility(View.GONE);
		title.setText("医生列表");
	}

	@Override
	protected void initValue()
	{
		dialog.setMessage("正在加载,请稍后...");
		dialog.show();
		// TODO Auto-generated method stub
		RequestParams param = webInterface.queryOrderDoctorList(team.getTeamId());
		invokeWebServer(param, GET_LIST);
	}
	
	
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
			HealthUtil.infoAlert(ExpertListActivity.this, "信息加载失败，请检查网络后重试");
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

	
	private void returnMsg(String json, int code)
	{
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonObject returnObj = jsonObject.getAsJsonObject("returnMsg");
		this.expertList = HealthUtil.json2Object(returnObj.toString(), OrderExpertList.class);
		OrderExpertAdapter adapter = new OrderExpertAdapter(ExpertListActivity.this, expertList);
		this.list.setAdapter(adapter);
		this.list.setXListViewListener(this);
		this.list.setPullLoadEnable(false);
		this.list.setPullRefreshEnable(false);
		this.list.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent(ExpertListActivity.this,ExpertDetailActivity.class);
		OrderExpert expert =expertList.getOrders().get(position-1);
		Bundle bundle = new Bundle();
		bundle.putSerializable("expert", expert);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	

}
