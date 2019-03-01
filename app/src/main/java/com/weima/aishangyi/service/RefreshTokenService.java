package com.weima.aishangyi.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.activity.UserLoginActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.LoginResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class RefreshTokenService extends Service{
    private static final int REFRESH_TIME_INTERVAL = 24*60*60*1000;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        refreshToken();
        return super.onStartCommand(intent, flags, startId);
    }

    private void refreshToken() {
        Timer timer = new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                String token = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CURRENT_TOKEN);
                if (Helper.isNotEmpty(token)) {
                    doRefresh(token);
                }
            }
        };
        timer.schedule(task,1, REFRESH_TIME_INTERVAL);
    }

    private void doRefresh(String token){
        HashMap<String,Object> requestMap = new HashMap<String, Object>();
        requestMap.put("token",token);
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(30000).setUrl(ProjectConstants.Url.ACCOUNT_REFRESH_TOKEN)
                .setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestMap).getRequestEntity();
        RequestHelper.post(entity, new ResponseListener() {
            @Override
            public boolean onResponseSuccess(int gact, String response, Object... extras) {
                LoginResp entity = JsonHelper.fromJson(response, LoginResp.class);
                if ("200".equals(entity.getCode())) {
                    PreferencesHelper.getInstance().putString(ProjectConstants.Preferences.KEY_CURRENT_TOKEN, entity.getToken());
                } else if ("100".equals(entity.getCode())){
                    ToastHelper.showToast("token失效，请重新登录");
                    Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
                return false;
            }
        });
    }

}
