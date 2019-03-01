package com.weima.aishangyi.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
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
import com.weima.aishangyi.entity.InfoBean;
import com.weima.aishangyi.entity.ThumbBean;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.xlistview.XListView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * 作者：cgy on 16/11/28 22:02
 * 邮箱：593960111@qq.com
 * 才艺资讯详情
 */
public class TalentqueryDetailAcitity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    private XListView xListView;
    private TalentCommentAdapter adapter;
    private int currentPage = 1;
    private InfoBean infoBean;
    private ImageView imv_detail_img;
    private EditText edit_content;
    private TextView txv_title,txv_create_date,txv_thumb,txv_collect,txv_content,txv_comment_count;
    private ImageView collectBtn;

    /*
     * Handler
     * 类应该应该为static类型，否则有可能造成泄露。在程序消息队列中排队的消息保持了对目标Handler类的应用。如果Handler是个内部类，那
     * 么它也会保持它所在的外部类的引用。为了避免泄露这个外部类，应该将Handler声明为static嵌套类，并且使用对外部类的弱应用。
     */
    private static class MyHandler extends Handler {
        WeakReference<TalentqueryDetailAcitity> mActivity;
        public MyHandler(TalentqueryDetailAcitity activity) {
            // TODO Auto-generated constructor stub
            mActivity = new WeakReference<TalentqueryDetailAcitity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            TalentqueryDetailAcitity theActivity = mActivity.get();
            if (msg.what == 0x101) {
                theActivity.txv_content.setText((CharSequence) msg.obj);
            }
            super.handleMessage(msg);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talentquery_detail);
        setCustomTitle("详情");
        setImageRightButton(R.drawable.ic_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.isNotEmpty(infoBean)){
                    Bundle bundle = new Bundle();
                    if (Helper.isNotEmpty(infoBean.getImage_url())){
                        bundle.putString("imageUrl",infoBean.getImage_url());
                    }
                    bundle.putString("title",infoBean.getTitle());
                    NavigationHelper.startActivity(TalentqueryDetailAcitity.this,ShareActivity.class,bundle,false);
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
        requestCommentList();
    }


    private void initUI() {
        infoBean = (InfoBean) getIntent().getSerializableExtra(ProjectConstants.BundleExtra.KEY_INFO);
        edit_content = findView(R.id.edit_content);
        xListView = findView(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        xListView.addHeaderView(getHeaderView());
        adapter = new TalentCommentAdapter(TalentqueryDetailAcitity.this);
        xListView.setAdapter(adapter);
        collectBtn.setImageResource(infoBean.getIs_collect() == 1 ? R.drawable.ic_collect_press : R.drawable.ic_collect);
        findView(R.id.btn_commit).setOnClickListener(this);
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(TalentqueryDetailAcitity.this).inflate(R.layout.header_talent_detail, null);
        imv_detail_img = (ImageView) view.findViewById(R.id.imv_detail_img);
        txv_title = (TextView) view.findViewById(R.id.txv_title);
        txv_create_date = (TextView) view.findViewById(R.id.txv_create_date);
        txv_thumb = (TextView) view.findViewById(R.id.txv_thumb);
        txv_collect = (TextView) view.findViewById(R.id.txv_collect);
        txv_content = (TextView) view.findViewById(R.id.txv_content);
        txv_comment_count = (TextView) view.findViewById(R.id.txv_comment_count);
        if (Helper.isNotEmpty(infoBean.getImage_url())){
            Picasso.with(TalentqueryDetailAcitity.this).load(infoBean.getImage_url()).placeholder(R.drawable.img_default).into(imv_detail_img);
        }
        txv_title.setText(ProjectHelper.getCommonText(infoBean.getTitle()));
        txv_create_date.setText(Helper.long2DateString(Long.parseLong(infoBean.getCreated_at()) * 1000, "yyyy-MM-dd"));
        txv_collect.setText(infoBean.getCollect_num() + "");
        txv_thumb.setText(infoBean.getThumb_up() + "");
        setHtmlContent(infoBean.getContent());
        if (Helper.isEmpty(infoBean.is_thumb())){
            Drawable drawable= getResources().getDrawable(R.drawable.ic_answer_good);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            txv_thumb.setCompoundDrawables(drawable, null, null, null);
        }else{
            Drawable drawable= getResources().getDrawable(R.drawable.ic_answer_good_press);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            txv_thumb.setCompoundDrawables(drawable, null, null, null);
        }
        txv_thumb.setOnClickListener(this);
        txv_collect.setOnClickListener(this);
        return view;
    }

    private void setHtmlContent(final String html){
        final MyHandler myHandler = new MyHandler(this);
        Thread t = new Thread(new Runnable() {
            Message msg = Message.obtain();

            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {

                    @Override
                    public Drawable getDrawable(String source) {
                        URL url;
                        Drawable drawable = null;
                        try {
                            url = new URL(source);
                            drawable = Drawable.createFromStream(
                                    url.openStream(), null);
                            drawable.setBounds(0, 0,
                                    drawable.getIntrinsicWidth(),
                                    drawable.getIntrinsicHeight());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return drawable;
                    }
                };
                CharSequence test = Html.fromHtml(html, imageGetter, null);
                msg.what = 0x101;
                msg.obj = test;
                myHandler.sendMessage(msg);
            }
        });
        t.start();
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
        switch (requestType){
            case 2:
                //收藏
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity collectEntiy = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(collectEntiy.getCode())){
                    if (infoBean.getIs_collect()==1){
                        infoBean.setIs_collect(0);
                        infoBean.setCollect_num(infoBean.getCollect_num()-1);
                        ToastHelper.showToast("取消收藏成功");
                    }else{
                        infoBean.setIs_collect(1);
                        infoBean.setCollect_num(infoBean.getCollect_num()+1);
                        ToastHelper.showToast("收藏成功");
                    }
                    collectBtn.setImageResource(infoBean.getIs_collect() == 1 ? R.drawable.ic_collect_press : R.drawable.ic_collect);
                    txv_collect.setText(infoBean.getCollect_num() + "");
                    LocalBroadcastManager.getInstance(TalentqueryDetailAcitity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_INFO_LIST));
                }else{
                    ToastHelper.showToast(collectEntiy.getMessage());
                }
                break;
            case 3:
                //点赞
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity upEntity = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(upEntity.getCode())){
                    if (Helper.isEmpty(infoBean.is_thumb())){
                        ToastHelper.showToast("点赞成功！");
                        ThumbBean thumbBean = new ThumbBean();
                        thumbBean.setThumb_id(infoBean.getId());
                        infoBean.setIs_thumb(thumbBean);
                        infoBean.setThumb_up(infoBean.getThumb_up()+1);
                    }else{
                        ToastHelper.showToast("取消点赞成功！");
                        infoBean.setIs_thumb(null);
                        infoBean.setThumb_up(infoBean.getThumb_up()-1);
                    }
                    txv_thumb.setText(infoBean.getThumb_up() + "");
                    if (Helper.isEmpty(infoBean.is_thumb())){
                        Drawable drawable= getResources().getDrawable(R.drawable.ic_answer_good);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        txv_thumb.setCompoundDrawables(drawable, null, null, null);
                    }else{
                        Drawable drawable= getResources().getDrawable(R.drawable.ic_answer_good_press);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        txv_thumb.setCompoundDrawables(drawable, null, null, null);
                    }
                }else{
                    ToastHelper.showToast(upEntity.getMessage());
                }
                break;
            case 4:
                //评论
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity commentEntity = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(commentEntity.getCode())){
                    edit_content.setText("");
                    ToastHelper.showToast("评论成功！");
                    currentPage = 1;
                    requestCommentList();
                }else{
                    ToastHelper.showToast(commentEntity.getMessage());
                }
                break;
            case 5:
                //评论列表
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
        ToastHelper.showToast("请求失败！");
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_commit){
            requestComment();
        }else if (id == R.id.txv_thumb){
            requestUp();
        }
    }

    private void requestCollect(){
        ProgressDialogHelper.showProgressDialog(TalentqueryDetailAcitity.this,"加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id",infoBean.getId());
        requestMap.put("device",0);
        requestMap.put("type",1);
        post(ProjectConstants.Url.COLLECT, requestMap,2);
    }

    private void requestUp(){
        ProgressDialogHelper.showProgressDialog(TalentqueryDetailAcitity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id",infoBean.getId());
        requestMap.put("device",0);
        post(ProjectConstants.Url.INFO_THUMB, requestMap,3);
    }

    private void requestComment(){
        String content = edit_content.getText().toString();
        if (Helper.isEmpty(content)){
            ToastHelper.showToast("说些什么呗...");
            return;
        }
        ProgressDialogHelper.showProgressDialog(TalentqueryDetailAcitity.this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("inform_id",infoBean.getId());
        requestMap.put("comment",content);
        post(ProjectConstants.Url.INFO_COMMENT, requestMap,4);
    }


    private void requestCommentList(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("inform_id",infoBean.getId());
        requestMap.put("page",currentPage);
        post(ProjectConstants.Url.INFO_COMMENT_LIST, requestMap,5);
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
