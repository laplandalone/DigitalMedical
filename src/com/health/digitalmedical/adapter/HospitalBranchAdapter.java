package com.health.digitalmedical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.health.digitalmedical.R;
import com.health.digitalmedical.model.HospitalT;
import com.health.digitalmedical.model.TeamT;

public class HospitalBranchAdapter extends BaseAdapter
{
	private Context mContext;

	private List<TeamT> teamTs;

	public HospitalBranchAdapter(Context context, List<TeamT> teamTs)
	{
		this.mContext = context;
		this.teamTs = teamTs;
	}
	
	@Override
	public int getCount()
	{
		if(teamTs!=null)
		{
			return teamTs.size();
		}
		return 0;
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.hospital_list_item, null);
			TextView textView = (TextView) convertView.findViewById(R.id.name);
			TextView textView1 = (TextView) convertView.findViewById(R.id.faculty_name);
			TextView textView2 = (TextView) convertView.findViewById(R.id.position);
			TeamT teamT = teamTs.get(position);
			textView.setText(teamT.getTeamName());
			textView1.setText(teamT.getAddress());
			textView2.setText(teamT.getBusRoute());
		}
		return convertView;
	}

}
