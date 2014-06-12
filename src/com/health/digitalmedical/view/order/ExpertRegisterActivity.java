package com.health.digitalmedical.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.health.digitalmedical.model.User;
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

public class ExpertRegisterActivity extends BaseActivity
{
	@ViewInject(R.id.title)
	private TextView title;

	@ViewInject(R.id.submit)
	private Button submitBtn;

	@ViewInject(R.id.textView_name)
	private TextView textViewName;

	@ViewInject(R.id.textView_time)
	private TextView textViewTime;

	@ViewInject(R.id.textView_number)
	private TextView textViewNumber;

	@ViewInject(R.id.textView_fee)
	private TextView textViewFee;

	@ViewInject(R.id.edit_name)
	private EditText editName;

	@ViewInject(R.id.edit_phone)
	private EditText editPhone;

	@ViewInject(R.id.edit_idCard)
	private EditText editIdCard;
	@ViewInject(R.id.check_btn)
	private RadioGroup group;
	@ViewInject(R.id.male)
	private RadioButton maleRadio;

	@ViewInject(R.id.female)
	private RadioButton femaleRadio;

	@ViewInject(R.id.check_btn)
	private RadioButton radioGroup;

	@ViewInject(R.id.step_2)
	private ImageView stepTwo;
	private String doctorName;
	private String registerTime;
	private String fee;
	private String registerId;
	private String userOrderNum;
	private String doctorId;
	private String teamId;
	private String teamName;
	private String userId;
	private String userName;
	private String userNo;
	private String userTelephone;
	public String sex;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expert_register_info_config);
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}

	@OnClick(R.id.edit_user_info)
	public void toHisOrder(View v)
	{
		Intent intent = new Intent(ExpertRegisterActivity.this, HisOrderActivity.class);
		intent.putExtra("doctorName", doctorName);
		intent.putExtra("registerTime", registerTime);
		intent.putExtra("fee", fee);
		intent.putExtra("registerId", registerId);
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
		Intent intent = new Intent(ExpertRegisterActivity.this, MainPageActivity.class);
		startActivity(intent);
		exit();
	}

	@OnClick(R.id.submit)
	public void submitOrder(View v)
	{
		if (this.user == null)
		{
			Intent intent = new Intent(ExpertRegisterActivity.this, LoginActivity.class);
			startActivityForResult(intent, 0);
		}
		this.userId = user.getUserId();
		this.userName = editName.getText().toString().trim();
		this.userNo = editIdCard.getText().toString().trim();
		this.userTelephone = editPhone.getText().toString().trim();
		RadioButton radioButton = (RadioButton)findViewById(group.getCheckedRadioButtonId());
		if(radioButton==null)
		{
			HealthUtil.infoAlert(ExpertRegisterActivity.this, "�û��Ա�Ϊ��!");
			return;
		}else
		{
			this.sex=radioButton.getText().toString();
		}
		String idCheckRst = IDCard.IDCardValidate(userNo);
		if ("".equals(userName))
		{
			HealthUtil.infoAlert(ExpertRegisterActivity.this, "�û���Ϊ��!");
			return;
		}
		if (!HealthUtil.isMobileNum(userTelephone))
		{
			HealthUtil.infoAlert(ExpertRegisterActivity.this, "�ֻ�����Ϊ�ջ��ʽ����!");
			return;
		}
		if (!"YES".equals(idCheckRst))
		{
			HealthUtil.infoAlert(ExpertRegisterActivity.this, idCheckRst);
			return;
		}
		dialog.setMessage("����ԤԼ,���Ժ�...");
		dialog.show();
		RequestParams param = webInterface.addUserRegisterOrder(userId, registerId, doctorId, doctorName, userOrderNum, fee, registerTime, userName,
				userNo, userTelephone, sex,teamId, teamName);
		invokeWebServer(param, ADD_REGISTER_ORDER);

	}

	@Override
	protected void initView()
	{
		title.setText("��Ϣȷ��");
		stepTwo.setBackgroundResource(R.drawable.bg_step_2);
		this.doctorName = getIntent().getStringExtra("doctorName");
		this.registerTime = getIntent().getStringExtra("registerTime");
		this.fee = getIntent().getStringExtra("fee");
		this.registerId = getIntent().getStringExtra("registerId");
		this.userOrderNum = getIntent().getStringExtra("userOrderNum");
		this.doctorId = getIntent().getStringExtra("doctorId");
		this.teamId = getIntent().getStringExtra("teamId");
		this.teamName = getIntent().getStringExtra("teamName");

		textViewName.setText(this.doctorName);
		textViewTime.setText(this.registerTime);
		textViewNumber.setText(this.userOrderNum);
		textViewFee.setText(this.fee);

		group.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1)
			{
				// ��ȡ������ѡ�����ID
				int radioButtonId = arg0.getCheckedRadioButtonId();
				// ����ID��ȡRadioButton��ʵ��
				RadioButton rb = (RadioButton) ExpertRegisterActivity.this.findViewById(radioButtonId);
				// �����ı����ݣ��Է���ѡ����
				sex=rb.getText().toString();
			}
		});
	}

	@Override
	protected void initValue()
	{
		this.user = HealthUtil.getUserInfo();
		if (this.user == null)
		{
			Intent intent = new Intent(ExpertRegisterActivity.this, LoginActivity.class);
			startActivityForResult(intent, 0);
		} else
		{
			this.editName.setText(user.getUserName());
			this.editPhone.setText(user.getTelephone());
			this.editIdCard.setText(user.getUserNo());
			this.sex = user.getSex();
			if ("��".equals(user.getSex()))
			{
				maleRadio.setChecked(true);
			} else if ("Ů".equals(user.getSex()))
			{
				femaleRadio.setChecked(true);
			}
		}
		// TODO Auto-generated method stub

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
				this.sex = user.getSex();
				if ("��".equals(user.getSex()))
				{
					maleRadio.setChecked(true);
				} else if ("Ů".equals(user.getSex()))
				{
					femaleRadio.setChecked(true);
				}
			}
			break;

		default:
			break;
		}
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

			HealthUtil.infoAlert(ExpertRegisterActivity.this, "��Ϣ����ʧ�ܣ��������������");
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
			case GET_LIST:
				returnMsg(arg0.result, GET_LIST);
				break;
			case GET_LIST_MORE:
				returnMsg(arg0.result, GET_LIST_MORE);
				break;
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
					HealthUtil.infoAlert(ExpertRegisterActivity.this, "ԤԼ�ɹ�...");
					Intent intent = new Intent(ExpertRegisterActivity.this, ConfirmOrderActivity.class);
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
					HealthUtil.infoAlert(ExpertRegisterActivity.this, "ԤԼʧ�ܣ�������...");
				}
				break;
			}
		} catch (Exception e)
		{
			HealthUtil.infoAlert(ExpertRegisterActivity.this, "ԤԼʧ�ܣ�������...");
		}

	}

}
