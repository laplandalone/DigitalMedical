package com.health.digitalmedical.view.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.model.HospitalNewsT;
import com.health.digitalmedical.view.user.LoginActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class NewsDetailActivity extends BaseActivity
{
    @ViewInject(R.id.newsImage)
	private ImageView imageView;
	
    @ViewInject(R.id.newsTitle)
	private TextView newsTitle;
	
    @ViewInject(R.id.newsContent)
	private TextView newsContent;
	
    @ViewInject(R.id.newsDate)
	private TextView createDate;
	
    private HospitalNewsT hospitalNewsT;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(NewsDetailActivity.this, MainPageActivity.class);
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
		// TODO Auto-generated method stub
		this.hospitalNewsT=(HospitalNewsT) getIntent().getSerializableExtra("hospitalNewsT");
		newsTitle.setText(hospitalNewsT.getNewsTitle());
		newsContent.setText(hospitalNewsT.getNewsContent());
		createDate.setText(hospitalNewsT.getCreateDate());
	}

}
