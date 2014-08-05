package com.health.digitalmedical.view.expert;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.adapter.TalkAdapter;
import com.health.digitalmedical.model.Doctor;
import com.health.digitalmedical.model.User;
import com.health.digitalmedical.model.UserQuestionT;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.view.order.RegisteredMain;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class TalkActivity extends BaseActivity
{

	@ViewInject(R.id.title)
	private TextView title;
	private ListView list;
	private String userId;
	private String questionType;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.talk_list);
		this.list=(ListView) findViewById(R.id.talklist);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(TalkActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		this.title.setText(R.string.ask_online_temp45);
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		dialog.setMessage("���ڼ���,���Ժ�...");
		dialog.show();
		this.questionType=getIntent().getStringExtra("questionType");
		UserQuestionT questionT = (UserQuestionT) getIntent().getSerializableExtra("questioin");
		this.userId=questionT.getUserId();
		
		String questionId=questionT.getQuestionId();
		RequestParams param = webInterface.getUserQuestionsByIds(questionId);
		invokeWebServer(param, ADD_QUESTION);
		
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
			
			HealthUtil.infoAlert(TalkActivity.this, "��Ϣ����ʧ�ܣ��������������");
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
			case ADD_QUESTION:
				returnMsg(arg0.result, ADD_QUESTION);
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
		List<UserQuestionT> questionTs = gson.fromJson(jsonArray, new TypeToken<List<UserQuestionT>>(){}.getType());  
		TalkAdapter adapter = new TalkAdapter(TalkActivity.this, questionTs);
		list.setAdapter(adapter);
	}


}
