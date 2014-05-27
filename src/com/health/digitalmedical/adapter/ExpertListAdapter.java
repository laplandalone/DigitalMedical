package com.health.digitalmedical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.health.digitalmedical.R;
import com.health.digitalmedical.model.Doctor;
import com.health.digitalmedical.model.DoctorList;

public class ExpertListAdapter  extends BaseAdapter
{
	private Context mContext;

	private List<Doctor> doctors;

	public ExpertListAdapter(Context context, DoctorList doctorList)
	{
		this.mContext=context;
		this.doctors = doctorList.getDoctors();
	}

	@Override
	public int getCount()
	{
		if (doctors == null)
		{
			return 0;
		}
		return doctors.size();
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
		// TODO Auto-generated method stub  my_online_select btn_my_online
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			 convertView = LayoutInflater.from(mContext).inflate(R.layout.common_doctor_list_item, null);
			 TextView textView = (TextView)convertView.findViewById( R.id.comdoctor_name);
			 TextView facultyName = (TextView)convertView.findViewById( R.id.good_job);
			 TextView doctorPosition = (TextView)convertView.findViewById( R.id.comdoctor_position);
			 String value=doctors.get(position).getName();
			 textView.setText(value);
			 doctorPosition.setText(doctors.get(position).getPost());
			 facultyName.setText(doctors.get(position).getSkill());
		}
		return convertView;
	}

}
