package com.weima.aishangyi.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.chat.MyChatActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.entity.User;
import com.weima.aishangyi.entity.UserCenterInfo;
import com.weima.aishangyi.entity.UserNumResp;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;

/**
 * 关注的个人主页
 *
 * @author cgy
 */
public class PersonInfoActivity extends BaseActivity {
    private TextView txv_nickname, txv_sex, txv_follows, txv_fans,txv_city,txv_interest,txv_sign,txv_follow;
    private ImageView imv_user_avater;
    private LinearLayout linBottom;
    private int tag = 0;
    private User user;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concern_info);
        getSupportActionBar().hide();
        initUI();
        if (type == 1){
            requestData();
        }else{
            initData();
        }
    }



    private void initUI() {
        type = getIntent().getIntExtra("type",0);
        user = (User) getIntent().getSerializableExtra(ProjectConstants.BundleExtra.KEY_USER);
        linBottom = findView(R.id.linBottom);
        imv_user_avater = findView(R.id.imv_user_avater);
        txv_nickname = findView(R.id.txv_nickname);
        txv_sex = findView(R.id.txv_sex);
        txv_follows = findView(R.id.txv_follows);
        txv_fans = findView(R.id.txv_fans);
        txv_city = findView(R.id.txv_city);
        txv_interest = findView(R.id.txv_interest);
        txv_sign = findView(R.id.txv_sign);
        txv_follow = findView(R.id.txv_follow);
        linBottom.setVisibility(user.getId() == CurrentUser.getInstance().getId()?View.GONE:View.VISIBLE);
        findView(R.id.imv_back).setOnClickListener(mClickListener);
        findView(R.id.btn_follow).setOnClickListener(mClickListener);
        findView(R.id.btn_chat).setOnClickListener(mClickListener);
        findView(R.id.btn_talent_circle).setOnClickListener(mClickListener);
    }

    private void initData() {
        if (Helper.isNotEmpty(user)){
            if (Helper.isNotEmpty(user.getIcon())){
                Picasso.with(PersonInfoActivity.this).load(user.getIcon()).placeholder(R.drawable.ic_avatar_default).into(imv_user_avater);
            }
            txv_nickname.setText(ProjectHelper.getCommonText(user.getNickname()));
            txv_follows.setText(user.getFollow_count()+"");
            txv_fans.setText(user.getFans_count()+"");
            txv_city.setText(ProjectHelper.getCommonText(user.getCity()));
            txv_interest.setText(ProjectHelper.getCommonText(user.getInterest()));

            txv_sign.setText(user.getSignature());
            if (user.getSex()==1){
                Drawable drawable= getResources().getDrawable(R.drawable.ic_man);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                txv_sex.setCompoundDrawables(drawable, null, null, null);
                txv_sex.setText(ProjectHelper.getAge(user.getBirth())+"");
            }else{
                Drawable drawable= getResources().getDrawable(R.drawable.ic_woman);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                txv_sex.setCompoundDrawables(drawable, null, null, null);
                txv_sex.setText(ProjectHelper.getAge(user.getBirth())+"");
            }
            if (user.getIs_follow()==0){
                Drawable drawable= getResources().getDrawable(R.drawable.ic_follow_normal);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                txv_follow.setCompoundDrawables(drawable, null, null, null);
                txv_follow.setText("关注");
            }else{
                Drawable drawable= getResources().getDrawable(R.drawable.ic_follow_press);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                txv_follow.setCompoundDrawables(drawable, null, null, null);
                txv_follow.setText("已关注");
            }
        }
    }

    @Override
    public boolean onResponseSuccess(int gact, String response, Object... extras) {
        int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
        if (requestType == 3){
            UserNumResp entity = JsonHelper.fromJson(response, UserNumResp.class);
            if ("200".equals(entity.getCode())) {
                user = convertToUser(entity.getData());
                initData();
            }else {
                ToastHelper.showToast(entity.getMessage());
            }
            return true;
        }
        ProgressDialogHelper.dismissProgressDialog();
        CommonEntity upEntity = JsonHelper.fromJson(response, CommonEntity.class);
        if ("200".equals(upEntity.getCode())){
            if (requestType == 1){
                ToastHelper.showToast("关注成功");
                user.setIs_follow(1);
            }else{
                ToastHelper.showToast("取消关注成功");
                user.setIs_follow(0);
            }
            LocalBroadcastManager.getInstance(PersonInfoActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_TALENT_LIST));
            LocalBroadcastManager.getInstance(PersonInfoActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_USER_INFO));
            initData();
        }else{
            ToastHelper.showToast(upEntity.getMessage());
        }
        return true;
    }

    /**
     * 点击事件
     */
    private OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imv_back:
                    finish();
                    break;
                case R.id.btn_follow:
                    if (user.getIs_follow()==0){
                        requestFollow();
                    }else{
                        cancelFollow();
                    }
                    break;
                case R.id.btn_chat:
                    if (Helper.isNotEmpty(user)){
                        Bundle bundle=new Bundle();
                        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                        bundle.putString(EaseConstant.EXTRA_USER_ID, user.getDevice()==0?"stu_"+user.getId():"teach_"+user.getId());
                        bundle.putString(EaseConstant.EXTRA_USER_NAME, user.getNickname());
                        NavigationHelper.startActivity(PersonInfoActivity.this, MyChatActivity.class, bundle, false);
                    }
                    break;
                case R.id.btn_talent_circle:
                    if (Helper.isNotEmpty(user)){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ProjectConstants.BundleExtra.KEY_USER, user);
                        NavigationHelper.startActivity(PersonInfoActivity.this, MyTelantCircleActivity.class, bundle, false);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void requestData(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id",user.getId());
        requestMap.put("type",user.getDevice());
        post(ProjectConstants.Url.ACCOUNT_GET_NUM, requestMap,3);
    }

    private void requestFollow(){
        if (Helper.isNotEmpty(user)){
            ProgressDialogHelper.showProgressDialog(PersonInfoActivity.this, "加载中...");
            HashMap<String, Object> requestMap = new HashMap<String, Object>();
            requestMap.put("id",user.getId());
            requestMap.put("type",user.getDevice());
            post(ProjectConstants.Url.FOLLOW, requestMap,1);
        }
    }

    private void cancelFollow(){
        if (Helper.isNotEmpty(user)){
            ProgressDialogHelper.showProgressDialog(PersonInfoActivity.this, "加载中...");
            HashMap<String, Object> requestMap = new HashMap<String, Object>();
            requestMap.put("id",user.getId());
            requestMap.put("type",user.getDevice());
            post(ProjectConstants.Url.FOLLOW_CANCEL, requestMap,2);
        }
    }


    private User convertToUser(UserCenterInfo userCenterInfo){
        User user = new User();
        if (Helper.isNotEmpty(userCenterInfo)){
            user.setId(userCenterInfo.getId());
            user.setIcon(userCenterInfo.getIcon());
            user.setSignature(userCenterInfo.getSignature());
            user.setInterest(userCenterInfo.getInterest());
            user.setNickname(userCenterInfo.getNickname());
            user.setFans_count(userCenterInfo.getFans_count());
            user.setFollow_count(userCenterInfo.getFollow_count());
            user.setIs_follow(userCenterInfo.getIs_follow());
            user.setCity(userCenterInfo.getCity());
            user.setDevice(userCenterInfo.getDevice());
            user.setSex(userCenterInfo.getSex());
            user.setBirth(userCenterInfo.getBirth());
        }
        return user;
    }

}
