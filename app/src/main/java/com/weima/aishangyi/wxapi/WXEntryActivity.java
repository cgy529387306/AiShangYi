package com.weima.aishangyi.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.weima.aishangyi.activity.HomeActivity;
import com.weima.aishangyi.activity.UserphoneBActivity;
import com.weima.aishangyi.constants.AppConstants;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.LoginResp;
import com.weima.aishangyi.entity.WxLoginEntity;
import com.weima.aishangyi.entity.WxUserInfoEntity;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI api;
	private  static String codes;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, AppConstants.WX_APP_ID, false);
		api.handleIntent(getIntent(), this);
	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
		finish();
	}

	@Override
	public void onReq(BaseReq arg0) {
		finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				SendAuth.Resp sendResp = (SendAuth.Resp) resp;
				codes = sendResp.code;
				getAccess_token(codes);
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				ToastHelper.showToast("取消登录");
				finish();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				ToastHelper.showToast("登录失败");
				finish();
				break;
			default:
				break;
		}
	}


	/**
	 * 获取openid accessToken值用于后期操作
	 *
	 * @param code 请求码
	 */
	private void getAccess_token(final String code) {
		String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ AppConstants.WX_APP_ID
				+ "&secret="
				+ AppConstants.WX_APP_SECRET
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		try {
			RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(path)
					.setRequestParamsMap(new HashMap<String, Object>()).setRequestHeader(ProjectHelper.getUserAgent1()).getRequestEntity();
			RequestHelper.post(entity, new ResponseListener() {
				@Override
				public boolean onResponseSuccess(int gact, String response, Object... extras) {
					if (Helper.isNotEmpty(response)) {
						WxLoginEntity wechatEntity = JsonHelper.fromJson(response, WxLoginEntity.class);
						if (Helper.isNotEmpty(wechatEntity)) {
							getUserMesg(wechatEntity.getAccess_token(), wechatEntity.getOpenid());
						}
					}
					return false;
				}

				@Override
				public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
					ToastHelper.showToast("网络错误");
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取微信的个人信息
	 *
	 * @param access_token
	 * @param openid
	 */
	private void getUserMesg(final String access_token, final String openid) {
		String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ access_token
				+ "&openid="
				+ openid;
		try {
			RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(path)
					.setRequestParamsMap(new HashMap<String, Object>()).setRequestHeader(ProjectHelper.getUserAgent1()).getRequestEntity();
        	RequestHelper.get(entity, new ResponseListener() {
				@Override
				public boolean onResponseSuccess(int gact, String response, Object... extras) {
					if(Helper.isNotEmpty(response)){
						WxUserInfoEntity userInfoEntity = JsonHelper.fromJson(response, WxUserInfoEntity.class);
						if (Helper.isNotEmpty(userInfoEntity)){
							requestLogin3(userInfoEntity.getOpenid(),userInfoEntity.getNickname(),userInfoEntity.getHeadimgurl());
						}
					}
					return false;
				}

				@Override
				public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
					ToastHelper.showToast("网络错误");
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	private void requestLogin3(String openId,String nickName,String avatar){
		ProgressDialogHelper.showProgressDialog(WXEntryActivity.this, "登录中...");
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("is_third", 1);
		requestMap.put("appid", openId);
		requestMap.put("nickname", nickName);
		requestMap.put("icon", avatar);
		requestMap.put("device", 0);
		RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(ProjectConstants.Url.ACCOUNT_LOGIN)
				.setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestMap).getRequestEntity();
		RequestHelper.post(entity, new ResponseListener() {
			@Override
			public boolean onResponseSuccess(int gact, String response, Object... extras) {
				ProgressDialogHelper.dismissProgressDialog();
				int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
				if (requestType == 1){
					LoginResp entity = JsonHelper.fromJson(response, LoginResp.class);
					if ("200".equals(entity.getCode())){
						ToastHelper.showToast("登录成功");
						PreferencesHelper.getInstance().putString(ProjectConstants.Preferences.KEY_CURRENT_TOKEN, entity.getToken());
						if (Helper.isNotEmpty(entity.getData()) && Helper.isNotEmpty(entity.getData().getBind_phone())){
							NavigationHelper.startActivity(WXEntryActivity.this, HomeActivity.class, null, true);
						}else{
							NavigationHelper.startActivity(WXEntryActivity.this, UserphoneBActivity.class, null, true);
						}
					}else{
						ToastHelper.showToast(entity.getMessage());
					}
					return true;
				}
				return false;
			}

			@Override
			public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
				ProgressDialogHelper.dismissProgressDialog();
				ToastHelper.showToast("登录失败");
				finish();
				return false;
			}
		}, 1);
	}
}