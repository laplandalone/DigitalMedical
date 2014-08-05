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
		View view = convertView;
		ViewHolder viewHolder = null;
//		if (view == null)
//		{
			 viewHolder = new ViewHolder();
			 view = LayoutInflater.from(mContext).inflate(R.layout.expert_doctor_list_item, null);		
			 viewHolder.textView = (TextView)view.findViewById( R.id.name);
			 viewHolder.weekView =   (TextView)view.findViewById( R.id.date);
			 viewHolder.doctorPosition = (TextView)view.findViewById( R.id.position);
			 viewHolder.layout =(LinearLayout) view.findViewById(R.id.countLayout);
			 
//			 view.setTag(viewHolder);
//		}else
//		{
//			viewHolder = (ViewHolder) view.getTag();
//		}
	
		 OrderExpert  expert=orders.get(position);
		 String value=expert.getDoctorName();
		 String weekDay=expert.getDay();
		 String week=expert.getWeek();
		 String display=expert.getDisplay();
		 viewHolder.textView.setText(value);
		 viewHolder.doctorPosition.setText(expert.getTeamName());
		 if("Y".equals(display))
		 {
			 viewHolder.weekView.setVisibility(View.VISIBLE);
			 viewHolder.weekView.setText(weekDay+" ÐÇÆÚ"+week);
			 day=weekDay;
		 }
		 viewHolder.layout.setVisibility(View.GONE);
		 
		return view;
	}
	

	private class ViewHolder
	{
		public TextView textView;
		public TextView weekView;
		public TextView doctorPosition;
		LinearLayout layout;
	}
}
