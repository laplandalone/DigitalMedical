package com.health.digitalmedical.view.expert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.model.Team;
import com.health.digitalmedical.view.order.RegisteredMain;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ø∆ “ΩÈ…‹
 *
 */
public class OnLineFacultyDescActivity extends BaseActivity
{

	@ViewInject(R.id.all_doctor)
	private Button expertListBtn;
	
	@ViewInject(R.id.all_doctor)
	private Button askLineBtn;

	@ViewInject(R.id.content)
	private TextView content;
	
	private Team team;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faculty_desc);
		ViewUtils.inject(this);
		this.team=(Team) getIntent().getSerializableExtra("team");
		this.content.setText(team.getIntroduce());
		initView();
		initValue();
		
	}
	
	@OnClick(R.id.all_doctor)
	public void getExpertList(View v)
	{
		Intent intent = new Intent(OnLineFacultyDescActivity.this,OnLineExpertListActivity.class);
		intent.putExtra("teamId",this.team.getTeamId());
		startActivity(intent);
	}
	
	@OnClick(R.id.online_doctor)
	public void askLineExpert(View v)
	{
		Intent intent = new Intent(OnLineFacultyDescActivity.this,AskExpertListActivity.class);
		intent.putExtra("teamId",this.team.getTeamId());
		startActivity(intent);
	}
	
	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(OnLineFacultyDescActivity.this,MainPageActivity.class);
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

	}

}
