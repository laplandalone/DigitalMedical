package com.health.digitalmedical.view.hospital;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import com.health.digitalmedical.adapter.HospitalBranchAdapter;
import com.health.digitalmedical.model.TeamT;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.view.faculty.DoctorDetailActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 医院导航
 * 
 */
public class HospitalDetailActivity extends BaseActivity implements OnItemClickListener
{

	private ListView list;
	
	@ViewInject(R.id.hospital_more_img)
	private ImageView hospitalImgMore;
	
	@ViewInject(R.id.hospital_more_text)
	private TextView hospitalTextMore;
	
	private int  initItemCount=2;
	private int itemHeight=132;
	List<TeamT> teamTs;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospital_hospital_detail);
		this.list=(ListView) findViewById(R.id.comlist);
		initValue();
		initView();
		ViewUtils.inject(this);
	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(HospitalDetailActivity.this,MainPageActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub

	}

	private void setListView()
	{   
		int heigths=0;
		String text = hospitalTextMore.getText().toString();
		if("更多".equals(text))
		{
			this.hospitalTextMore.setText("收起");
			heigths=getHeight(list,teamTs.size());
		}else
		{
			this.hospitalTextMore.setText("更多");
			heigths=getHeight(list,2);
		}
		ViewGroup.LayoutParams params = this.list.getLayoutParams(); 
	    params.height =heigths; 
	    this.list.setLayoutParams(params); 
	}
	

    public static int getHeight(ListView listView,int num)
    {
        ListAdapter listAdapter=listView.getAdapter();
        if(listAdapter==null){return 0;}
        int maxHeight=0;
        int itemNum=listAdapter.getCount();
        for(int i=0;i<itemNum;i++)
        {
        	if(i==num)break;
            View listItem=listAdapter.getView(i,null,listView);
            listItem.measure(0,0);
            int thisHeight=listItem.getMeasuredHeight();//计算子项View的宽高
            maxHeight+=thisHeight;
        }
        return maxHeight;
    }
	
	private void intentBaiduMap()
	{
		try
		{
			String uri = "intent://map/marker?location=30.584072,114.266477,&title=123&content=4567&src=好吃佬#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
			@SuppressWarnings("deprecation")
			Intent intent = Intent.getIntent(uri);
			startActivity(intent);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected void initValue()
	{
		dialog.setMessage("正在加载,请稍后...");
		dialog.show();
		// TODO Auto-generated method stub
		RequestParams param = webInterface.getTeamByHospitalId("101");
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
			HealthUtil.infoAlert(HospitalDetailActivity.this, "信息加载失败，请检查网络后重试");
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
		this.teamTs =	 gson.fromJson(jsonArray, new TypeToken<List<TeamT>>(){}.getType());  
		HospitalBranchAdapter adapter = new HospitalBranchAdapter(HospitalDetailActivity.this, teamTs);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
		setListView();
	}
	
	@OnClick(R.id.hospital_more_detail)
	public void hospitalMore(View v)
	{
		setListView();
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		try
		{
			TeamT teamT = teamTs.get(position);
			String uri = "intent://map/marker?location="+teamT.getY()+","+teamT.getX()+",&title="+teamT.getTeamName()+"&content=&src=好吃佬#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
			@SuppressWarnings("deprecation")
			Intent intent = Intent.getIntent(uri);
			startActivity(intent);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
