package com.health.digitalmedical.view.expert;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.adapter.ImgViewPager;
import com.health.digitalmedical.model.Team;
import com.health.digitalmedical.view.order.RegisteredMain;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ¿ÆÊÒ½éÉÜ
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
	
	@ViewInject(R.id.imgViewPager)
	ImgViewPager myPager; // Í¼Æ¬ÈÝÆ÷
	
	private Team team;
	
	private List<View> listViews; // Í¼Æ¬×é
	
	private BitmapUtils bitmapUtils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faculty_desc);
		ViewUtils.inject(this);
		this.team=(Team) getIntent().getSerializableExtra("team");
		this.content.setText(team.getIntroduce());
		addActivity(this);
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
		exit();
	}
	
	/**
	 * ³õÊ¼»¯Í¼Æ¬
	 */
	private void InitViewPager()
	{
		bitmapUtils = new BitmapUtils(this);
		bitmapUtils.closeCache();
		
		listViews = new ArrayList<View>();
		String imgUrls=team.getImgUrl();
		String[] imgUrlsT=imgUrls.split(",");
		
//		int[] imageResId = new int[]
//		{ R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
		if(imgUrlsT!=null && imgUrlsT.length>0)
		{
			for (int i = 0; i < imgUrlsT.length; i++)
			{
				ImageView imageView = new ImageView(this);
				bitmapUtils.display(imageView, imgUrlsT[i]);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				listViews.add(imageView);
			}
		}
		
	}
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		InitViewPager();// ³õÊ¼»¯Í¼Æ¬
		myPager.start(this, listViews, 4000, null, R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);
	}
	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub

	}

}
