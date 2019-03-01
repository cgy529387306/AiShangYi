package com.weima.aishangyi.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.BuyMemberActivity;
import com.weima.aishangyi.activity.ClassDetailAcitity;
import com.weima.aishangyi.activity.UserAccountActivity;
import com.weima.aishangyi.activity.UserActivityOrderActivity;
import com.weima.aishangyi.activity.UserAnswerActivity;
import com.weima.aishangyi.activity.UserClassroomOrderActivity;
import com.weima.aishangyi.activity.UserCollectActivity;
import com.weima.aishangyi.activity.UserContactActivity;
import com.weima.aishangyi.activity.UserCouponsActivity;
import com.weima.aishangyi.activity.UserInfoActivity;
import com.weima.aishangyi.activity.UserInviteActivity;
import com.weima.aishangyi.activity.UserLoginActivity;
import com.weima.aishangyi.activity.UserSettingActivity;
import com.weima.aishangyi.activity.UserTimetableActivity;
import com.weima.aishangyi.chat.MyConversationListActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.entity.UserCenterInfo;
import com.weima.aishangyi.entity.UserInfoResp;
import com.weima.aishangyi.entity.UserNumResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.widget.BadgeView;

import java.util.HashMap;


/**
 * 用户中心
 * Created by cgy on 16/7/18.
 */
public class UserFragment extends Fragment implements ResponseListener {
    private TextView txv_nickname, txv_telephone;
    private ImageView imv_user_avater;
    private ImageView imv_msg_new,imv_fans;
    private BadgeView badgeView;
    private TextView txv_user_follows,txv_user_fans,txv_user_teachers;
    private LocalBroadcastManager mLocalBroadcastManager;
    /**
     * 更新用户信息广播接受者
     */
    private BroadcastReceiver mUpdateUserInfoReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            requestData();
        }
    };
    private BroadcastReceiver mUpdateNewFansReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            requestData();
           setNewFansCount();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_USER_INFO));
        mLocalBroadcastManager.registerReceiver(mUpdateNewFansReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_FANS_NEW));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mUpdateUserInfoReceiver);
        mLocalBroadcastManager.unregisterReceiver(mUpdateNewFansReceiver);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_user, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initChatMsg();
        requestData();
    }

    private void initUI(View view) {
        txv_nickname = (TextView) view.findViewById(R.id.txv_nickname);
        txv_telephone = (TextView) view.findViewById(R.id.txv_telephone);
        imv_user_avater = (ImageView) view.findViewById(R.id.imv_user_avater);
        imv_msg_new = (ImageView) view.findViewById(R.id.imv_msg_new);
        imv_fans = (ImageView) view.findViewById(R.id.imv_fans);
        txv_user_follows = (TextView) view.findViewById(R.id.txv_user_follows);
        txv_user_fans = (TextView) view.findViewById(R.id.txv_user_fans);
        txv_user_teachers = (TextView) view.findViewById(R.id.txv_user_teachers);
        badgeView = new BadgeView(getActivity());
        badgeView.setBadgeMargin(30,0, 0, 0);
        badgeView.setTextSize(10);
        badgeView.setTargetView(imv_fans);
        view.findViewById(R.id.btn_concern).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_fans).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_myteacher).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_classroomorder).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_activityorder).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_classing).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_classed).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_account).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_buy_member).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_coupons).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_answer).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_collect).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_invite).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_setting).setOnClickListener(mClickListener);
        view.findViewById(R.id.imv_user_avater).setOnClickListener(mClickListener);
        view.findViewById(R.id.imv_user_msg).setOnClickListener(mClickListener);
    }

    private void setNewFansCount(){
        if (badgeView!=null){
            int count = PreferencesHelper.getInstance().getInt(ProjectConstants.Preferences.KEY_FANS_COUNT,0);
            if (count>0){
                badgeView.setVisibility(View.VISIBLE);
                badgeView.setText(ProjectHelper.formatBadgeNumber(count));
            }else{
                badgeView.setVisibility(View.GONE);
                badgeView.setText(ProjectHelper.formatBadgeNumber(count));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initChatMsg();
        requestData();
    }

    private void initChatMsg(){
        if (CurrentUser.getInstance().born() && imv_msg_new != null
                && EMClient.getInstance().chatManager()!=null){
            int count =  EMClient.getInstance().chatManager().getUnreadMsgsCount();
            imv_msg_new.setVisibility(count>0?View.VISIBLE:View.GONE);
        }
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
        if (requestType == 1){
            UserInfoResp entity = JsonHelper.fromJson(response, UserInfoResp.class);
            if ("200".equals(entity.getCode())) {
                CurrentUser.getInstance().born(entity.getData());
                initData();
                requestNum();
            }else if ("401".equals(entity.getCode())){
                ToastHelper.showToast("token失效，请重新登录");
                NavigationHelper.startActivity(getActivity(), UserLoginActivity.class, null, false);
            }else {
                ToastHelper.showToast(entity.getMessage());
            }
        }else if (requestType == 2){
            UserNumResp entity = JsonHelper.fromJson(response, UserNumResp.class);
            if ("200".equals(entity.getCode())) {
                initNum(entity.getData());
            }else {
                ToastHelper.showToast(entity.getMessage());
            }
        }

        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        return false;
    }

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


    private void requestData() {
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        post(ProjectConstants.Url.ACCOUNT_GET_USERINFO, requestMap,1);
    }

    private void requestNum() {
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("type",0);
        requestMap.put("id",CurrentUser.getInstance().getId());
        post(ProjectConstants.Url.ACCOUNT_GET_NUM, requestMap,2);
    }




    private void initData() {
        CurrentUser currentUser = CurrentUser.getInstance();
        if (currentUser.born()){
            txv_nickname.setText(Helper.isEmpty(currentUser.getNickname())?"匿名用户":currentUser.getNickname());
            txv_telephone.setText(ProjectHelper.getCommonText(currentUser.getBind_phone()));
            if (Helper.isNotEmpty(currentUser.getIcon()) && getActivity()!=null){
                Picasso.with(getActivity()).load(currentUser.getIcon()).placeholder(R.drawable.ic_avatar_default).into(imv_user_avater);
            }
        }
    }

    private void initNum(UserCenterInfo info){
        if (Helper.isNotEmpty(info)){
            txv_user_follows.setText(info.getFollow_count()+"");
            txv_user_fans.setText(info.getFans_count()+"");
            txv_user_teachers.setText(info.getStu_teach_num()+"");
        }
    }

    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_account://我的账户
                    NavigationHelper.startActivity(getActivity(), UserAccountActivity.class, null, false);
                    break;

                case R.id.btn_concern://关注
                    Bundle bundle = new Bundle();
                    bundle.putInt(ProjectConstants.BundleExtra.KEY_CONTACT_TYPE,0);
                    NavigationHelper.startActivity(getActivity(), UserContactActivity.class, bundle, false);
                    break;
                case R.id.btn_fans://粉丝
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt(ProjectConstants.BundleExtra.KEY_CONTACT_TYPE,1);
                    NavigationHelper.startActivity(getActivity(), UserContactActivity.class, bundle1, false);
                    PreferencesHelper.getInstance().putInt(ProjectConstants.Preferences.KEY_FANS_COUNT,0);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_FANS_NEW));
                    break;
                case R.id.btn_myteacher://我的老师
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(ProjectConstants.BundleExtra.KEY_CONTACT_TYPE,2);
                    NavigationHelper.startActivity(getActivity(), UserContactActivity.class, bundle2, false);
                    break;


                case R.id.btn_classroomorder://课程/课室订单
                    Bundle bundle5 = new Bundle();
                    bundle5.putInt("status",0);
                    NavigationHelper.startActivity(getActivity(), UserClassroomOrderActivity.class, bundle5, false);
                    break;
                case R.id.btn_activityorder://活动订单
                    NavigationHelper.startActivity(getActivity(), UserActivityOrderActivity.class, null, false);
                    break;

                case R.id.btn_classing://进行中的课程
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt(ProjectConstants.BundleExtra.KEY_TIMEABLE_TYPE, 0);
                    NavigationHelper.startActivity(getActivity(), UserTimetableActivity.class, bundle3, false);
                    break;
                case R.id.btn_classed://已结束的课程
                    Bundle bundle4 = new Bundle();
                    bundle4.putInt(ProjectConstants.BundleExtra.KEY_TIMEABLE_TYPE,1);
                    NavigationHelper.startActivity(getActivity(), UserTimetableActivity.class, bundle4, false);
                    break;
                case R.id.btn_coupons://我的优惠券
                    Bundle b1 = new Bundle();
                    b1.putInt("is_clickable",0);
                    NavigationHelper.startActivity(getActivity(), UserCouponsActivity.class, b1, false);
                    break;
                case R.id.btn_answer://我的问答
                    NavigationHelper.startActivity(getActivity(), UserAnswerActivity.class, null, false);
                    break;
                case R.id.btn_collect://我的收藏
                    NavigationHelper.startActivity(getActivity(), UserCollectActivity.class, null, false);
                    break;
                case R.id.btn_invite://邀请好友
                    NavigationHelper.startActivity(getActivity(), UserInviteActivity.class, null, false);
                    break;
                case R.id.btn_setting://设置
                    NavigationHelper.startActivity(getActivity(), UserSettingActivity.class, null, false);
                    break;
                case R.id.imv_user_avater://头像
                    NavigationHelper.startActivity(getActivity(), UserInfoActivity.class, null, false);
                    break;
                case R.id.imv_user_msg://未读消息数
                    NavigationHelper.startActivity(getActivity(), MyConversationListActivity.class,null,false);
                    break;
                case R.id.btn_buy_member:
                    NavigationHelper.startActivity(getActivity(),BuyMemberActivity.class,null,false);
                    break;
                default:
                    break;
            }
        }
    };
}
