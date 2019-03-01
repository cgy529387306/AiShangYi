package com.weima.aishangyi.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.android.volley.VolleyError;
import com.mb.android.utils.DialogHelper;
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
import com.weima.aishangyi.entity.AnswerDetailResp;
import com.weima.aishangyi.entity.BalanceResp;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.entity.OrderPayResp;
import com.weima.aishangyi.entity.QuestionBean;
import com.weima.aishangyi.entity.QuestionDetailResp;
import com.weima.aishangyi.entity.WxPayResp;
import com.weima.aishangyi.utils.NavigationHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单支付
 */
public class OrderPayActivity extends BaseActivity {
    private LinearLayout lin_coupons;
    private View lineCoupons;
    private ImageView btn_weixin, btn_zhifubao,btn_balance;
    private TextView txv_all_cost,txv_rest_cost,txv_balance,txv_coupons;
    private int payType = 1;//1余额支付 2微信 3支付宝
    private IWXAPI api;
    //class order
    private int lesson_type;
    private int lesson_mode;
    private long lesson_id;
    private int number;
    private double price;
    private double amount;

    //activity order
    private long active_id;
    private String quote;
    private String contacts;
    private String phone;

    //question order
    private long item;
    private String problem;
    private int is_public;

    //anwser see order
    private long question_id;

    //order pay
    private long order_id;

    //buy member
    private long member_id;

    private double balance;
    private long coupon_id = 0;
    private double counpons = 0;

    private int type = 1;//1课程 2活动 3问答 4看答案  5待支付支付  6购买会员

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
        setCustomTitle("支付订单");
        setContentView(R.layout.activity_order_pay);
        initUI();
        initData();
        regToWxLogin();
        requestData();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter("paySuccess"));
    }

    private void initUI() {
        lin_coupons = findView(R.id.lin_coupons);
        lineCoupons = findView(R.id.lineCoupons);
        txv_all_cost = findView(R.id.txv_all_cost);
        txv_rest_cost = findView(R.id.txv_rest_cost);
        txv_balance = findView(R.id.txv_balance);
        txv_coupons = findView(R.id.txv_coupons);
        btn_weixin = findView(R.id.btn_weixin);
        btn_zhifubao = findView(R.id.btn_zhifubao);
        btn_balance = findView(R.id.btn_balance);
        btn_weixin.setOnClickListener(mClickListener);
        btn_zhifubao.setOnClickListener(mClickListener);
        btn_balance.setOnClickListener(mClickListener);
        findView(R.id.btn_balance).setOnClickListener(mClickListener);
        findView(R.id.btn_pay).setOnClickListener(mClickListener);
        findView(R.id.lin_coupons).setOnClickListener(mClickListener);
    }

    private void initData() {
        type = getIntent().getIntExtra("type", 1);
        number = getIntent().getIntExtra("number", 0);
        price = getIntent().getDoubleExtra("price", 0);
        amount = getIntent().getDoubleExtra("amount", 0);
        //1课程 2活动 3问答 4看答案  5待支付支付
        if (type == 1){
            lesson_type = getIntent().getIntExtra("lesson_type",0);
            lesson_mode = getIntent().getIntExtra("lesson_mode", 0);
            lesson_id = getIntent().getLongExtra("lesson_id", 0);
        }else if (type == 2){
            active_id = getIntent().getLongExtra("active_id", 0);
            quote = getIntent().getStringExtra("quote");
            contacts = getIntent().getStringExtra("contacts");
            phone = getIntent().getStringExtra("phone");
        }else if (type == 3){
            lin_coupons.setVisibility(View.GONE);
            lineCoupons.setVisibility(View.GONE);
            amount = price;
            item = getIntent().getLongExtra("item",0);
            problem = getIntent().getStringExtra("problem");
            is_public = getIntent().getIntExtra("is_public",0);
        }else if (type == 4){
            lin_coupons.setVisibility(View.GONE);
            lineCoupons.setVisibility(View.GONE);
            amount = price;
            question_id = getIntent().getLongExtra("question_id",0);
        }else if (type == 5){
            order_id = getIntent().getLongExtra("order_id",0);
            coupon_id = getIntent().getLongExtra("coupon_id",0);
            counpons = getIntent().getDoubleExtra("discount",0);
            if (counpons!=0){
                txv_coupons.setText(counpons+"元优惠券");
                txv_rest_cost.setText("¥" + (amount-counpons));
            }
        }else if (type == 6){
            member_id = getIntent().getLongExtra("member_id",0);
        }
        txv_all_cost.setText("¥" + amount);
        txv_rest_cost.setText("¥" + amount);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        int respType = Integer.parseInt(extras[0].toString());
        ProgressDialogHelper.dismissProgressDialog();
        if (respType == 1){
            OrderPayResp entity = JsonHelper.fromJson(response, OrderPayResp.class);
            if ("200".equals(entity.getCode())){
                if (Helper.isNotEmpty(entity.getData()))
                    if (payType == 1){
                        doPaySuccess();
                    }if (payType == 2) {
                        doWxPay(entity.getData().getOrder_no(),entity.getData().getReal_money());
                    } else if (payType == 3){
                        doAliPay(entity.getData().getOrder_no(),entity.getData().getReal_money());
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
                        req.extData = "2";//1:充值  2：订单支付
                        api.sendReq(req);
                    }
                }else {
                    ToastHelper.showToast(entity.getMessage());
                }
            }
        }else if (respType == 3){
            BalanceResp entity = JsonHelper.fromJson(response, BalanceResp.class);
            if ("200".equals(entity.getCode())){
                balance = Helper.isEmpty(entity.getData())?0:Double.parseDouble(entity.getData());
                txv_balance.setText("余额支付:"+balance+"元");
            }else{
                ToastHelper.showToast(entity.getMessage());
            }
        }else if (respType == 4){
            AnswerDetailResp entity = JsonHelper.fromJson(response, AnswerDetailResp.class);
            if ("200".equals(entity.getCode())){
                if(Helper.isNotEmpty(entity) && Helper.isNotEmpty(entity.getData())){
                    LocalBroadcastManager.getInstance(OrderPayActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_QUESTION_LIST));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("question", entity.getData());
                    NavigationHelper.startActivity(OrderPayActivity.this, AnswerDetailAcitity.class, bundle, true);
                }
            }else{
                ToastHelper.showToast(entity.getMessage());
            }
        }

        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
        ProgressDialogHelper.dismissProgressDialog();
        return true;
    }

    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_weixin://微信支付
                    payType = 2;
                    btn_balance.setImageResource(R.drawable.ic_checkbox_uncheck);
                    btn_weixin.setImageResource(R.drawable.ic_checkbox_checked);
                    btn_zhifubao.setImageResource(R.drawable.ic_checkbox_uncheck);

                    break;
                case R.id.btn_zhifubao://支付宝支付
                    payType = 3;
                    btn_balance.setImageResource(R.drawable.ic_checkbox_uncheck);
                    btn_zhifubao.setImageResource(R.drawable.ic_checkbox_checked);
                    btn_weixin.setImageResource(R.drawable.ic_checkbox_uncheck);
                    break;
                case R.id.btn_balance://余额支付
                    payType = 1;
                    btn_balance.setImageResource(R.drawable.ic_checkbox_checked);
                    btn_weixin.setImageResource(R.drawable.ic_checkbox_uncheck);
                    btn_zhifubao.setImageResource(R.drawable.ic_checkbox_uncheck);
                    break;
                case R.id.btn_pay://立即支付
                    if (type == 1){
                        doPayLesson();
                    }else if (type == 2){
                        doPayActivity();
                    }else if (type == 3){
                        doPayQuestion();
                    }else if (type == 4){
                        doPaySeeAnwser();
                    }else if (type == 5){
                        doPayOrder();
                    }else if (type == 6){
                        doPayMember();
                    }
                    break;
                case R.id.lin_coupons://优惠券
                    Bundle bundle = new Bundle();
                    bundle.putInt("is_clickable",1);
                    NavigationHelper.startActivityForResult(OrderPayActivity.this, UserCouponsActivity.class, bundle, 1);
                    break;
                default:
                    break;
            }
        }
    };

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
                PayTask alipay = new PayTask(OrderPayActivity.this);
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
                        Toast.makeText(OrderPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void doPayLesson() {
        if (payType == 1 && (amount-counpons)>balance){
            ToastHelper.showToast("账户余额不足，请选择其他支付方式");
            return;
        }
        ProgressDialogHelper.showProgressDialog(OrderPayActivity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("lesson_type", lesson_type);
        requestMap.put("lesson_mode", lesson_mode);
        requestMap.put("lesson_id", lesson_id);
        requestMap.put("number", number);
        requestMap.put("price", price);
        requestMap.put("amount",amount-counpons);
        requestMap.put("type", payType==1?1:0);
        if (counpons!=0 && coupon_id!=0){
            requestMap.put("discount", counpons);
            requestMap.put("coupon_id", coupon_id);
        }
        post(ProjectConstants.Url.ORDER_LESSON, requestMap,1);
    }

    private void doPayActivity() {
        if (payType == 1 && (amount-counpons)>balance){
            ToastHelper.showToast("账户余额不足，请选择其他支付方式");
            return;
        }
        ProgressDialogHelper.showProgressDialog(OrderPayActivity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("active_id", active_id);
        requestMap.put("number", number);
        requestMap.put("price", price);
        requestMap.put("amount",amount-counpons);
        requestMap.put("type", payType==1?1:0);
        requestMap.put("quote", quote);
        requestMap.put("contacts", contacts);
        requestMap.put("phone", phone);
        if (counpons!=0 && coupon_id!=0){
            requestMap.put("discount", counpons);
            requestMap.put("coupon_id", coupon_id);
        }
        post(ProjectConstants.Url.ORDER_ACTIVITY, requestMap,1);
    }

    private void doPayQuestion() {
        if (payType == 1 && (amount-counpons)>balance){
            ToastHelper.showToast("账户余额不足，请选择其他支付方式");
            return;
        }
        ProgressDialogHelper.showProgressDialog(OrderPayActivity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("item", item);
        requestMap.put("price", price);
        requestMap.put("problem",problem);
        requestMap.put("is_public", is_public);
        requestMap.put("type", payType==1?1:0);
        post(ProjectConstants.Url.ORDER_QUESTION, requestMap,1);
    }

    private void doPaySeeAnwser() {
        if (payType == 1 && (amount-counpons)>balance){
            ToastHelper.showToast("账户余额不足，请选择其他支付方式");
            return;
        }
        ProgressDialogHelper.showProgressDialog(OrderPayActivity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("question_id", question_id);
        requestMap.put("price", price);
        requestMap.put("type", payType==1?1:0);
        post(ProjectConstants.Url.ORDER_SEE, requestMap,1);
    }


    private void doPayOrder() {
        if (payType == 1 && (amount-counpons)>balance){
            ToastHelper.showToast("账户余额不足，请选择其他支付方式");
            return;
        }
        ProgressDialogHelper.showProgressDialog(OrderPayActivity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("order_id", order_id);
        requestMap.put("amount",amount-counpons);
        requestMap.put("price", price);
        requestMap.put("type", payType==1?1:0);
        if (counpons!=0 && coupon_id!=0){
            requestMap.put("discount", counpons);
            requestMap.put("coupon_id", coupon_id);
        }
        post(ProjectConstants.Url.ORDER_GOPAY, requestMap,1);
    }

    private void doPayMember() {
        if (payType == 1 && (amount-counpons)>balance){
            ToastHelper.showToast("账户余额不足，请选择其他支付方式");
            return;
        }
        ProgressDialogHelper.showProgressDialog(OrderPayActivity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("type_id", member_id);
        requestMap.put("amount",amount-counpons);
        requestMap.put("type", payType==1?1:0);
        if (counpons!=0 && coupon_id!=0){
            requestMap.put("discount", counpons);
            requestMap.put("coupon_id", coupon_id);
        }
        post(ProjectConstants.Url.ORDER_MEMBER, requestMap,1);
    }


    private void doPaySuccess(){
        Toast.makeText(OrderPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
        if (type == 1){
            Bundle bundle = new Bundle();
            bundle.putInt("status",0);
            NavigationHelper.startActivity(OrderPayActivity.this, UserClassroomOrderActivity.class, bundle, true);
        }else if (type == 2){
            Bundle bundle = new Bundle();
            bundle.putInt("status",0);
            NavigationHelper.startActivity(OrderPayActivity.this, UserActivityOrderActivity.class, bundle, true);
        }else if (type == 3){
            NavigationHelper.startActivity(OrderPayActivity.this, UserQuestionActivity.class, null, true);
        }else if (type == 4){
            answerDetail();
        }else if (type == 6){
            LocalBroadcastManager.getInstance(OrderPayActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_MEMBER));
            finish();
        }else{
            Bundle bundle = new Bundle();
            bundle.putInt("status",0);
            NavigationHelper.startActivity(OrderPayActivity.this, UserClassroomOrderActivity.class, bundle, true);
        }
    }



    private void requestData(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        post(ProjectConstants.Url.CASH_BALANCE, requestMap,3);
    }

    private void answerDetail(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("question_id", question_id);
        post(ProjectConstants.Url.QUESTION_DETAIL, requestMap,4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1:
                // 优惠券
                counpons = data.getDoubleExtra(ProjectConstants.BundleExtra.KEY_COUPONS,0);
                coupon_id = data.getLongExtra(ProjectConstants.BundleExtra.KEY_COUPONS_ID,0);
                if (counpons!=0){
                    txv_coupons.setText(counpons+"元优惠券");
                    txv_rest_cost.setText("¥" + (amount-counpons));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DialogHelper.showConfirmDialog(OrderPayActivity.this, "提示", "订单未支付，确定退出？", true,
                R.string.dialog_positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                }, R.string.dialog_negative, null);
    }
}
