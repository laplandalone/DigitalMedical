package com.health.digitalmedical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.health.digitalmedical.R;
import com.health.digitalmedical.model.UserQuestionT;

public class QuestionDoctorListAdapter extends BaseAdapter
{
	private Context mContext;

	private List<UserQuestionT> questionTs;

	public QuestionDoctorListAdapter(Context context, List<UserQuestionT> questionTs)
	{
		this.mContext = context;
		this.questionTs = questionTs;
	}

	@Override
	public int getCount()
	{
		if (questionTs == null)
		{
			return 0;
		}
		return questionTs.size();
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.online_doctor_question_item, null);
			TextView textView = (TextView) convertView.findViewById(R.id.telephone);
			TextView content = (TextView) convertView.findViewById(R.id.contentItem);
			UserQuestionT questionT = questionTs.get(position);
			textView.setText(questionT.getUserTelephone());
			content.setText(questionT.getContent());
			// doctorPosition.setText(doctors.get(position).getPost());
			// facultyName.setText(doctors.get(position).getSkill());
		}
		return convertView;
	}

}
