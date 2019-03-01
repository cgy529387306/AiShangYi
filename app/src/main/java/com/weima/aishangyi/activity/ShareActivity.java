package com.weima.aishangyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mb.android.utils.Helper;
import com.mb.android.utils.ToastHelper;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weima.aishangyi.R;
import com.weima.aishangyi.constants.AppConstants;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.utils.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * 分享
 * Created by cgy
 */
public class ShareActivity extends Activity implements View.OnClickListener,IWXAPIEventHandler,IWeiboHandler.Response {
    private IWXAPI api;
    private Tencent mTencent;
    /** 微博微博分享接口实例 */
    private IWeiboShareAPI mWeiboShareAPI = null;
    private String imageUrl;
    private String title;
    private String url = "http://aishangyi.h5h5h5.cn/stu_download";
    private String localImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_share);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        localImage = getLocalImage();
        initThird(savedInstanceState);
        initComponent();
    }



    private void initComponent() {
        imageUrl = getIntent().getStringExtra("imageUrl");
        title = getIntent().getStringExtra("title");
        if (Helper.isEmpty(title)){
            title = "爱尚艺学生端";
        }
        findViewById(R.id.outView).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_sina).setOnClickListener(this);
        findViewById(R.id.btn_moments).setOnClickListener(this);
        findViewById(R.id.btn_wechat).setOnClickListener(this);
        findViewById(R.id.btn_qzone).setOnClickListener(this);
        findViewById(R.id.btn_qq).setOnClickListener(this);
    }

    private void initThird(Bundle savedInstanceState) {
        //通过WXAPIFactory工厂,获取IWXAPI的实例
        if (api == null) {
            api = WXAPIFactory.createWXAPI(ShareActivity.this, AppConstants.WX_APP_ID, false);
            //将应用的appId注册到微信
            api.registerApp(AppConstants.WX_APP_ID);
        }
        if (mTencent == null){
            mTencent = Tencent.createInstance(AppConstants.APP_ID, ShareActivity.this);
        }
        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, AppConstants.SINA_APP_KEY);
        mWeiboShareAPI.registerApp();
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(),ShareActivity.this);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_sina){
            doShareSina();
        }else if (id == R.id.btn_moments){
            doShareToWX(1);
        }else if (id == R.id.btn_wechat){
            doShareToWX(0);
        }else if (id == R.id.btn_qzone){
            doShareToQzone();
        }else if (id == R.id.btn_qq){
            doShareToQQ();
        }
        finish();
    }

    private String getLocalImage(){
        BitmapDrawable d = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);
        Bitmap img = d.getBitmap();
        String name = "ic_launcher.png";
        String path = this.getFilesDir() + File.separator + name;
        try{
            OutputStream os = new FileOutputStream(path);
            img.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
        }catch(Exception e){
            Log.e("TAG", "", e);
        }
        return path;
    }

    //begin wechat share
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }


    /**
     * 微信分享
     *
     * @param flag 其中flag 0是好友，1是朋友圈，
     */
    private void doShareToWX(int flag) {
        if (!api.isWXAppInstalled()) {
            ToastHelper.showToast("");
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        Bitmap thumb = null;
        if (Helper.isNotEmpty(imageUrl)){
            thumb = Bitmap.createScaledBitmap(ProjectHelper.getLocalOrNetBitmap(imageUrl), 80, 80, true);//压缩Bitmap
        }else{
            thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        }
        msg.thumbData = Util.bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = flag;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    //end wechat share


    //begin qq share
    private void doShareToQQ() {
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    Bundle bundle = new Bundle();
                    bundle.putString(QQShare.SHARE_TO_QQ_TITLE, title);
                    bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
                    if (Helper.isNotEmpty(imageUrl)){
                        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
                    }else{

                        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,localImage);
                    }
                    bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                    mTencent.shareToQQ(ShareActivity.this, bundle, QQShareListener);
                }
            }
        });
    }

    /**
     * 用异步方式启动分享
     */
    private void doShareToQzone() {
        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    final Bundle params = new Bundle();
                    params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                    params.putString(QQShare.SHARE_TO_QQ_TITLE,title);
                    params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
                    if (Helper.isNotEmpty(imageUrl)){
                        ArrayList imageUrls = new ArrayList();
                        imageUrls.add(imageUrl);
                        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
                    }else {
                        ArrayList imageUrls = new ArrayList();
                        imageUrls.add(localImage);
                        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageUrls);
                    }
                    mTencent.shareToQzone(ShareActivity.this, params, QQShareListener);
                }
            }
        });
    }

    IUiListener QQShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            ToastHelper.showToast("取消分享");
        }

        @Override
        public void onError(UiError e) {
            ToastHelper.showToast("分享失败" + e.errorMessage);
        }

        @Override
        public void onComplete(Object response) {
            ToastHelper.showToast("分享成功");
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
    }
    //end qq share


    //begin sina share
    private void doShareSina() {
        if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
            WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
            WebpageObject mediaObject = new WebpageObject();
            mediaObject.identify = Utility.generateGUID();
            mediaObject.title = title;
            mediaObject.description = title;
            Bitmap  bitmap = null;
            if (Helper.isNotEmpty(imageUrl)){
                bitmap = Bitmap.createScaledBitmap(ProjectHelper.getLocalOrNetBitmap(imageUrl), 80, 80, true);//压缩Bitmap
            }else{
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            }
            mediaObject.setThumbImage(bitmap);
            mediaObject.actionUrl = url;
            mediaObject.defaultText = "Webpage 默认文案";
            weiboMessage.mediaObject = mediaObject;
            SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
            request.transaction = String.valueOf(System.currentTimeMillis());
            request.multiMessage = weiboMessage;
            mWeiboShareAPI.sendRequest(ShareActivity.this, request);
        } else {
            Toast.makeText(this, "微博客户端未安装或微博客户端是非官方版本。", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        if(baseResp!= null){
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    Toast.makeText(this,
                            getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: " + baseResp.errMsg,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


}
