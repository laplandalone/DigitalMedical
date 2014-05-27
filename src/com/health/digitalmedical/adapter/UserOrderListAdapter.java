package com.health.digitalmedical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.digitalmedical.R;
import com.health.digitalmedical.model.OrderExpert;
import com.health.digitalmedical.model.RegisterOrderT;
import com.health.digitalmedical.model.UserQuestionT;

public class UserOrderListAdapter extends BaseAdapter
{
	private Context mContext;

	private List<RegisterOrderT> registerOrderTs;

	public UserOrderListAdapter(Context context, List<RegisterOrderT> registerOrderTs)
	{
		this.mContext = context;
		this.registerOrderTs = registerOrderTs;
	}

	@Override
	public int getCount()
	{
		if (registerOrderTs == null)
		{
			return 0;
		}
		return registerOrderTs.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			 convertView = LayoutInflater.from(mContext).inflate(R.layout.user_order_list_item, null);
			 TextView textView =  (TextView)convertView.findViewById( R.id.text1);
			 TextView textView2 = (TextView)convertView.findViewById( R.id.text22);
			 TextView textView3 = (TextView)convertView.findViewById( R.id.text3);
			 TextView textView4 = (TextView)convertView.findViewById( R.id.text4);
			 ImageView localImageView = (ImageView)convertView.findViewById(R.id.icon);
			 RegisterOrderT registerOrderT=registerOrderTs.get(position);
			 
			// localImageView.setVisibility(0);
			 textView.setText(registerOrderT.getRegisterTime());
			 textView3.setText(registerOrderT.getTeamName());
			 textView4.setText(registerOrderT.getOrderNum());
		}
		return convertView;
	}

}
