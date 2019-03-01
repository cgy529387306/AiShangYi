package com.weima.aishangyi.utils;

import android.content.Intent;

import com.mb.android.utils.app.MBApplication;
import com.weima.aishangyi.service.GetClassService;
import com.weima.aishangyi.service.GetCommonDataService;
import com.weima.aishangyi.service.RefreshTokenService;

/**
 */
public class ServiceHelper {

    public static final String TAG = ServiceHelper.class.getSimpleName();

    /**
     * 请求Url配置
     */
    public static void startRefreshToken() {
        Intent startOffline = new Intent(MBApplication.getInstance(), RefreshTokenService.class);
        MBApplication.getInstance().startService(startOffline);
    }

    /**
     * 停止Url配置请求
     */
    public static void stopUrlConfig() {
        Intent stopOffline = new Intent(MBApplication.getInstance(), RefreshTokenService.class);
        MBApplication.getInstance().stopService(stopOffline);
    }

    /**
     * 请求获取课程列表数据
     */
    public static void startGetClass() {
        Intent startOffline = new Intent(MBApplication.getInstance(), GetClassService.class);
        MBApplication.getInstance().startService(startOffline);
    }

    /**
     * 停止求获取课程列表数据
     */
    public static void stopGetClass() {
        Intent stopOffline = new Intent(MBApplication.getInstance(), GetClassService.class);
        MBApplication.getInstance().stopService(stopOffline);
    }

    /**
     * 请求获取课程列表数据
     */
    public static void startGetData() {
        Intent startOffline = new Intent(MBApplication.getInstance(), GetCommonDataService.class);
        MBApplication.getInstance().startService(startOffline);
    }

    /**
     * 停止求获取课程列表数据
     */
    public static void stopGetData() {
        Intent stopOffline = new Intent(MBApplication.getInstance(), GetCommonDataService.class);
        MBApplication.getInstance().stopService(stopOffline);
    }
}
