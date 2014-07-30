package com.health.digitalmedical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.health.digitalmedical.R;
import com.health.digitalmedical.model.OrderExpert;
import com.health.digitalmedical.model.OrderExpertList;

public class OrderExpertAdapter  extends BaseAdapter
{
	private Context mContext;

	private List<OrderExpert> orders;

	private String day="";
	public OrderExpertAdapter(Context context, OrderExpertList expertList)
	{
		this.mContext=context;
		this.orders = expertList.getOrders();
	}

	@Override
	public int getCount()
	{
		if (orders == null)
		{
			return 0;
		}
		return orders.size();
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
		
			 convertView = LayoutInflater.from(mContext).inflate(R.layout.expert_doctor_list_item, null);
			 TextView textView = (TextView)convertView.findViewById( R.id.name);
			 TextView weekView =   (TextView)convertView.findViewById( R.id.date);
			 TextView facultyName = (TextView)convertView.findViewById( R.id.faculty_name);
			 TextView doctorPosition = (TextView)convertView.findViewById( R.id.position);
			 LinearLayout layout =(LinearLayout) convertView.findViewById(R.id.countLayout);
			 OrderExpert  expert=orders.get(position);
			 String value=expert.getDoctorName();
			 String weekDay=expert.getDay();
			 textView.setText(value);
			 doctorPosition.setText(expert.getTeamName());
			 if("".equals(day) || !day.equals(weekDay))
			 {
				 weekView.setVisibility(0);
				 weekView.setText(weekDay);
				 day=weekDay;
			 }
			 layout.setVisibility(View.GONE);
			 //facultyName.setText(orders.get(position).getSkill());
		
		return convertView;
	}

}
