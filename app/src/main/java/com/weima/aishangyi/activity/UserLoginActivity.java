package com.weima.aishangyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hyphenate.chat.EMClient;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.AppConstants;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.LoginResp;
import com.weima.aishangyi.sina.AccessTokenKeeper;
import com.weima.aishangyi.sina.UsersAPI;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.widget.ClearableEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 用户登录
 * Created by cgy on 16/7/19.
 */
public class UserLoginActivity extends BaseActivity{
    private ClearableEditText edt_user_account;
    private ClearableEditText edt_user_password;
    private TextView txv_user_register;
    private TextView txv_user_forget_pwd;
    private Button btn_user_comfirm;
    private Tencent mTencent;
    private String openId;
    private IWXAPI api;
    private AuthInfo mAuthInfo;
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;
    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        getSupportActionBar().hide();
        setCustomTitle("登录");
        initUI();
        regToWxLogin();
    }

    private void initUI() {
        edt_user_account = findView(R.id.edt_user_account);
        edt_user_password = findView(R.id.edt_user_password);
        btn_user_comfirm = findView(R.id.btn_user_comfirm);
        txv_user_register = findView(R.id.txv_user_register);
        txv_user_forget_pwd = findView(R.id.txv_user_forget_pwd);
        btn_user_comfirm.setOnClickListener(mClickListener);
        txv_user_register.setOnClickListener(mClickListener);
        txv_user_forget_pwd.setOnClickListener(mClickListener);
        edt_user_account.addTextChangedListener(myTextWatcher);
        edt_user_password.addTextChangedListener(myTextWatcher);
        findView(R.id.btn_login_wechat).setOnClickListener(mClickListener);
        findView(R.id.btn_login_qq).setOnClickListener(mClickListener);
        findView(R.id.btn_login_sina).setOnClickListener(mClickListener);
    }

    private void regToWxLogin() {
        //通过WXAPIFactory工厂,获取IWXAPI的实例
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, AppConstants.WX_APP_ID, true);
            //将应用的appId注册到微信
            api.registerApp(AppConstants.WX_APP_ID);
        }
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String phone = edt_user_account.getText().toString().trim();
            String password = edt_user_password.getText().toString().trim();
            if (Helper.isNotEmpty(phone) && ProjectHelper.isMobiPhoneNum(phone) && Helper.isNotEmpty(password) ){
                btn_user_comfirm.setClickable(true);
                btn_user_comfirm.setBackgroundResource(R.drawable.shape_btn_orange);
            }else {
                btn_user_comfirm.setClickable(false);
                btn_user_comfirm.setBackgroundResource(R.drawable.shape_btn_gray);
            }
        }
    };

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        ProgressDialogHelper.dismissProgressDialog();
        int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
        if (requestType == 1){
            LoginResp entity = JsonHelper.fromJson(response,LoginResp.class);
            if ("200".equals(entity.getCode())){
                ToastHelper.showToast("登录成功");
                PreferencesHelper.getInstance().putString(ProjectConstants.Preferences.KEY_CURRENT_TOKEN, entity.getToken());
                if (Helper.isNotEmpty(entity.getData()) && Helper.isNotEmpty(entity.getData().getBind_phone())){
                    NavigationHelper.startActivity(UserLoginActivity.this, HomeActivity.class, null, true);
                }else{
                    NavigationHelper.startActivity(UserLoginActivity.this, UserphoneBActivity.class, null, true);
                }
            }else{
                ToastHelper.showToast(entity.getMessage());
            }
            return true;
        }
        LoginResp entity = JsonHelper.fromJson(response,LoginResp.class);
        if ("200".equals(entity.getCode())){
            PreferencesHelper.getInstance().putString(ProjectConstants.Preferences.KEY_CURRENT_TOKEN,entity.getToken());
            EMClient.getInstance().updateCurrentUserNick(entity.getData().getNickname());
            NavigationHelper.startActivity(UserLoginActivity.this, HomeActivity.class, null, true);
            ToastHelper.showToast("登录成功");
        }else{
            ToastHelper.showToast(entity.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ProgressDialogHelper.dismissProgressDialog();
        ToastHelper.showToast("登录失败");
        return true;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProjectHelper.disableViewDoubleClick(v);
            switch (v.getId()) {
                case R.id.txv_user_register:
                    NavigationHelper.startActivity(UserLoginActivity.this,
                            UserRegisterActivity.class, null, false);
                    break;
                case R.id.txv_user_forget_pwd:
                    NavigationHelper.startActivity(UserLoginActivity.this,
                            UserForgetPwdActivity.class, null, false);
                    break;
                case R.id.btn_user_comfirm:
                    doLogin();
                    break;
                case R.id.btn_login_wechat:
                    loginWechat();
                    break;
                case R.id.btn_login_qq:
                    loginQQ();
                    break;
                case R.id.btn_login_sina:
                    loginSina();
                    break;
                default:
                    break;
            }

        }
    };

    private void doLogin() {
        String account = edt_user_account.getText().toString().trim();
        String password = edt_user_password.getText().toString().trim();
        if (Helper.isEmpty(account)) {
            ToastHelper.showToast("请输入手机号码");
            return;
        } else if (Helper.isEmpty(password)) {
            ToastHelper.showToast("请输入密码");
            return;
        }else if (!ProjectHelper.isMobiPhoneNum(account)){
            ToastHelper.showToast("手机号码错误");
            return;
        }else if (!ProjectHelper.isPwdValid(password)){
            ToastHelper.showToast("密码格式错误");
            return;
        }
        ProgressDialogHelper.showProgressDialog(UserLoginActivity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("phone", account);
        requestMap.put("password", password);
        requestMap.put("device", 0);
        requestMap.put("is_third", 0);
        post(ProjectConstants.Url.ACCOUNT_LOGIN, requestMap);
    }

    private void requestLogin3(String openId,String nickName,String avatar){
        ProgressDialogHelper.showProgressDialog(UserLoginActivity.this, "登录中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("is_third", 1);
        requestMap.put("appid", openId);
        requestMap.put("nickname", nickName);
        requestMap.put("icon", avatar);
        requestMap.put("device", 0);
        post(ProjectConstants.Url.ACCOUNT_LOGIN, requestMap, 1);
    }

    private void loginSina() {
        mAuthInfo = new AuthInfo(this, AppConstants.SINA_APP_KEY, AppConstants.SINA_REDIRECT_URL, AppConstants.SCOPE);
        mSsoHandler = new SsoHandler(UserLoginActivity.this, mAuthInfo);
        mSsoHandler.authorizeClientSso(new AuthListener());
    }

    private void loginQQ() {
        ProgressDialogHelper.showProgressDialog(UserLoginActivity.this, "加载中...");
        mTencent = Tencent.createInstance(AppConstants.APP_ID, this);
        mTencent.login(this, "all", new QQLoginListener());
    }

    private void loginWechat() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
//        req.state = "health";
        //发送认证请求
        api.sendReq(req);
        finish();
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(UserLoginActivity.this, mAccessToken);
                Toast.makeText(UserLoginActivity.this,
                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        UsersAPI usersAPI = new UsersAPI(UserLoginActivity.this, AppConstants.SINA_APP_KEY, mAccessToken);
                        usersAPI.show(Long.valueOf(mAccessToken.getUid()), new SinaRequestListener());
                    }
                }).start();
            } else {
                String code = values.getString("code");
                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(UserLoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(UserLoginActivity.this,
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(UserLoginActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public class SinaRequestListener implements RequestListener { //新浪微博请求接口
        @Override
        public void onComplete(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String idStr = jsonObject.getString("idstr");// 唯一标识符(uid)
                String name = jsonObject.getString("name");// 姓名
                String avatarHd = jsonObject.getString("avatar_hd");// 头像
                Log.e("userfo==========", "nickname:" + name + "headimg:" + avatarHd);
                requestLogin3(idStr,name,avatarHd);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Log.i("mylog", "Auth exception : " + e.getMessage());
            Toast.makeText(UserLoginActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    //qq登录
    class QQLoginListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            ProgressDialogHelper.dismissProgressDialog();
            if (response == null) {
                return;
            }
            Log.e("qqlogin=======", response + "");
            try {
                JSONObject jsonObject = (JSONObject) response;
                openId = jsonObject.getString("openid");
                String accessToken = jsonObject.getString("access_token");
                String expires = jsonObject.getString("expires_in");
                mTencent.setOpenId(openId);
                mTencent.setAccessToken(accessToken, expires);
                //获取用户信息
                UserInfo userInfo = new UserInfo(UserLoginActivity.this, mTencent.getQQToken());
                userInfo.getUserInfo(new UserInfoIUiListener());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCancel() {
            ProgressDialogHelper.dismissProgressDialog();
            ToastHelper.showToast("取消登录");
        }

        @Override
        public void onError(final UiError uiError) {
            ProgressDialogHelper.dismissProgressDialog();
            ToastHelper.showToast("登录失败: " + uiError.errorDetail);
        }
    }

    //qq登录
    class UserInfoIUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Log.e("userfo==========", response + "");
            if (response == null) {
                return;
            }
            try {
                JSONObject json = (JSONObject) response;
                String nickname = json.getString("nickname");
                String headimg = json.getString("figureurl");
                Log.e("userfo==========", "nickname:" + nickname+"headimg:"+headimg);
                requestLogin3(openId,nickname,headimg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCancel() {
            ToastHelper.showToast("获取用户信息失败");
        }

        @Override
        public void onError(UiError uiError) {
            ToastHelper.showToast("获取用户信息失败: " + uiError.errorDetail);
        }
    }


    // region 双击返回
    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private long mLastClickTimeMills = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTimeMills > DOUBLE_CLICK_INTERVAL) {
            ToastHelper.showToast("再按一次返回退出");
            mLastClickTimeMills = System.currentTimeMillis();
            return;
        }
        finish();
    }
    // endregion 双击返回

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //qq登录 官方文档没没没没没没没没没没没这句代码, 但是很很很很很很重要, 不然不会回调!
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, new QQLoginListener());
        }
        if(requestCode == Constants.REQUEST_API) {
            Tencent.handleResultData(data, new QQLoginListener());
        }
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
