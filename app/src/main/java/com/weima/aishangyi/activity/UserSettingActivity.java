package com.weima.aishangyi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.mb.android.utils.AppHelper;
import com.mb.android.utils.DialogHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.chat.DemoModel;
import com.weima.aishangyi.chat.HxEaseuiHelper;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.GetCodeResp;
import com.weima.aishangyi.entity.UpdateResp;
import com.weima.aishangyi.utils.CacheHelper;
import com.weima.aishangyi.utils.NavigationHelper;

import java.util.HashMap;

/**
 * 设置
 */
public class UserSettingActivity extends BaseActivity {
    private ImageView btn_voiceremind;
    private boolean isRemind = true;
    private DemoModel settingsModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("设置");
        setContentView(R.layout.activity_user_setting);
        initUI();
    }


    private void initUI() {
        settingsModel = HxEaseuiHelper.getInstance().getModel();
        btn_voiceremind = findView(R.id.btn_voiceremind);
        btn_voiceremind.setOnClickListener(mClickListener);
        findView(R.id.btn_normalproblem).setOnClickListener(mClickListener);
        findView(R.id.btn_feekback).setOnClickListener(mClickListener);
        findView(R.id.btn_aboutus).setOnClickListener(mClickListener);
        findView(R.id.btn_clearcache).setOnClickListener(mClickListener);
        findView(R.id.btn_unlogin).setOnClickListener(mClickListener);
        findView(R.id.btn_updateversion).setOnClickListener(mClickListener);
        isRemind = settingsModel.getSettingMsgSound();
        btn_voiceremind.setImageResource(isRemind ? R.drawable.ic_sound_open : R.drawable.ic_sound_close);
    }

    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_normalproblem://常见问题
                    NavigationHelper.startActivity(UserSettingActivity.this, ProblemActivity.class, null, false);
                    break;
                case R.id.btn_feekback://意见反馈
                    NavigationHelper.startActivity(UserSettingActivity.this, UserFeedbackActivity.class, null, false);
                    break;
                case R.id.btn_aboutus://关于我们
                    NavigationHelper.startActivity(UserSettingActivity.this, AboutUsActivity.class, null, false);
                    break;
                case R.id.btn_clearcache://清理缓存
                    //清理缓存
                    DialogHelper.showConfirmDialog(UserSettingActivity.this, "清理缓存", "确定要清理缓存？",
                            true, R.string.dialog_positive, mPositiveListener, R.string.dialog_negative, null);
                    break;
                case R.id.btn_updateversion://版本更新
                    requestData();
                    break;
                case R.id.btn_unlogin://退出登录
                    loginOut();
                    break;
                case R.id.btn_voiceremind://声音提醒
                    isRemind = !isRemind;
                    btn_voiceremind.setImageResource(isRemind?R.drawable.ic_sound_open:R.drawable.ic_sound_close);
                    settingsModel.setSettingMsgSound(isRemind);
                    break;
                default:
                    break;
            }
        }
    };

    private DialogInterface.OnClickListener mPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            ProgressDialogHelper.showProgressDialog(UserSettingActivity.this, "清理缓存中...");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    CacheHelper.cleanCache(UserSettingActivity.this);
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);
                }
            });
            thread.start();
        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ProgressDialogHelper.dismissProgressDialog();
                    ToastHelper.showToast("清理完成");
                    break;
            }
            super.handleMessage(msg);
        }

    };

    /**
     * 退出登录
     */
    private void loginOut(){
        //注销账号
        DialogHelper.showConfirmDialog(UserSettingActivity.this, "注销", "确定要注销当前账号？", true,
                R.string.dialog_positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EMClient.getInstance().logout(false, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                PreferencesHelper.getInstance().putInt(ProjectConstants.Preferences.KEY_FANS_COUNT,0);
                                PreferencesHelper.getInstance().putInt(ProjectConstants.Preferences.KEY_TALENT_COUNT,0);
                                PreferencesHelper.getInstance().putString(ProjectConstants.Preferences.KEY_CURRENT_TOKEN, "");
                                Intent intent = new Intent(UserSettingActivity.this, UserLoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onProgress(int progress, String status) {

                            }

                            @Override
                            public void onError(int code, String error) {

                            }
                        });
                    }

                }, R.string.dialog_negative, null);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        UpdateResp entity = JsonHelper.fromJson(response,UpdateResp.class);
        if ("200".equals(entity.getCode())){
            if (Helper.isNotEmpty(entity.getData())){
                if (String.valueOf(AppHelper.getCurrentVersion()).equals(entity.getData().getVersion())){
                    ToastHelper.showToast("已经是最新版本");
                }else{
                    if (Helper.isNotEmpty(entity.getData().getUrl())){
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(entity.getData().getUrl());
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                }
            }
        }else{
            ToastHelper.showToast(entity.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ToastHelper.showToast("请求异常");
        return true;
    }

    private void requestData(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        post(ProjectConstants.Url.UPDATE, requestMap);
    }


}
