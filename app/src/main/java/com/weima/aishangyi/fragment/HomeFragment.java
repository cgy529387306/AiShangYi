package com.weima.aishangyi.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.mb.android.utils.AppHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.ClassListActivity;
import com.weima.aishangyi.activity.HomeActivity;
import com.weima.aishangyi.activity.HomeSearchActivity;
import com.weima.aishangyi.activity.SelectCityActivity;
import com.weima.aishangyi.adapter.HeadWheelAdapter;
import com.weima.aishangyi.adapter.RecommendClassAdapter;
import com.weima.aishangyi.adapter.RecommendGridAdapter;
import com.weima.aishangyi.adapter.RecommendTeacherAdapter;
import com.weima.aishangyi.chat.MyConversationListActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.constants.UrlConstants;
import com.weima.aishangyi.entity.ActiveCourseEntity;
import com.weima.aishangyi.entity.ActiveCourseResp;
import com.weima.aishangyi.entity.ActivityHomeResp;
import com.weima.aishangyi.entity.ActivityResp;
import com.weima.aishangyi.entity.AdvertResp;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.entity.RecommendTeacherResp;
import com.weima.aishangyi.entity.StuCategoryEntity;
import com.weima.aishangyi.entity.StuCategoryResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.utils.Utils;
import com.weima.aishangyi.widget.NestedGridView;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cgy on 16/7/18.
 * 首页
 */
public class HomeFragment extends Fragment implements XListView.IXListViewListener,View.OnClickListener, ResponseListener ,HomeActivity.MyCityListener{
    private TextView txv_home_location;
    private ImageView imv_msg_new;
    private LoadingView loadingView;
    private RollPagerView myViewPager1,myViewPager2;
    private NestedGridView grd_recommend;
    private RecommendGridAdapter gridAdapter;
    private XListView xListView;
    private RecommendTeacherAdapter mAdapter;
    private int currentPage = 1;
    private LocalBroadcastManager mLocalBroadcastManager;
    /**
     * 更新用户信息广播接受者
     */
    private BroadcastReceiver mUpdateUserInfoReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            getRecommendTeacher();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_TEACHER_LIST));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mUpdateUserInfoReceiver);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initChatMsg();
        getHomeAdvert();
        getHomeCategory();
        getRecommendTeacher();
        getActiveCourse();
    }

    private void initUI(View view) {
        String city = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CITY);
        txv_home_location = (TextView) view.findViewById(R.id.txv_home_location);
        txv_home_location.setText(Helper.isEmpty(city)?"选择城市":city);
        xListView = (XListView) view.findViewById(R.id.xListView);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        xListView.addHeaderView(getHeaderView());
        mAdapter = new RecommendTeacherAdapter(getActivity());
        xListView.setAdapter(mAdapter);
        imv_msg_new = (ImageView) view.findViewById(R.id.imv_msg_new);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
        txv_home_location.setOnClickListener(this);
        ((HomeActivity)getActivity()).setMyCityListener(this);
        view.findViewById(R.id.edt_home_search).setOnClickListener(this);
        view.findViewById(R.id.imv_home_msg).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initChatMsg();
    }

    private void initChatMsg(){
        if (CurrentUser.getInstance().born() && imv_msg_new != null
                && EMClient.getInstance().chatManager()!=null){
            int count =  EMClient.getInstance().chatManager().getUnreadMsgsCount();
            imv_msg_new.setVisibility(count>0?View.VISIBLE:View.GONE);
        }
    }

    private View getHeaderView(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_home, null);
        myViewPager1 = (RollPagerView) view.findViewById(R.id.myViewPager1);
        myViewPager2 = (RollPagerView) view.findViewById(R.id.myViewPager2);
        myViewPager1.setHintView(new ColorPointHintView(getActivity(), getActivity().getResources().getColor(R.color.base_orange),
                getActivity().getResources().getColor(R.color.base_orange_light)));
        myViewPager2.setHintView(new ColorPointHintView(getActivity(), getActivity().getResources().getColor(R.color.base_orange),
                getActivity().getResources().getColor(R.color.base_orange_light)));
        grd_recommend = (NestedGridView) view.findViewById(R.id.grd_recommend);
        view.findViewById(R.id.imv_one).setOnClickListener(this);
        view.findViewById(R.id.imv_together).setOnClickListener(this);
        return view;
    }

    private void finshLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }

    // Refresh
    @Override
    public void onRefresh() {
        currentPage = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finshLoad();
            }
        },1000);
    }

    // LoadMore
    @Override
    public void onLoadMore() {
        currentPage++;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.edt_home_search){
            NavigationHelper.startActivity(getActivity(), HomeSearchActivity.class,null,false);
        }else if (id == R.id.txv_home_location){
            Intent intent = new Intent(getActivity(), SelectCityActivity.class);
            startActivityForResult(intent, ProjectConstants.ActivityRequestCode.REQUEST_HOME_LOCATION);
        }else if (id == R.id.imv_home_msg){
            NavigationHelper.startActivity(getActivity(), MyConversationListActivity.class,null,false);
        }else if (id == R.id.imv_one){
            NavigationHelper.startActivity(getActivity(), ClassListActivity.class, null, false);
        }else if (id == R.id.imv_together){
            NavigationHelper.startActivity(getActivity(), ClassListActivity.class, null, false);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == ProjectConstants.ActivityRequestCode.REQUEST_HOME_LOCATION){
                if (data != null){
                    String city = data.getStringExtra(ProjectConstants.BundleExtra.KEY_CITY_NAME);
                    PreferencesHelper.getInstance().putString(ProjectConstants.Preferences.KEY_CITY, city);
                    txv_home_location.setText(city);
                }
            }
        }
    }

    /**
     * 获取首页广告
     */
    private void getHomeAdvert(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("position", 1);
        params.put("device", 0);
        post(UrlConstants.HOME_ADVERT_URL, params, 1);
    }

    /**
     * 获取首页分类
     */
    private void getHomeCategory(){
        HashMap<String, Object> params = new HashMap<>();
        post(UrlConstants.HOME_CATEGORY_URL, params, 2);
    }

    /**
     * 获取推荐老师
     */
    private void getRecommendTeacher(){
        HashMap<String, Object> params = new HashMap<>();
        post(UrlConstants.HOME_RECOMMEND_TEACHER, params, 3);
    }

    /**
     * 获取活动课堂
     */
    private void getActiveCourse(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("sort", 1);
        post(UrlConstants.HOME_ACTIVITY_URL, params, 4);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        ProgressDialogHelper.dismissProgressDialog();
        if(Utils.isEmpty(response)){
            ToastHelper.showToast("请求返回数据为空");
            return true;
        }
        int respType = Integer.parseInt(extras[0].toString());
        try {
            if(1 == respType){
                AdvertResp resp = JsonHelper.fromJson(response, AdvertResp.class);
                if(null != resp && Utils.notEmpty(resp.getData())){
                    myViewPager1.setAdapter(new HeadWheelAdapter(getActivity(), resp.getData()));
                }
            }else if(2 == respType){
                StuCategoryResp resp = JsonHelper.fromJson(response, StuCategoryResp.class);
                if(null != resp && "200".equals(resp.getCode())){
                    List<StuCategoryEntity> data = resp.getData();
                    gridAdapter = new RecommendGridAdapter(getActivity(), data);
                    grd_recommend.setAdapter(gridAdapter);
                }
            }else if(3 == respType){
                RecommendTeacherResp resp = JsonHelper.fromJson(response, RecommendTeacherResp.class);
                if (Helper.isNotEmpty(resp.getData())){
                    mAdapter.addMore(resp.getData());
                }else{
                    loadingView.postLoadState(LoadingView.State.LOADING_EMPTY);
                    xListView.setPullLoadEnable(false);
                }
            }else if(4 == respType){
                ActivityHomeResp resp = JsonHelper.fromJson(response, ActivityHomeResp.class);
                if (Helper.isNotEmpty(resp) && Helper.isNotEmpty(resp.getData())){
                    myViewPager2.setAdapter(new RecommendClassAdapter(getActivity(), resp.getData()));
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
        int respType = Integer.parseInt(extras[0].toString());
        if (respType == 3){
            loadingView.postLoadState(LoadingView.State.LOADING_FALIED);
        }
        return true;
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

    @Override
    public void onCityGet(String city) {
        String cityStr = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CITY);
        if (Helper.isEmpty(cityStr) || "选择城市".equals(cityStr)){
            if (txv_home_location!=null){
                txv_home_location.setText(Helper.isEmpty(city)?"选择城市":city);
            }
            PreferencesHelper.getInstance().putString(ProjectConstants.Preferences.KEY_CITY, city);
        }
    }
}
