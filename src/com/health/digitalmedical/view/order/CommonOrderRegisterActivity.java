package com.health.digitalmedical.view.order;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.model.Team;
import com.health.digitalmedical.model.User;
import com.health.digitalmedical.tools.DateUtils;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.health.digitalmedical.tools.IDCard;
import com.health.digitalmedical.view.user.LoginActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class CommonOrderRegisterActivity extends BaseActivity
{

	@ViewInject(R.id.calendar_btn)
	private ImageButton calendarBtn;

	@ViewInject(R.id.register_date)
	private TextView registerDate;
	
	@ViewInject(R.id.department_name)
	private TextView teamTV;

	@ViewInject(R.id.edit_name)
	private EditText editName;

	@ViewInject(R.id.textView_time_1)
	private TextView time1;
	
	@ViewInject(R.id.textView_time_2)
	private TextView time2;
	
	@ViewInject(R.id.edit_phone)
	private EditText editPhone;

	@ViewInject(R.id.edit_idCard)
	private EditText editIdCard;

	@ViewInject(R.id.image_time_1)
	private ImageView imageTime1;
	
	@ViewInject(R.id.image_time_2)
	private ImageView imageTime2;
	
	@ViewInject(R.id.guahao)
	private Button submitBtn;
	
	@ViewInject(R.id.check_btn)
	private RadioGroup group;
	
	String thisDate = DateUtils.getCHNDate();

	ArrayList<String> data = DateUtils.getAfterDate();

	private User user;
	private String doctorName = "0";
	private String registerTime;
	private String fee = "0";
	private String registerId = "0";
	private String userOrderNum = "0";
	private String doctorId = "0";
	private String teamId;
	private String teamName;
	private String userId;
	private String userName;
	private String userNo;
	private String userTelephone;
	private String sex;
	private Team team;
    private String dayTime="����";
    private String dayWeek="";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_department_detail);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}
	
	@OnClick(R.id.textView_time_1)
	public void chooseDay1(View v)
	{
		String dateStr=registerDate.getText().toString()+" 12:00:00";
		if(DateUtils.checkDay(dateStr))
		{
			HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "ԤԼʱ���ѹ���");
			return;
		}
		this.dayTime="����";
		imageTime1.setVisibility(View.VISIBLE);
		imageTime2.setVisibility(View.GONE);
	}
	
	@OnClick(R.id.textView_time_2)
	public void chooseDay2(View v)
	{
		imageTime1.setVisibility(View.GONE);
		imageTime2.setVisibility(View.VISIBLE);
		this.dayTime="����";
	}
	
	@OnClick(R.id.edit_user_info)
	public void toHisOrder(View v)
	{
		String dateStr=registerDate.getText().toString()+" 16:30:00";
		if(DateUtils.checkDay(dateStr))
		{
			HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "ԤԼʱ���ѹ���");
			return;
		}
		Intent intent = new Intent(CommonOrderRegisterActivity.this, HisOrderActivity.class);
		
		dayWeek=DateUtils.getWeekOfStr(registerDate.getText().toString());
		intent.putExtra("doctorName", doctorName);
		intent.putExtra("registerTime", registerDate.getText()+" ����"+dayWeek+" "+dayTime);
		intent.putExtra("fee", fee);
		intent.putExtra("registerId",registerId);
		intent.putExtra("userOrderNum", userOrderNum);
		intent.putExtra("doctorId", doctorId);
		intent.putExtra("teamId", teamId);
		intent.putExtra("teamName", teamName);
		startActivity(intent);
		finish();
	}

	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(CommonOrderRegisterActivity.this, MainPageActivity.class);
		startActivity(intent);
		exit();
	}

	@OnClick(R.id.calendar_btn)
	public void clickCalendarBtn(View v)
	{
		new AlertDialog.Builder(CommonOrderRegisterActivity.this).setTitle("��ʾ").setIcon(android.R.drawable.ic_dialog_map)
				.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						registerDate.setText(data.get(which));
					}
				}).create().show();
	}

	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		registerDate.setText(thisDate);
		String dateStr=thisDate+" 12:00:00";
		if(DateUtils.checkDay(dateStr))
		{
			imageTime1.setVisibility(View.GONE);
			imageTime2.setVisibility(View.VISIBLE);
			this.dayTime="����";
		}else
		{
			imageTime1.setVisibility(View.VISIBLE);
			imageTime2.setVisibility(View.GONE);
			this.dayTime="����";
		}
		
		group.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1)
			{
				// ��ȡ������ѡ�����ID
				int radioButtonId = arg0.getCheckedRadioButtonId();
				// ����ID��ȡRadioButton��ʵ��
				RadioButton rb = (RadioButton) CommonOrderRegisterActivity.this.findViewById(radioButtonId);
				// �����ı����ݣ��Է���ѡ����
				sex=rb.getText().toString();
			}
		});
	}

	@Override
	protected void initValue()
	{
		// TODO Auto-generated method stub
		this.team = (Team) getIntent().getSerializableExtra("team");
		this.teamId = team.getTeamId();
		this.teamName = team.getTeamName();
		teamTV.setText(teamName);
		this.user = HealthUtil.getUserInfo();
		if (this.user == null)
		{
			Intent intent = new Intent(CommonOrderRegisterActivity.this, LoginActivity.class);
			startActivityForResult(intent, 0);
		} else
		{
			this.editName.setText(user.getUserName());
			this.editPhone.setText(user.getTelephone());
			this.editIdCard.setText(user.getUserNo());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		switch (resultCode)
		{
		case RESULT_OK:
			this.user = HealthUtil.getUserInfo();
			if (this.user != null)
			{
				this.editName.setText(user.getUserName());
				this.editPhone.setText(user.getTelephone());
				this.editIdCard.setText(user.getUserNo());
			}
			break;

		default:
			break;
		}
	}

	@OnClick(R.id.guahao)
	public void submitOrder(View v)
	{
		this.userId = user.getUserId();
		this.userName = editName.getText().toString().trim();
		this.userNo = editIdCard.getText().toString().trim();
		this.userTelephone = editPhone.getText().toString().trim();
		dayWeek=DateUtils.getWeekOfStr(registerDate.getText().toString());
		
		this.registerTime=registerDate.getText().toString().trim()+" ����"+dayWeek+" "+dayTime;
		
		String dateStr=registerDate.getText().toString()+" 16:30:00";
		if(DateUtils.checkDay(dateStr))
		{
			HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "ԤԼʱ���ѹ���");
			return;
		}
	
		String idCheckRst = IDCard.IDCardValidate(userNo);
		RadioButton radioButton = (RadioButton)findViewById(group.getCheckedRadioButtonId());
		if(radioButton==null)
		{
			HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "�û��Ա�Ϊ��!");
			return;
		}else
		{
			this.sex=radioButton.getText().toString();
		}
		if ("".equals(userName))
		{
			HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "�û���Ϊ��!");
			return;
		}
		if (!HealthUtil.isMobileNum(userTelephone))
		{
			HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "�ֻ�����Ϊ�ջ��ʽ����!");
			return;
		}
		if (!"YES".equals(idCheckRst))
		{
			HealthUtil.infoAlert(CommonOrderRegisterActivity.this, idCheckRst);
			return;
		}
		
		dialog.setMessage("����ԤԼ,���Ժ�...");
		dialog.show();

		RequestParams param = webInterface.addUserRegisterOrder(userId, registerId, doctorId, doctorName, userOrderNum, fee, registerTime, userName,
				userNo, userTelephone, sex, teamId, teamName);
		invokeWebServer(param, ADD_REGISTER_ORDER);

	}

	/**
	 * ����web����
	 * 
	 * @param param
	 */
	private void invokeWebServer(RequestParams param, int responseCode)
	{
		HealthUtil.LOG_D(getClass(), "connect to web server");
		MineRequestCallBack requestCallBack = new MineRequestCallBack(responseCode);
		if (httpHandler != null)
		{
			httpHandler.stop();
		}
		httpHandler = mHttpUtils.send(HttpMethod.POST, HealthConstant.URL, param, requestCallBack);
	}

	/**
	 * ��ȡ��̨���ص�����
	 */
	class MineRequestCallBack extends RequestCallBack<String>
	{

		private int responseCode;

		public MineRequestCallBack(int responseCode)
		{
			super();
			this.responseCode = responseCode;
		}

		@Override
		public void onFailure(HttpException error, String msg)
		{
			HealthUtil.LOG_D(getClass(), "onFailure-->msg=" + msg);
			if (dialog.isShowing())
			{
				dialog.cancel();
			}

			HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "��Ϣ����ʧ�ܣ��������������");
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0)
		{
			// TODO Auto-generated method stub
			HealthUtil.LOG_D(getClass(), "result=" + arg0.result);
			if (dialog.isShowing())
			{
				dialog.cancel();
			}
			switch (responseCode)
			{
			case ADD_REGISTER_ORDER:
				returnMsg(arg0.result, ADD_REGISTER_ORDER);
				break;
			}
		}

	}

	/*
	 * �����ؽ������
	 */
	private void returnMsg(String json, int responseCode)
	{
		try
		{
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(json);
			JsonObject jsonObject = jsonElement.getAsJsonObject();

			switch (responseCode)
			{
			case GET_LIST:

				break;
			case ADD_REGISTER_ORDER:
				String result = jsonObject.get("returnMsg").toString();
				if ("true".equals(result))
				{
					HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "ԤԼ�ɹ�...");
					Intent intent = new Intent(CommonOrderRegisterActivity.this, ConfirmOrderActivity.class);
					intent.putExtra("doctorName", doctorName);
					intent.putExtra("registerTime", registerTime);
					intent.putExtra("fee", fee);
					intent.putExtra("userOrderNum", userOrderNum);
					intent.putExtra("teamName", teamName);
					intent.putExtra("userName", userName);
					intent.putExtra("userNo", userNo);
					intent.putExtra("userTelephone", userTelephone);
					intent.putExtra("sex", sex);
					startActivity(intent);
					finish();
				} else
				{
					HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "ԤԼʧ�ܣ�������...");
				}
				break;
			}
		} catch (Exception e)
		{
			HealthUtil.infoAlert(CommonOrderRegisterActivity.this, "ԤԼʧ�ܣ�������...");
		}

	}

}
