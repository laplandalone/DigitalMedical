package com.health.digitalmedical.view.expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
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
import com.health.digitalmedical.adapter.ExpertListAdapter;
import com.health.digitalmedical.adapter.FacultyListAdapter;
import com.health.digitalmedical.model.Doctor;
import com.health.digitalmedical.model.DoctorList;
import com.health.digitalmedical.model.TeamList;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.view.faculty.DoctorDetailActivity;
import com.health.digitalmedical.view.order.RegisteredMain;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ����ҽ����
 *
 */
public class OnLineExpertListActivity extends BaseActivity  implements OnItemClickListener
{

	@ViewInject(R.id.title)
	private TextView title;

	private ListView list;

	private String teamId;
	private DoctorList doctorList;
	private List<Map<String, Object>> unhandList = new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.online_faculty_list);
		this.list=(ListView) findViewById(R.id.asklist);
		this.teamId=getIntent().getStringExtra("teamId");
		ViewUtils.inject(this);
		initView();
		initValue();
	}
	
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stubtotal_count

	}

	@Override
	protected void initValue()
	{
		dialog.setMessage("���ڼ���,���Ժ�...");
		dialog.show();
		// TODO Auto-generated method stub
		RequestParams param = webInterface.queryDoctorList(null,null,teamId);
		invokeWebServer(param, GET_LIST);
	}
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(OnLineExpertListActivity.this,MainPageActivity.class);
		startActivity(intent);
		finish();
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
			HealthUtil.infoAlert(OnLineExpertListActivity.this, "��Ϣ����ʧ�ܣ��������������");
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
	 */
	private void returnMsg(String json, int code)
	{
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonObject returnObj = jsonObject.getAsJsonObject("returnMsg");
		this.doctorList = HealthUtil.json2Object(returnObj.toString(), DoctorList.class);
		ExpertListAdapter adapter = new ExpertListAdapter(OnLineExpertListActivity.this, doctorList);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent(OnLineExpertListActivity.this,DoctorDetailActivity.class);
		Doctor doctor = doctorList.getDoctors().get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("doctor", doctor);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
