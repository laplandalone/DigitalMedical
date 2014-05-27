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

public class TalkAdapter extends BaseAdapter
{

	private Context mContext;

	private List<UserQuestionT> questionTs;

	public TalkAdapter(Context context, List<UserQuestionT> questionTs)
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
		UserQuestionT questionT = questionTs.get(position);
		String recordType=questionT.getRecordType();
		if("ask".equals(recordType))
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.ask_text_item, null);
			TextView textView = (TextView) convertView.findViewById(R.id.content);
			textView.setText(questionT.getContent());
		}else if("ans".equals(recordType))
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.answer_text_item, null);
			TextView textView = (TextView) convertView.findViewById(R.id.anscontent);
			textView.setText(questionT.getContent());
		}
			
		return convertView;
	}

	

}
