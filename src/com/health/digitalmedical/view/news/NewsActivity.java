package com.health.digitalmedical.view.news;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.health.digitalmedical.adapter.NewsListAdapter;
import com.health.digitalmedical.model.HospitalNewsT;
import com.health.digitalmedical.model.UserQuestionT;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.view.expert.TalkActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ҽԺ��Ѷ
 * 
 */
public class NewsActivity extends BaseActivity implements OnItemClickListener
{

	private List<HospitalNewsT> hospitalNewsTs;
	private String hospitalId;
	private ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_article_list);
		this.list=(ListView) findViewById(R.id.newlist);
		ViewUtils.inject(this);
		initValue();
	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(NewsActivity.this, MainPageActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		dialog.setMessage("���ڼ���,���Ժ�...");
		dialog.show();

		RequestParams param = webInterface.getNewsByHospitalId("101");
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

			HealthUtil.infoAlert(NewsActivity.this, "��Ϣ����ʧ�ܣ��������������");
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
			}
		}

	}

	/*
	 * �����ؽ������
	 */
	private void returnMsg(String json, int code)
	{
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonArray jsonArray = jsonObject.getAsJsonArray("returnMsg");
		Gson gson = new Gson();
		this.hospitalNewsTs = gson.fromJson(jsonArray, new TypeToken<List<HospitalNewsT>>()
		{
		}.getType());
		NewsListAdapter adapter = new NewsListAdapter(NewsActivity.this, hospitalNewsTs);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
		HospitalNewsT hospitalNewsT = this.hospitalNewsTs.get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("hospitalNewsT", hospitalNewsT);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
