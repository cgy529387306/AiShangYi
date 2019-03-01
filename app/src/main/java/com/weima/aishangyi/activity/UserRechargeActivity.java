package com.weima.aishangyi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.weima.aishangyi.R;
import com.weima.aishangyi.alipay.AuthResult;
import com.weima.aishangyi.alipay.OrderInfoUtil2_0;
import com.weima.aishangyi.alipay.PayResult;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.AppConstants;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.entity.OrderPayResp;
import com.weima.aishangyi.entity.RechargePayResp;
import com.weima.aishangyi.entity.WxPayResp;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 账户充值
 */
public class UserRechargeActivity extends BaseActivity {
    private TextView txv_telephone;
    private EditText edit_rechargecash;
    private ImageView btn_weixin, btn_zhifubao;
    private String[] text = {"100元", "200元", "500元", "1000元"};
    private ListView recharge_list;
    private double money = 100;
    private int payType = 0;//0微信 1支付宝
    private IWXAPI api;
    private LocalBroadcastManager mLocalBroadcastManager;
    /**
     * 更新用户信息广播接受者
     */
    private BroadcastReceiver mUpdateUserInfoReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            doPaySuccess();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mUpdateUserInfoReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("充值");
        setContentView(R.layout.activity_user_recharge);
        initUI();
        regToWxLogin();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter("rechargeSuccess"));
    }


    private void initUI() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        txv_telephone = findView(R.id.txv_telephone);//充值账户
        edit_rechargecash = findView(R.id.edit_rechargecash);//自定义充值金额
        recharge_list = findView(R.id.recharge_list);
        btn_weixin = findView(R.id.btn_weixin);
        btn_weixin.setOnClickListener(mClickListener);
        btn_zhifubao = findView(R.id.btn_zhifubao);
        btn_zhifubao.setOnClickListener(mClickListener);
        findView(R.id.btn_recharge).setOnClickListener(mClickListener);
        MyAdapter myAdapter = new MyAdapter();
        recharge_list.setAdapter(myAdapter);
        txv_telephone.setText(ProjectHelper.getCommonText(CurrentUser.getInstance().getNickname()));
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        int respType = Integer.parseInt(extras[0].toString());
        ProgressDialogHelper.dismissProgressDialog();
        if (respType == 1){
            RechargePayResp entity = JsonHelper.fromJson(response, RechargePayResp.class);
            if ("200".equals(entity.getCode())){
                if (Helper.isNotEmpty(entity.getData()))
                    if (payType == 0) {
                        doWxPay(entity.getData().getRecharge_no(),entity.getData().getMoney());
                    } else {
                        doAliPay(entity.getData().getRecharge_no(),entity.getData().getMoney());
                    }
            }else {
                ToastHelper.showToast(entity.getMessage());
            }
        }else if (respType == 2){
            WxPayResp entity = JsonHelper.fromJson(response, WxPayResp.class);
            if (Helper.isNotEmpty(entity)){
                if ("200".equals(entity.getCode())){
                    WxPayResp.WechatPay wechatPay = entity.getData();
                    if (Helper.isNotEmpty(wechatPay)){
                        PayReq req = new PayReq();
                        req.appId = wechatPay.getAppid();
                        req.partnerId = wechatPay.getPartnerid();
                        req.prepayId = wechatPay.getPrepayid();
                        req.nonceStr = wechatPay.getNoncestr();
                        req.timeStamp = wechatPay.getTimestamp();
                        req.packageValue = "Sign=WXPay";
                        req.sign = wechatPay.getSign();
                        req.extData = "1";//1:充值  2：订单支付
                        api.sendReq(req);
                    }
                }else {
                    ToastHelper.showToast(entity.getMessage());
                }
            }
        }

        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
        ProgressDialogHelper.dismissProgressDialog();
        return true;
    }

    private void regToWxLogin() {
        //通过WXAPIFactory工厂,获取IWXAPI的实例
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, AppConstants.WX_APP_ID, true);
            //将应用的appId注册到微信
            api.registerApp(AppConstants.WX_APP_ID);
        }
        //判断手机是否安装微信
        if (!api.isWXAppInstalled()) {
            ToastHelper.showToast("请先安装微信应用");
            return;
        }
    }

    private void doWxPay(String orderNo,double money){
        try{
            ProgressDialogHelper.showProgressDialog(this, "获取订单中...");
            HashMap<String, Object> requestMap = new HashMap<String, Object>();
            requestMap.put("body", "订单价格");
            requestMap.put("nonce_str", Helper.createRandomString(6));
            requestMap.put("spbill_create_ip", "127.0.0.1");
            requestMap.put("money", money);
            requestMap.put("order_no", orderNo);
            post(ProjectConstants.Url.WECHAT_PREPAY, requestMap, 2);
        }catch(Exception e){
            Log.e("PAY_GET", "异常：" + e.getMessage());
            ToastHelper.showToast("异常：" + e.getMessage());
        }
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void doAliPay(String orderNo,double money) {
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap("爱尚艺","爱尚艺支付",orderNo,String.valueOf(money));
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = AppConstants.RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, false);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(UserRechargeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        doPaySuccess();
                    } else {
                        Toast.makeText(UserRechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void doPay() {
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        if (Helper.isNotEmpty(edit_rechargecash.getText().toString())){
            requestMap.put("money", Double.parseDouble(edit_rechargecash.getText().toString()));
        }else{
            requestMap.put("money", money);
        }
        post(ProjectConstants.Url.CASH_RECHARGE, requestMap, 1);
    }

    private void doPaySuccess(){
        LocalBroadcastManager.getInstance(UserRechargeActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_BALANCE));
        Toast.makeText(UserRechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_weixin://微信支付
                    payType = 0;
                    btn_weixin.setImageResource(R.drawable.ic_checkbox_checked);
                    btn_zhifubao.setImageResource(R.drawable.ic_checkbox_uncheck);
                    break;
                case R.id.btn_zhifubao://支付宝支付
                    payType = 1;
                    btn_zhifubao.setImageResource(R.drawable.ic_checkbox_checked);
                    btn_weixin.setImageResource(R.drawable.ic_checkbox_uncheck);
                    break;
                case R.id.btn_recharge://立即充值
                    doPay();
                    break;
                default:
                    break;
            }
        }
    };

    private class MyAdapter extends BaseAdapter {
        private int tempIndex = 0;
        @Override
        public int getCount() {
            return text.length;
        }

        @Override
        public Object getItem(int position) {
            return text[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private void setTempIndex(int index){
            this.tempIndex = index;
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(UserRechargeActivity.this).inflate(R.layout.item_rechargecash, null);
                viewHolder.txv_cash = (TextView) convertView.findViewById(R.id.txv_cash);
                viewHolder.imv_checkbox = (ImageView) convertView.findViewById(R.id.imv_checkbox);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.txv_cash.setText(text[position]);
            if (position == tempIndex){
                viewHolder.imv_checkbox.setImageResource(R.drawable.ic_checkbox_checked);
            }else{
                viewHolder.imv_checkbox.setImageResource(R.drawable.ic_checkbox_uncheck);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position==0){
                        money = 100;
                    }else if (position==1){
                        money = 200;
                    }else if (position==2){
                        money = 500;
                    }else if (position==3){
                        money = 1000;
                    }
                    setTempIndex(position);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView txv_cash;
            ImageView imv_checkbox;
        }
    }


}
