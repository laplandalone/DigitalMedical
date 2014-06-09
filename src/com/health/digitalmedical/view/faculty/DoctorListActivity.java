package com.health.digitalmedical.view.faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.adapter.DoctorListAdapter;
import com.health.digitalmedical.view.order.RegisteredMain;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DoctorListActivity extends BaseActivity implements OnItemClickListener
{

	@ViewInject(R.id.title)
	private TextView title;

	private ListView list;

	private List<Map<String, Object>> unhandList = new ArrayList<Map<String, Object>>();

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_list);
		this.list=(ListView) findViewById(R.id.comlist);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(DoctorListActivity.this,MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText(R.string.btn_hospital_doctor);
		getListRst();
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub

	}

	public void getListRst()
	{

		HashMap localHashMap = new HashMap();
		localHashMap.put("text", "ÄÄÌì");

		unhandList.add(localHashMap);
		DoctorListAdapter adapter = new DoctorListAdapter(DoctorListActivity.this, unhandList);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent(DoctorListActivity.this,DoctorDetailActivity.class);
		startActivity(intent);
	}
}
