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
import com.health.digitalmedical.model.Team;
import com.health.digitalmedical.model.TeamList;

public class FacultyListAdapter extends BaseAdapter
{
	private Context mContext;

	private List<Team> teams ;

	public FacultyListAdapter(Context context,  TeamList unhandList)
	{
		this.mContext = context;
		this.teams = unhandList.getTeams();
	}

	@Override
	public int getCount()
	{
		if (teams == null)
		{
			return 0;
		}
		return teams.size();
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
		
			convertView = LayoutInflater.from(mContext).inflate(R.layout.img_list_item, null);
			TextView textView = (TextView) convertView.findViewById(R.id.comtext1);
			String value = teams.get(position).getTeamName().toString();
			textView.setText(value);
		
		return convertView;
	}

}