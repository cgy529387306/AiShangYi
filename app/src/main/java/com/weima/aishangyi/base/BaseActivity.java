package com.weima.aishangyi.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mb.android.utils.DialogHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.NetworkHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.UserLoginActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.io.File;
import java.util.HashMap;

/**
 * Created by cgy on 16/7/18.
 */
public class BaseActivity extends AppCompatActivity implements ResponseListener {
    public static final String BROADCAST_KEY_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    // 网络状态
    protected boolean isNetworkAvailable = false;
    protected BroadcastReceiver netWorkStateChangeReceiver;
    private TextView mTitle;
    private TextView mAction;
    private ImageView mImgAciton;
    private ImageView mImgAciton2;
    private LinearLayout lin_actionbar_back;
    protected InputMethodManager inputMethodManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        RelativeLayout custom_acitonbar = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.common_actionbar_back, null);
        getSupportActionBar().setCustomView(custom_acitonbar,params);
        getSupportActionBar().setElevation(0);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        lin_actionbar_back = (LinearLayout) custom_acitonbar.findViewById(R.id.lin_actionbar_back);
        lin_actionbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mTitle = (TextView) custom_acitonbar.findViewById(R.id.txv_actionbar_title);
        mAction = (TextView) custom_acitonbar.findViewById(R.id.txv_actionbar_action);
        mImgAciton = (ImageView) custom_acitonbar.findViewById(R.id.imv_actionbar_action);
        mImgAciton2 = (ImageView) custom_acitonbar.findViewById(R.id.imv_actionbar_action2);
        isNetworkAvailable = NetworkHelper.isNetworkAvailable(this);
        registerNetWorkConnectivityChangeReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doNetWorkChange();
    }

    /**
     *
     * function: 网络变化接收
     *
     *
     * @ author:cgy 2014-12-26 上午10:12:37
     */
    private void registerNetWorkConnectivityChangeReceiver() {

        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_KEY_CONNECTIVITY_CHANGE);
        filter.setPriority(1000);

        netWorkStateChangeReceiver = new BroadcastReceiver() {


            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(null != action && BROADCAST_KEY_CONNECTIVITY_CHANGE.equals(action)){
                    boolean temp = NetworkHelper.isNetworkAvailable(BaseActivity.this);
                    if(temp != isNetworkAvailable) {
                        isNetworkAvailable = temp;
                        doNetWorkChange();
                    }
                    return;
                }
            }
        };

        registerReceiver(netWorkStateChangeReceiver, filter);
    }

    // 处理网络连接状态变化
    protected void doNetWorkChange() {
        // 没有网络
        if(!isNetworkAvailable) {
            DialogHelper.showConfirmDialog(BaseActivity.this, "提示", "当前网络连接中断,请设置网络", true,
                    R.string.dialog_positive, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NetworkHelper.openSetting(BaseActivity.this);
                        }

                    }, R.string.dialog_negative, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != netWorkStateChangeReceiver){
            unregisterReceiver(netWorkStateChangeReceiver);
        }
    }


    /**
     * @ author:gy 2014年8月7日 下午2:53:01
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 隐藏返回按钮
     */
    public void hideBack(){
        lin_actionbar_back.setVisibility(View.INVISIBLE);
    }


    /**
     * 设置标题
     * @param title
     */
    public void setCustomTitle(String title){
        mTitle.setText(title);
    }


    /**
     * 设置有按钮
     * @param btnText
     */
    public void setRightButton(String btnText,View.OnClickListener listener){
        mAction.setVisibility(View.VISIBLE);
        mAction.setText(btnText);
        mAction.setOnClickListener(listener);
    }

    /**
     * 设置有按钮
     * @param btnText
     */
    public void setRightButton(String btnText,int background, View.OnClickListener listener){
        mAction.setVisibility(View.VISIBLE);
        mAction.setText(btnText);
        mAction.setBackgroundResource(background);
        mAction.setOnClickListener(listener);
    }

    /**
     * 设置有按钮
     */
    public void setImageRightButton(int resId, View.OnClickListener listener){
        mImgAciton.setVisibility(View.VISIBLE);
        mImgAciton.setImageResource(resId);
        mImgAciton.setOnClickListener(listener);
    }

    /**
     * 设置有按钮
     */
    public ImageView setImageRightButton2(int resId, View.OnClickListener listener){
        mImgAciton2.setVisibility(View.VISIBLE);
        mImgAciton2.setImageResource(resId);
        mImgAciton2.setOnClickListener(listener);
        return mImgAciton2;
    }



    /**
     * 发送get请求
     * @param url url地址
     * @param extras 附加参数（本地参数，将原样返回给回调）
     */
    public void get(String url, Object... extras) {
        LogHelper.i(url);
        // 构建请求实体
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(url).setRequestHeader(ProjectHelper.getUserAgent1()).getRequestEntity();
        // 发送请求
        RequestHelper.get(entity, this, extras);
    }

//    /**
//     * 发送get请求
//     * @param url 接口地址
//     * @param requestParamsMap 请求参数Map
//     * @param extras 附加参数（本地参数，将原样返回给回调）
//     */
//    public void get(String url, HashMap<String, String> requestParamsMap, Object... extras) {
//        LogHelper.i(url);
//        HashMap<String, String> encodeParams = new HashMap<String, String>();
//        encodeParams.put("params", UserAgentHelper.simpleEncode(ProjectHelper.mapToJson(requestParamsMap)));
//        RequestEntity entity = new RequestEntity.Builder().setUrl(url).setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(encodeParams).getRequestEntity();
//        RequestHelper.get(entity, this, extras);
//    }


    /**
     * 发送get请求
     * @param url 接口地址
     * @param requestParamsMap 请求参数Map
     * @param extras 附加参数（本地参数，将原样返回给回调）
     */
    public void post(String url, HashMap<String, Object> requestParamsMap, Object... extras) {
        LogHelper.i(url);
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(url).setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestParamsMap).getRequestEntity();
        RequestHelper.post(entity, this, extras);
    }



    /**
     * 发送get请求
     * @param url 接口地址
     * @param fileKey 文件名
     * @param filePath 文件路径
     */
    public void upload(String url,String fileKey,String filePath,AsyncHttpResponseHandler listener) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("device","0");
            if (ProjectHelper.isLogin()){
                String token = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CURRENT_TOKEN);
                if (Helper.isNotEmpty(token)){
                    client.addHeader("Authorization","Bearer "+token);
                }
            }
            RequestParams params = new RequestParams();
            params.put(fileKey, new File(filePath));
            client.post(url, params,listener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        if (Helper.isNotEmpty(response)){
            CommonEntity entity = JsonHelper.fromJson(response, CommonEntity.class);
            if (Helper.isNotEmpty(entity) && "401".equals(entity.getCode())){
                ToastHelper.showToast("token失效，请重新登录");
                NavigationHelper.startActivity(BaseActivity.this, UserLoginActivity.class, null, false);
            }
        }
        LogHelper.i(response);
        return false;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        LogHelper.i(response);
        return false;
    }

    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
