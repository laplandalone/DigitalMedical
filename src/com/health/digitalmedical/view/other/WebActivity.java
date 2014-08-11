package com.health.digitalmedical.view.other;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.health.digitalmedical.BaseActivity;
import com.health.digitalmedical.MainPageActivity;
import com.health.digitalmedical.R;
import com.health.digitalmedical.tools.HealthUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WebActivity extends BaseActivity 
{
	@ViewInject(R.id.title)
	private TextView title;
	
	WebView web;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_webview);
		 web = (WebView) findViewById(R.id.webview);  
		String url = "http://hiseemedical.com:10822/declare.html"; 
		 if(web != null) 
	        { 
	            web.setWebViewClient(new WebViewClient() 
	            { 
	            	
	                @Override 
	                public void onPageFinished(WebView view,String url) 
	                { 
	                	System.out.println("1");
	                    dialog.dismiss(); 
	                }

					@Override
					public void onReceivedError(WebView view, int errorCode,
							String description, String failingUrl) 
					{
						System.out.println("2");
						HealthUtil.infoAlert(WebActivity.this, "加载失败,请重试...");
						web.setVisibility(View.GONE);
						super.onReceivedError(view, errorCode, description, failingUrl);
					} 
	                
	            }); 
	             
	            loadUrl(url); 
	        } 
		
		ViewUtils.inject(this);
		addActivity(this);
		initView();
		initValue();
	}

	  public void loadUrl(String url) 
	    { 
	        if(web != null) 
	        { 
	            web.loadUrl(url); 
	            dialog = ProgressDialog.show(this,null,"加载中，请稍后..."); 
	            web.reload(); 
	        } 
	    } 
	  
	@Override
	protected void initView()
	{
		// TODO Auto-generated method stub
		title.setText("免责声明");
	}


	@OnClick(R.id.back)
	public void toHome(View v)
	{
		Intent intent = new Intent(WebActivity.this, MainPageActivity.class);
		startActivity(intent);
		exit();
	}
	
	@Override
	protected void initValue()
	{
		
	}
	
}
