package com.weima.aishangyi.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.mb.android.utils.DialogHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.AppraiseAdapter;
import com.weima.aishangyi.adapter.DetailClassAdapter;
import com.weima.aishangyi.adapter.TalentCircleAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.chat.MyChatActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.constants.UrlConstants;
import com.weima.aishangyi.entity.ClassBean;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.LessonBean;
import com.weima.aishangyi.entity.LessonDetailBean;
import com.weima.aishangyi.entity.LessonDetailResp;
import com.weima.aishangyi.entity.MemberBean;
import com.weima.aishangyi.entity.MemberResp;
import com.weima.aishangyi.entity.TimeBean;
import com.weima.aishangyi.entity.User;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.map.AddressActivity;
import com.weima.aishangyi.pop.TimeablePop;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.widget.NestListView;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 作者：cgy on 16/11/28 22:02
 * 邮箱：593960111@qq.com
 * 课程详情
 */
public class ClassDetailAcitity extends BaseActivity implements View.OnClickListener {
    private TextView tv_class_name,tv_class_org,tv_class_desc;
    private ImageView ivDetailImg, ivDetailAvatar;
    private TextView tvName, tvMajor,tvAddress, tvDistance;
    private LoadingView loadingView;
    private LessonDetailBean detailEntity;
    private ImageView collectBtn;
    private long id;
    private TimeBean timeBean;
    private String dateStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        id = getIntent().getLongExtra("id", -1);
        setCustomTitle("课程详情");
        setImageRightButton(R.drawable.ic_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.isNotEmpty(detailEntity)){
                    Bundle bundle = new Bundle();
                    if (Helper.isNotEmpty(detailEntity.getIcon())){
                        bundle.putString("imageUrl",detailEntity.getIcon());
                    }
                    bundle.putString("title",detailEntity.getName());
                    NavigationHelper.startActivity(ClassDetailAcitity.this,ShareActivity.class,bundle,false);
                }
            }
        });
        collectBtn = setImageRightButton2(R.drawable.ic_collect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCollect();
            }
        });
        initUI();
        getTeacherDetail();
        requestMember();
    }

    private void initUI() {
        ivDetailImg = findView(R.id.imv_detail_img);
        ivDetailAvatar = findView(R.id.imv_detail_avatar);
        tvName = findView(R.id.tv_name);
        tvMajor = findView(R.id.tv_major);
        tvAddress = findView(R.id.tv_address);
        tvDistance = findView(R.id.tv_distance);
        tv_class_name = findView(R.id.tv_class_name);
        tv_class_org = findView(R.id.tv_class_org);
        tv_class_desc = findView(R.id.tv_class_desc);
        loadingView = findView(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
        ivDetailImg.setFocusable(true);
        ivDetailImg.setFocusableInTouchMode(true);
        ivDetailImg.requestFocus();
        tvDistance.setOnClickListener(this);
        findView(R.id.btn_buy_class).setOnClickListener(this);
        findView(R.id.btn_timeable).setOnClickListener(this);
        findView(R.id.btn_ask).setOnClickListener(this);
        findView(R.id.btn_test).setOnClickListener(this);
        findView(R.id.txv_teacher).setOnClickListener(this);
    }


    private void initData(LessonDetailBean entity){
        detailEntity = entity;
        if (Helper.isNotEmpty(entity)){
            collectBtn.setImageResource(detailEntity.getIs_collect()==1?R.drawable.ic_collect_press:R.drawable.ic_collect);
            tv_class_name.setText(ProjectHelper.getCommonText(entity.getName()));
            tv_class_org.setText("机构："+ProjectHelper.getEmptyText(entity.getTeacher().getOrg()));
            tv_class_desc.setText("简介："+ProjectHelper.getEmptyText(entity.getLesson_brief()));
            if (Helper.isNotEmpty(entity.getIcon())){
                Picasso.with(this).load(entity.getIcon()).placeholder(R.drawable.img_default).into(ivDetailImg);
            }
            if (Helper.isNotEmpty(entity.getTeacher())){
                tvName.setText(ProjectHelper.getCommonText(entity.getTeacher().getNickname()));
                tvMajor.setText("专业：" + ProjectHelper.getEmptyText(entity.getTeacher().getMajor()));
                tvDistance.setText(ProjectHelper.formatDecimal(entity.getTeacher().getDistance()));
                tvAddress.setText(ProjectHelper.getCommonText(entity.getTeacher().getAddress()));
                if (Helper.isNotEmpty(entity.getTeacher().getIcon())){
                    Picasso.with(this).load(entity.getTeacher().getIcon()).placeholder(R.drawable.ic_avatar_default).into(ivDetailAvatar);
                }
//                appraiseAdapter.addMore(entity.getTeacher().getAppraise());
            }

        }
    }

//    private void showDesc() {
//        txv_detail_desc.setSelected(true);
//        txv_detail_comment.setSelected(false);
//        line_detail_desc.setVisibility(View.VISIBLE);
//        line_detail_comment.setVisibility(View.INVISIBLE);
//        descView.setVisibility(View.VISIBLE);
//        commentView.setVisibility(View.GONE);
//    }
//
//    private void showComment() {
//        txv_detail_desc.setSelected(false);
//        txv_detail_comment.setSelected(true);
//        line_detail_desc.setVisibility(View.INVISIBLE);
//        line_detail_comment.setVisibility(View.VISIBLE);
//        descView.setVisibility(View.GONE);
//        commentView.setVisibility(View.VISIBLE);
//    }

    /**
     * 获取教师详情
     */
    private void getTeacherDetail(){
        String longitude = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_LON);
        String latitude = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_LAT);
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        post(UrlConstants.HOME_CLASS_DETAIL, params, 1);
    }

    private void requestCollect(){
        ProgressDialogHelper.showProgressDialog(ClassDetailAcitity.this, "收藏中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id",id);
        requestMap.put("device",0);
        requestMap.put("type",2);
        post(ProjectConstants.Url.COLLECT, requestMap,2);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        int respType = Integer.parseInt(extras[0].toString());
        try {
            if(1 == respType){
                LessonDetailResp resp = JsonHelper.fromJson(response, LessonDetailResp.class);
                if(null != resp && Helper.isNotEmpty(resp.getData())){
                    initData(resp.getData());
                }
            }else if (2 == respType){
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity collectEntity = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(collectEntity.getCode())){
                    if (detailEntity.getIs_collect()==1){
                        detailEntity.setIs_collect(0);
                        ToastHelper.showToast("取消收藏成功");
                    }else{
                        detailEntity.setIs_collect(1);
                        ToastHelper.showToast("收藏成功");
                    }
                    collectBtn.setImageResource(detailEntity.getIs_collect()==1?R.drawable.ic_collect_press:R.drawable.ic_collect);
                    LocalBroadcastManager.getInstance(ClassDetailAcitity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_LESSON_LIST));
                }else{
                    ToastHelper.showToast(collectEntity.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastHelper.showToast("请求异常："+e.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
        if(1 == requestType){
            loadingView.postLoadState(LoadingView.State.LOADING_FALIED);
        }else if (2 == requestType){
            ProgressDialogHelper.dismissProgressDialog();
            ToastHelper.showToast("收藏失败");
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (Helper.isEmpty(detailEntity)){
            return;
        }
        if (id == R.id.btn_buy_class){
            if (Helper.isNotEmpty(MemberBean.getInstance())){
                if (MemberBean.getInstance().getIs_member()==1){
                    if (Helper.isEmpty(timeBean) || Helper.isEmpty(dateStr)){
                        ToastHelper.showToast("请选择预约时间");
                    }else{
                        if (MemberBean.getInstance().getNumber()>=1){
                            if (Helper.isNotEmpty(detailEntity)){
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("lesson", transLesson(detailEntity));
                                bundle.putSerializable("timeBean", timeBean);
                                bundle.putSerializable("dateStr", dateStr);
                                if (Helper.isNotEmpty(detailEntity.getTeacher())){
                                    bundle.putString("address",detailEntity.getTeacher().getAddress());
                                }
                                NavigationHelper.startActivity(ClassDetailAcitity.this, BuyClassActivity.class, bundle, false);
                            }
                        }else{
                            showBuyMember("可预订课时数不足！");
                        }
                    }
                }else{
                    showBuyMember("需要充值会员才能预约课程哦~");
                }
            }
        }else if (id == R.id.btn_ask){
            if (MemberBean.getInstance()!=null && MemberBean.getInstance().getIs_member()==1){
                Bundle bundle=new Bundle();
                bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                bundle.putString(EaseConstant.EXTRA_USER_ID, "teach_"+detailEntity.getTeacher().getId());
                bundle.putString(EaseConstant.EXTRA_USER_NAME, detailEntity.getTeacher().getNickname());
                NavigationHelper.startActivity(ClassDetailAcitity.this, MyChatActivity.class, bundle, false);
            }else{
                ProjectHelper.callTel(ClassDetailAcitity.this);
            }
        }else if(id == R.id.btn_timeable){
            if (Helper.isNotEmpty(detailEntity) && Helper.isNotEmpty(detailEntity.getTeacher())&&
                    Helper.isNotEmpty(detailEntity.getTeacher().getLesson_time())){
                Bundle bundle = new Bundle();
                bundle.putSerializable("lesson_time", (Serializable) detailEntity.getTeacher().getLesson_time());
                NavigationHelper.startActivityForResult(ClassDetailAcitity.this,TimePickActivity.class,bundle,1);
            }else{
                ToastHelper.showToast("无可预约时间表");
            }
        }else if (id == R.id.btn_test){
            if (Helper.isNotEmpty(detailEntity)&& Helper.isNotEmpty(detailEntity.getTeacher())){
                if (detailEntity.getTeacher().getIs_test() ==1){
                    ProjectHelper.callTel(ClassDetailAcitity.this);
                }else{
                    ToastHelper.showToast("不支持试课");
                }
            }
        }else if(id == R.id.tv_distance){
            if (Helper.isNotEmpty(detailEntity.getTeacher().getA_latitude())&&Helper.isNotEmpty(detailEntity.getTeacher().getA_longitude())){
                Bundle bundle = new Bundle();
                bundle.putString("latitude",detailEntity.getTeacher().getA_latitude());
                bundle.putString("longitude",detailEntity.getTeacher().getA_longitude());
                NavigationHelper.startActivity(ClassDetailAcitity.this, AddressActivity.class, bundle, false);
            }
        }else if (id == R.id.txv_teacher){
            Intent intent = new Intent(ClassDetailAcitity.this, TeacherDetailAcitity.class);
            intent.putExtra("id", detailEntity.getTeacher().getId());
            startActivity(intent);
        }
    }


    private void showBuyMember(String title){
        DialogHelper.showConfirmDialog(ClassDetailAcitity.this, "", title, true,
                R.string.dialog_buy, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavigationHelper.startActivity(ClassDetailAcitity.this,BuyMemberActivity.class,null,false);
                    }

                }, R.string.dialog_negative, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 1){
            dateStr = data.getStringExtra("dateStr");
            timeBean = (TimeBean) data.getSerializableExtra("timeBean");
        }
    }


    private LessonBean transLesson(LessonDetailBean detail){
        LessonBean lessonBean = new LessonBean();
        lessonBean.setId(detail.getId());
        lessonBean.setIcon(detail.getIcon());
        lessonBean.setLesson_item(detail.getLesson_item());
        lessonBean.setLesson_brief(detail.getLesson_brief());
        lessonBean.setLesson_detail(detail.getLesson_detail());
        return lessonBean;
    }

    private void requestMember(){
        final RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(ProjectConstants.Url.USER_MEMBER)
                .setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(new HashMap<String,Object>()).getRequestEntity();
        RequestHelper.post(entity, new ResponseListener() {
            @Override
            public boolean onResponseSuccess(int gact, String response, Object... extras) {
                try {
                    if (Helper.isNotEmpty(response)){
                        MemberResp entity = JsonHelper.fromJson(response,MemberResp.class);
                        if (Helper.isNotEmpty(entity) && "200".equals(entity.getCode())
                                && Helper.isNotEmpty(entity.getData())){
                            MemberBean.getInstance().born(entity.getData());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
                return false;
            }
        });
    }
}
