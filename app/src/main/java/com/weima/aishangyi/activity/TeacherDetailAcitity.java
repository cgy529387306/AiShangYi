package com.weima.aishangyi.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.hyphenate.easeui.EaseConstant;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mb.android.utils.AppHelper;
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
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.chat.MyChatActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.constants.UrlConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.LessonBean;
import com.weima.aishangyi.entity.MemberBean;
import com.weima.aishangyi.entity.MemberResp;
import com.weima.aishangyi.entity.TeacherDetailEntity;
import com.weima.aishangyi.entity.TeacherDetailResp;
import com.weima.aishangyi.entity.TimeBean;
import com.weima.aishangyi.entity.UploadResp;
import com.weima.aishangyi.entity.User;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.map.AddressActivity;
import com.weima.aishangyi.pop.TimeablePop;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.widget.NestListView;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * 作者：cgy on 16/11/28 22:02
 * 邮箱：593960111@qq.com
 */
public class TeacherDetailAcitity extends BaseActivity implements View.OnClickListener {
    private NestListView listClass;
    private DetailClassAdapter mAdapter;
    private ImageView ivDetailImg, ivDetailAvatar;
    private TextView tvName,tvOrg, tvMajor, tvAddress, tvDistance;
    private TextView txv_detail_desc,txv_detail_comment;
    private View line_detail_desc,line_detail_comment;
    private LinearLayout descView;
    private TextView txv_desc;
    private ImageView imv_desc;
    private NestListView commentView;
    private LoadingView loadingView;
    private TeacherDetailEntity detailEntity;
    private ImageView collectBtn;
    private AppraiseAdapter appraiseAdapter;
    private long id;
    private TimeBean timeBean;
    private String dateStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        id = getIntent().getLongExtra("id", -1);
        setCustomTitle("老师详情");
        setImageRightButton(R.drawable.ic_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.isNotEmpty(detailEntity)){
                    Bundle bundle = new Bundle();
                    if (Helper.isNotEmpty(detailEntity.getIcon())){
                        bundle.putString("imageUrl",detailEntity.getIcon());
                    }
                    bundle.putString("title",detailEntity.getNickname());
                    NavigationHelper.startActivity(TeacherDetailAcitity.this,ShareActivity.class,bundle,false);
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
        tvOrg = findView(R.id.tv_org);
        tvMajor = findView(R.id.tv_major);
        tvAddress = findView(R.id.tv_address);
        tvDistance = findView(R.id.tv_distance);
        txv_detail_desc = findView(R.id.txv_detail_desc);
        txv_detail_comment = findView(R.id.txv_detail_comment);
        line_detail_desc = findView(R.id.line_detail_desc);
        line_detail_comment = findView(R.id.line_detail_comment);
        descView = findView(R.id.descView);
        commentView = findView(R.id.commentView);
        txv_desc = findView(R.id.txv_desc);
        imv_desc = findView(R.id.imv_desc);
        appraiseAdapter = new AppraiseAdapter(TeacherDetailAcitity.this);
        commentView.setAdapter(appraiseAdapter);
        listClass = findView(R.id.listClass);
        mAdapter = new DetailClassAdapter(TeacherDetailAcitity.this);
        listClass.setAdapter(mAdapter);
        loadingView = findView(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
        ivDetailImg.setFocusable(true);
        ivDetailImg.setFocusableInTouchMode(true);
        ivDetailImg.requestFocus();
        tvDistance.setOnClickListener(this);
        ivDetailAvatar.setOnClickListener(this);
        findView(R.id.btn_buy_class).setOnClickListener(this);
        findView(R.id.btn_timeable).setOnClickListener(this);
        findView(R.id.btn_ask).setOnClickListener(this);
        findView(R.id.btn_test).setOnClickListener(this);
        findView(R.id.lin_detail_desc).setOnClickListener(this);
        findView(R.id.lin_detail_comment).setOnClickListener(this);
        showDesc();
    }


    private void initData(TeacherDetailEntity entity){
        detailEntity = entity;
        if (Helper.isNotEmpty(entity)){
            if (Helper.isNotEmpty(entity.getImage_cover())){
                Picasso.with(this).load(entity.getImage_cover()).placeholder(R.drawable.img_default).into(ivDetailImg);
            }
            if (Helper.isNotEmpty(entity.getIcon())){
                Picasso.with(this).load(entity.getIcon()).placeholder(R.drawable.ic_avatar_default).into(ivDetailAvatar);
            }
            tvName.setText(ProjectHelper.getCommonText(entity.getNickname()));
            tvOrg.setText("机构："+ProjectHelper.getEmptyText(entity.getOrg()));
            tvMajor.setText("专业："+ProjectHelper.getEmptyText(entity.getMajor()));
            tvAddress.setText(ProjectHelper.getCommonText(entity.getAddress()));
            tvDistance.setText(ProjectHelper.formatDecimal(entity.getDistance()));
            mAdapter.addMore(entity.getLesson());
            txv_desc.setText(ProjectHelper.getCommonText(entity.getDetail()));
            collectBtn.setImageResource(detailEntity.getIs_collect() == 1 ? R.drawable.ic_collect_press : R.drawable.ic_collect);
            appraiseAdapter.addMore(detailEntity.getAppraise());
            if (Helper.isNotEmpty(entity.getAlbum())){
                imv_desc.setVisibility(View.VISIBLE);
                if (Helper.isNotEmpty(entity.getAlbum().get(0))){
                    Picasso.with(this).load(entity.getAlbum().get(0)).placeholder(R.drawable.img_default).into(imv_desc);
                }
            }else{
                imv_desc.setVisibility(View.GONE);
            }
        }
    }

    private void showDesc() {
        txv_detail_desc.setSelected(true);
        txv_detail_comment.setSelected(false);
        line_detail_desc.setVisibility(View.VISIBLE);
        line_detail_comment.setVisibility(View.INVISIBLE);
        descView.setVisibility(View.VISIBLE);
        commentView.setVisibility(View.GONE);
    }

    private void showComment() {
        txv_detail_desc.setSelected(false);
        txv_detail_comment.setSelected(true);
        line_detail_desc.setVisibility(View.INVISIBLE);
        line_detail_comment.setVisibility(View.VISIBLE);
        descView.setVisibility(View.GONE);
        commentView.setVisibility(View.VISIBLE);
    }

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
        post(UrlConstants.TEACHER_DETAIL_URL, params, 1);
    }

    private void requestCollect(){
        ProgressDialogHelper.showProgressDialog(TeacherDetailAcitity.this, "收藏中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id",id);
        requestMap.put("device",0);
        requestMap.put("type",3);
        post(ProjectConstants.Url.COLLECT, requestMap,2);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        int respType = Integer.parseInt(extras[0].toString());
        try {
            if(1 == respType){
                TeacherDetailResp resp = JsonHelper.fromJson(response, TeacherDetailResp.class);
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
                    LocalBroadcastManager.getInstance(TeacherDetailAcitity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_TEACHER_LIST));
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
            ToastHelper.showToast("请求失败");
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
            if (Helper.isNotEmpty(detailEntity)){
                if (Helper.isNotEmpty(detailEntity.getLesson()) && detailEntity.getLesson().size()>mAdapter.getSelectIndex()){
                    if (Helper.isNotEmpty(MemberBean.getInstance())){
                        if (MemberBean.getInstance().getIs_member()==1){
                           if (Helper.isEmpty(timeBean) || Helper.isEmpty(dateStr)){
                               ToastHelper.showToast("请选择预约时间");
                           }else{
                               if (MemberBean.getInstance().getNumber()>=1){
                                   LessonBean lessonBean = detailEntity.getLesson().get(mAdapter.getSelectIndex());
                                   Bundle bundle = new Bundle();
                                   bundle.putSerializable("lesson", lessonBean);
                                   bundle.putSerializable("timeBean", timeBean);
                                   bundle.putSerializable("dateStr", dateStr);
                                   bundle.putString("address",detailEntity.getAddress());
                                   NavigationHelper.startActivity(TeacherDetailAcitity.this, BuyClassActivity.class, bundle, false);
                               }else{
                                   showBuyMember("可预订课时数不足！");
                               }
                           }
                        }else{
                            showBuyMember("需要充值会员才能预约课程哦~");
                        }
                    }
                }else{
                    ToastHelper.showToast("请选择课程");
                }
            }
        }else if(id == R.id.btn_timeable){
            if (Helper.isNotEmpty(detailEntity) && Helper.isNotEmpty(detailEntity.getLesson_time())){
                if (MemberBean.getInstance()!=null && MemberBean.getInstance().getIs_member()==1){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("lesson_time", (Serializable) detailEntity.getLesson_time());
                    NavigationHelper.startActivityForResult(TeacherDetailAcitity.this,TimePickActivity.class,bundle,1);
                }else{
                    showBuyMember("需要充值会员才能预约课程哦~");
                }
            }else{
                ToastHelper.showToast("无可预约时间表");
            }
        }else if (id == R.id.btn_ask){
            if (MemberBean.getInstance()!=null && MemberBean.getInstance().getIs_member()==1){
                Bundle bundle=new Bundle();
                bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                bundle.putString(EaseConstant.EXTRA_USER_ID, "teach_"+detailEntity.getId());
                bundle.putString(EaseConstant.EXTRA_USER_NAME, detailEntity.getNickname());
                NavigationHelper.startActivity(TeacherDetailAcitity.this, MyChatActivity.class, bundle, false);
            }else{
                ProjectHelper.callTel(TeacherDetailAcitity.this);
            }
        }else if (id == R.id.btn_test){
            if (detailEntity.getIs_test() ==1){
                ProjectHelper.callTel(TeacherDetailAcitity.this);
            }else{
                ToastHelper.showToast("不支持试课");
            }
        }else if (id == R.id.lin_detail_desc){
            showDesc();
        }else if (id == R.id.lin_detail_comment){
            showComment();
        }else if (id == R.id.tv_distance){
            if (Helper.isNotEmpty(detailEntity.getA_latitude())&&Helper.isNotEmpty(detailEntity.getA_longitude())){
                Bundle bundle = new Bundle();
                bundle.putString("latitude",detailEntity.getA_latitude());
                bundle.putString("longitude",detailEntity.getA_longitude());
                NavigationHelper.startActivity(TeacherDetailAcitity.this, AddressActivity.class, bundle, false);
            }
        }else if (id == R.id.imv_detail_avatar){
            if (Helper.isNotEmpty(detailEntity)){
                User user = new User();
                user.setId(detailEntity.getId());
                user.setDevice(detailEntity.getDevice());
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                bundle.putSerializable(ProjectConstants.BundleExtra.KEY_USER, user);
                NavigationHelper.startActivity(TeacherDetailAcitity.this, PersonInfoActivity.class, bundle, false);
            }
        }
    }

    private void showBuyMember(String title){
        DialogHelper.showConfirmDialog(TeacherDetailAcitity.this, "", title, true,
                R.string.dialog_buy, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavigationHelper.startActivity(TeacherDetailAcitity.this,BuyMemberActivity.class,null,false);
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
