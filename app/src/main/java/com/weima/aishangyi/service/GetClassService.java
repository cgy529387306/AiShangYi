package com.weima.aishangyi.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.VolleyError;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ClassTypeResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.utils.ServiceHelper;

import java.util.HashMap;

public class GetClassService extends Service{
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        doGet();
        return super.onStartCommand(intent, flags, startId);
    }

    private void doGet(){
        HashMap<String,Object> requestMap = new HashMap<String, Object>();
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(30000).setUrl(ProjectConstants.Url.GET_LESSON_TYPE)
                .setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestMap).getRequestEntity();
        RequestHelper.post(entity, new ResponseListener() {
            @Override
            public boolean onResponseSuccess(int gact, String response, Object... extras) {
                ServiceHelper.stopGetClass();
                ClassTypeResp entity = JsonHelper.fromJson(response, ClassTypeResp.class);
                if ("200".equals(entity.getCode())) {
                    PreferencesHelper.getInstance().putString(ProjectConstants.Preferences.KEY_CLASS_TYPE, response);
                } else {
                    LogHelper.e("########getClassTypeError##########", entity.getMessage());
                }
                return true;
            }

            @Override
            public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
                ServiceHelper.stopGetClass();
                return false;
            }
        });
    }

}
