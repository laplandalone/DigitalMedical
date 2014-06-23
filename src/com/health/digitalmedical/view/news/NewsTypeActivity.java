package com.health.digitalmedical.view.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.adapter.NewsTypeAdapter;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.view.expert.QuestionActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsTypeActivity extends BaseActivity implements OnItemClickListener
{
	
	@ViewInject(R.id.title)
	private TextView title;

	private ListView list;
	private String type;
	
	private JsonArray jsonArray;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_list);
		list = (ListView) findViewById(R.id.comlist);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
		
	}

	@Override
	protected void initView()
	{
		this.type=getIntent().getStringExtra("type");
		if("BAIKE".equals(type))
		{
			title.setText("�����ٿƷ���");
		}else
		{
			title.setText("�������ķ���");
		}
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		// getListRst();
		dialog.setMessage("���ڼ���,���Ժ�...");
		dialog.show();
		String hospitalId=HealthUtil.readHospitalId();
		RequestParams param = webInterface.getNewsType(hospitalId, this.type);
		invokeWebServer(param, GET_LIST);
	}

	/**
	 * ����web����
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
	 * ��ȡ��̨���ص�����
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
			HealthUtil.infoAlert(NewsTypeActivity.this, "��Ϣ����ʧ�ܣ��������������");
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
	 * �����ؽ������
	 * executeType
	 */
	private void returnMsg(String json, int code)
	{
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		String executeType = jsonObject.get("executeType").getAsString();
		if (!"success".equals(executeType))
		{
			HealthUtil.infoAlert(NewsTypeActivity.this, "����ʧ��������.");
			return;
		}
		this.jsonArray = jsonObject.getAsJsonArray("returnMsg");
		NewsTypeAdapter adapter = new NewsTypeAdapter(NewsTypeActivity.this, jsonArray);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent(NewsTypeActivity.this, NewsActivity.class);
		JsonElement element= jsonArray.get(position);
		JsonObject jsonObject = element.getAsJsonObject();
		String typeId=jsonObject.get("configId").getAsString();
		intent.putExtra("typeId", typeId);
		intent.putExtra("type", type);
		startActivity(intent);
	}

}
