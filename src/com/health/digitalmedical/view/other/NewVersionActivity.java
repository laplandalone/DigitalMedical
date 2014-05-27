package com.health.digitalmedical.view.other;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.tools.HealthConstant;
import com.health.digitalmedical.tools.HealthUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class NewVersionActivity extends BaseActivity implements OnClickListener{
	private Button updateBtn;
	private Button cancelBtn;
	private TextView remarkTxt;
	
	private Bundle mBundle;
	private String mApplicationUrl;
	private String mForceUpdateFlag;
	private String mApplicationVersionCode;
	private HttpUtils mHttpUtils;
	private HttpHandler<File> mHttpHandler;

	protected void initView() {
		// TODO Auto-generated method stub
		updateBtn = (Button) findViewById(R.id.new_version_upload);
		cancelBtn = (Button) findViewById(R.id.new_version_cancel);
		remarkTxt = (TextView) findViewById(R.id.new_version_remark);
		updateBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}

	protected void initValue() {
		mApplicationVersionCode = mBundle.getString("applicationVersionCode");
		mApplicationUrl = mBundle.getString("applicationUrl");
		mForceUpdateFlag = mBundle.getString("forceUpdateFlag");
		remarkTxt.setText("版本：" + mApplicationVersionCode + "\n更新内容：\n" + mBundle.getString("remark"));
		if (!"N".equalsIgnoreCase(mForceUpdateFlag)) {
			cancelBtn.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_version);
		mHttpUtils = new HttpUtils();
		mBundle = this.getIntent().getExtras();
		initView();
		initValue();
	}
	
	public void updateBtn() {
		updateBtn.setEnabled(false);
		mHttpHandler = mHttpUtils.download(mApplicationUrl, HealthConstant.Download_path+"digitalhealth" + mApplicationVersionCode + ".apk", true, new RequestCallBack<File>() {
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				
				float size = current / 1024F /1024F;
//				float totalSize = total / 1024F /1024F;
				updateBtn.setText("正在下载(已下载" + size + "M)");
				super.onLoading(total, current, isUploading);
			}

			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				updateBtn.setText("下载完成");
				installApkByGuide(HealthConstant.Download_path+"digitalhealth" + mApplicationVersionCode + ".apk");
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				HealthUtil.infoAlert(NewVersionActivity.this, arg1);
				if (arg0 != null && arg0.getExceptionCode() == 416) {
					installApkByGuide(HealthConstant.Download_path+"haochilao" + mApplicationVersionCode + ".apk");
				} else {
					updateBtn.setText("点击重试");
					updateBtn.setEnabled(true);
				}
			}
		});
	}
	
	 private void installApkByGuide(String localFilePath) {
		    Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        intent.setDataAndType(Uri.parse("file://" + localFilePath),"application/vnd.android.package-archive");
	        startActivity(intent);
	  }
	
	public void cancelBtn() {
		if (mHttpHandler != null) {
			mHttpHandler.stop();
			mHttpHandler = null;
		}
		finish();
	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onBackPressed() {
//		if ("N".equalsIgnoreCase(mForceUpdateFlag)) {
//			super.onBackPressed();
//		}
//	}
//	
//	
//	
//	@Override
//	protected void onDestroy() {
//		if (mHttpHandler != null) {
//			mHttpHandler.stop();
//			mHttpHandler = null;
//		}
//		super.onDestroy();
//	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.new_version_upload:
			updateBtn();
			break;
		case R.id.new_version_cancel:
			cancelBtn();
			break;
		}
		
	}
	
	
	
	

}
