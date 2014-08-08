package com.health.digitalmedical.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.health.digitalmedical.R;

public class DoctorListAdapter  extends BaseAdapter
{


	private Context mContext;

	private List<Map<String, Object>> unhandList = new ArrayList<Map<String, Object>>();

	public DoctorListAdapter(Context context, List<Map<String, Object>> unhandList)
	{
		this.mContext=context;
		this.unhandList = unhandList;
	}

	@Override
	public int getCount()
	{
		if (unhandList == null)
		{
			return 0;
		}
		return unhandList.size();
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
			 convertView = LayoutInflater.from(mContext).inflate(R.layout.common_doctor_list_item, null);
			 TextView textView = (TextView)convertView.findViewById( R.id.comdoctor_name);
			 TextView doctorPosition = (TextView)convertView.findViewById( R.id.comdoctor_position);
			 TextView goodJob = (TextView)convertView.findViewById( R.id.good_job);
			 String value=unhandList.get(position).get("text").toString();
			 textView.setText(value);
			 doctorPosition.setText(value+"副教授2");
			 goodJob.setText(value+"从事医疗好多年1");
		}
		return convertView;
	}

}
