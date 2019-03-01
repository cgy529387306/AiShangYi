package com.weima.aishangyi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.AppHelper;
import com.mb.android.utils.DialogHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.TalentCommentAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommentListResp;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.entity.TalentBean;
import com.weima.aishangyi.entity.TalentDetailResp;
import com.weima.aishangyi.entity.ThumbBean;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.widget.CircleTransform;
import com.weima.aishangyi.widget.GridListView;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;

/**
 * 作者：cgy on 16/11/28 22:02
 * 邮箱：593960111@qq.com
 * 动态详情
 */
public class TalentCircleDetailAcitity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    private XListView xListView;
    private LoadingView loadingView;
    private TalentCommentAdapter adapter;
    private EditText edit_content;
    private ImageView imv_user_avater, imv_weibo_operate;
    private TextView txv_user_name, txv_content, txv_time, txv_like, txv_comment_count;
    private GridListView picList;
    private int currentPage = 1;
    private TalentBean talentBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talentcircle_detail);
        setCustomTitle("动态详情");
        initUI();
        initData();
        requestCommentList();
    }

    private void initUI() {
        talentBean = (TalentBean) getIntent().getSerializableExtra(ProjectConstants.BundleExtra.KEY_TALENT);
        edit_content = findView(R.id.edit_content);
        loadingView = findView(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
        xListView = findView(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        xListView.addHeaderView(getHeaderView());
        adapter = new TalentCommentAdapter(TalentCircleDetailAcitity.this);
        xListView.setAdapter(adapter);
        findView(R.id.btn_commit).setOnClickListener(this);
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(TalentCircleDetailAcitity.this).inflate(R.layout.header_talentcircle_detail, null);
        imv_user_avater = (ImageView) view.findViewById(R.id.imv_user_avater);
        imv_weibo_operate = (ImageView) view.findViewById(R.id.imv_weibo_operate);
        txv_user_name = (TextView) view.findViewById(R.id.txv_user_name);
        txv_content = (TextView) view.findViewById(R.id.txv_content);
        picList = (GridListView) view.findViewById(R.id.picList);
        txv_time = (TextView) view.findViewById(R.id.txv_time);
        txv_like = (TextView) view.findViewById(R.id.txv_like);
        txv_comment_count = (TextView) view.findViewById(R.id.txv_comment_count);
        imv_weibo_operate.setOnClickListener(this);
        txv_like.setOnClickListener(this);
        return view;
    }

    private void initData() {
        if (Helper.isNotEmpty(talentBean)) {
            if (Helper.isNotEmpty(talentBean.getUser())) {
                imv_weibo_operate.setVisibility(ProjectHelper.isMine(talentBean.getUser().getDevice(),talentBean.getUser().getId()) ? View.VISIBLE : View.INVISIBLE);
                if (Helper.isNotEmpty(talentBean.getUser().getNickname())) {
                    txv_user_name.setText(ProjectHelper.getCommonText(talentBean.getUser().getNickname()));
                } else {
                    txv_user_name.setText(ProjectHelper.getCommonText(talentBean.getUser().getPhone()));
                }
                if (Helper.isNotEmpty(talentBean.getImages())) {
                    picList.setVisibility(View.VISIBLE);
                    picList.setData(talentBean.getImages());
                } else {
                    picList.setVisibility(View.GONE);
                }
                if (Helper.isNotEmpty(talentBean.getUser().getIcon())) {
                    Picasso.with(TalentCircleDetailAcitity.this).load(talentBean.getUser().getIcon()).placeholder(R.drawable.ic_avatar_default).into(imv_user_avater);
                }
            }
            txv_content.setText(ProjectHelper.getCommonText(talentBean.getContent()));
            txv_time.setText(Helper.long2DateString(Long.parseLong(talentBean.getCreated_at()) * 1000, "yyyy-MM-dd HH:mm"));
            txv_like.setText(talentBean.getThumb_up() + "");
            if (Helper.isEmpty(talentBean.getIs_thumb())) {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_answer_good);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                txv_like.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_answer_good_press);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                txv_like.setCompoundDrawables(drawable, null, null, null);
            }
        }
    }


    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
        switch (requestType) {
            case 1:
                //详情信息
                loadingView.postLoadState(LoadingView.State.GONE);
                TalentDetailResp entity = JsonHelper.fromJson(response, TalentDetailResp.class);
                if ("200".equals(entity.getCode())) {
//                    initData(entity);
                } else {
                    ToastHelper.showToast(entity.getMessage());
                }
                xListView.stopRefresh();
                xListView.stopLoadMore();
                break;
            case 2:
                //删除
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity commonEntity = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(commonEntity.getCode())) {
                    LocalBroadcastManager.getInstance(TalentCircleDetailAcitity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_TALENT_LIST));
                    ToastHelper.showToast("删除成功！");
                    finish();
                } else {
                    ToastHelper.showToast(commonEntity.getMessage());
                }
                break;
            case 3:
                //点赞
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity upEntity = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(upEntity.getCode())) {
                    LocalBroadcastManager.getInstance(TalentCircleDetailAcitity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_TALENT_LIST));
                    if (Helper.isEmpty(talentBean.getIs_thumb())) {
                        ToastHelper.showToast("点赞成功！");
                        ThumbBean thumbBean = new ThumbBean();
                        thumbBean.setThumb_id(talentBean.getId());
                        talentBean.setIs_thumb(thumbBean);
                        talentBean.setThumb_up(talentBean.getThumb_up() + 1);
                    } else {
                        ToastHelper.showToast("取消点赞成功！");
                        talentBean.setIs_thumb(null);
                        talentBean.setThumb_up(talentBean.getThumb_up() - 1);
                    }
                    initData();
                } else {
                    ToastHelper.showToast(upEntity.getMessage());
                }
                break;
            case 4:
                //评论
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity commentEntity = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(commentEntity.getCode())) {
                    edit_content.setText("");
                    LocalBroadcastManager.getInstance(TalentCircleDetailAcitity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_TALENT_LIST));
                    ToastHelper.showToast("评论成功！");
                    currentPage = 1;
                    requestCommentList();
                } else {
                    ToastHelper.showToast(commentEntity.getMessage());
                }
                break;
            case 5:
                //评论列表
                loadingView.postLoadState(LoadingView.State.GONE);
                CommentListResp commentListResp = JsonHelper.fromJson(response, CommentListResp.class);
                if ("200".equals(commentListResp.getCode())) {
                    if (Helper.isNotEmpty(commentListResp.getData())) {
                        if (currentPage == 1) {
                            adapter.clear();
                            if (Helper.isNotEmpty(commentListResp.getData().getData())) {
                                txv_comment_count.setText("全部评论 (" + commentListResp.getData().getTotal() + ")");
                            } else {
                                txv_comment_count.setText("暂无评论");
                            }
                        }
                        adapter.addMore(commentListResp.getData().getData());
                        if (Helper.isEmpty(commentListResp.getData().getData()) ||
                                currentPage == commentListResp.getData().getLast_page()) {
                            xListView.setPullLoadEnable(false);
                        } else {
                            xListView.setPullLoadEnable(true);
                        }
                    } else {
                        xListView.setPullLoadEnable(false);
                    }
                } else {
                    ToastHelper.showToast(commentListResp.getMessage());
                }
                xListView.stopRefresh();
                xListView.stopLoadMore();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
        switch (requestType) {
            case 1:
                loadingView.postLoadState(LoadingView.State.LOADING_FALIED);
                break;
            case 2:
                ProgressDialogHelper.dismissProgressDialog();
                ToastHelper.showToast("删除失败！");
                break;
            case 3:
                ProgressDialogHelper.dismissProgressDialog();
                ToastHelper.showToast("操作失败！");
                break;
            case 4:
                ProgressDialogHelper.dismissProgressDialog();
                ToastHelper.showToast("评论失败！");
                break;
            default:
                break;
        }
        return true;
    }

//    private void requestData(){
//        HashMap<String, Object> requestMap = new HashMap<String, Object>();
//        requestMap.put("id",talentBean.getId());
//        post(ProjectConstants.Url.TALENT_DETAIl, requestMap,1);
//    }

    private void requestDelete() {
        ProgressDialogHelper.showProgressDialog(TalentCircleDetailAcitity.this, "删除中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id", talentBean.getId());
        requestMap.put("device", 0);
        post(ProjectConstants.Url.TALENT_DELETE, requestMap, 2);
    }

    private void requestUp() {
        ProgressDialogHelper.showProgressDialog(TalentCircleDetailAcitity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id", talentBean.getId());
        requestMap.put("device", 0);
        post(ProjectConstants.Url.TALENT_UP, requestMap, 3);
    }

    private void requestComment() {
        String content = edit_content.getText().toString();
        if (Helper.isEmpty(content)) {
            ToastHelper.showToast("说些什么呗...");
            return;
        }
        ProgressDialogHelper.showProgressDialog(TalentCircleDetailAcitity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("talent_id", talentBean.getId());
        requestMap.put("comment", content);
        post(ProjectConstants.Url.TALENT_COMMENT, requestMap, 4);
    }

    private void requestCommentList() {
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("talent_id", talentBean.getId());
        requestMap.put("page", currentPage);
        post(ProjectConstants.Url.TALENT_COMMENT_LIST, requestMap, 5);
    }

    @Override
    public void onClick(View v) {
        ProjectHelper.disableViewDoubleClick(v);
        int id = v.getId();
        if (id == R.id.imv_weibo_operate) {
            DialogHelper.showConfirmDialog(TalentCircleDetailAcitity.this, "删除", "确定要删除该条动态？", true,
                    R.string.dialog_positive, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestDelete();
                        }

                    }, R.string.dialog_negative, null);
        } else if (id == R.id.txv_like) {
            requestUp();
        } else if (id == R.id.btn_commit) {
            AppHelper.hideSoftInputFromWindow(v);
            requestComment();
        }
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        requestCommentList();
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        requestCommentList();
    }
}
