package com.weima.aishangyi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.mb.android.utils.Helper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.utils.ServiceHelper;

import cn.jpush.android.api.JPushInterface;


/**
 * 引导页面
 * 
 * @author
 * 
 */
public class LoadingActivity extends Activity{
	private static final int LOADING_TIME_OUT = 1*1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 去除信号栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
        ServiceHelper.startGetClass();
		if (ProjectHelper.isLogin()){
			ServiceHelper.startRefreshToken();
		}
		new Handler().postDelayed(new Runnable() {

			public void run() {
				if (ProjectHelper.isLogin()) {
					NavigationHelper.startActivity(LoadingActivity.this, HomeActivity.class, null, true);
				} else {
					NavigationHelper.startActivity(LoadingActivity.this, UserLoginActivity.class, null, true);
				}
			}

		}, LOADING_TIME_OUT);
	}
}
