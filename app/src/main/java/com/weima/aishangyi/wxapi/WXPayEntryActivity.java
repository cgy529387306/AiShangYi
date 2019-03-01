package com.weima.aishangyi.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.mb.android.utils.ToastHelper;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.weima.aishangyi.constants.AppConstants;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, AppConstants.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode == -1) {
				ToastHelper.showToast("支付失败");
				finish();
			}else if (resp.errCode == -2) {
               ToastHelper.showToast("支付取消");
				finish();
			}else if (resp.errCode == 0) {
				if ("1".equals(((PayResp)resp).extData)){
					LocalBroadcastManager.getInstance(WXPayEntryActivity.this).sendBroadcast(new Intent("rechargeSuccess"));
					finish();
				}if ("2".equals(((PayResp)resp).extData)){
					LocalBroadcastManager.getInstance(WXPayEntryActivity.this).sendBroadcast(new Intent("paySuccess"));
               		finish();
                }
			}
		}
	}
}