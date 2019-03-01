package com.weima.aishangyi.app;

import android.database.sqlite.SQLiteDatabase;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.util.EMLog;
import com.mb.android.utils.app.MBApplication;
import com.weima.aishangyi.chat.Constant;
import com.weima.aishangyi.chat.DemoModel;
import com.weima.aishangyi.chat.HxEaseuiHelper;
import com.weima.aishangyi.db.DataBaseOpenHelper;

import cn.jpush.android.api.JPushInterface;

import static com.hyphenate.chat.EMGCMListenerService.TAG;

/**
 * Created by cgy on 16/7/17.
 */
public class WMApplication extends MBApplication {
    private static final String TAG = WMApplication.class.getSimpleName();
    public static RequestQueue volleyQueue;
    private static SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化volley请求队列
        volleyQueue = Volley.newRequestQueue(getInstance());
        initHuanXin();
        initJpush();
    }

    //获取全局请求队列方法
    public static RequestQueue getRequestQueue() {
        return volleyQueue;
    }

    /**
     * function: 得到db
     *
     * @return
     * @ author:linjunying 2011-12-3 上午11:03:55
     */
    public static SQLiteDatabase getSQLiteDataBase() {
        db = DataBaseOpenHelper.getInstance(getInstance());
        return db;
    }

    private void initJpush(){
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }



    //begin huanxin
    private void initHuanXin(){
        EaseUI.getInstance().init(this, null);
        HxEaseuiHelper.getInstance().init(this.getApplicationContext());
//        DemoModel settingsModel = HxEaseuiHelper.getInstance().getModel();
//        settingsModel.setSettingMsgNotification(true);
//        settingsModel.setSettingMsgSound(true);
//        settingsModel.setSettingMsgVibrate(true);
        //设置全局监听
        setGlobalListeners();
    }

    EMConnectionListener connectionListener;

    /**
     * 设置一个全局的监听
     */
    protected void setGlobalListeners(){
        // create the global connection listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                EMLog.d("global listener", "onDisconnect" + error);
                if (error == EMError.USER_REMOVED) {
                    onUserException(Constant.ACCOUNT_REMOVED);
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    onUserException(Constant.ACCOUNT_CONFLICT);
                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
                    onUserException(Constant.ACCOUNT_FORBIDDEN);
                }
            }
            @Override
            public void onConnected() {
                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
            }
        };

        //register connection listener
        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    /**
     * user met some exception: conflict, removed or forbidden
     */
    protected void onUserException(String exception){
        EMLog.e(TAG, "onUserException: " + exception);
    }
    //end huanxin

}
