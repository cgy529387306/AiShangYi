package com.weima.aishangyi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.UserActivityOrderActivity;
import com.weima.aishangyi.activity.UserAnswerActivity;
import com.weima.aishangyi.activity.UserClassroomOrderActivity;
import com.weima.aishangyi.activity.UserCollectActivity;
import com.weima.aishangyi.activity.UserContactActivity;
import com.weima.aishangyi.activity.UserCouponsActivity;
import com.weima.aishangyi.activity.UserInfoActivity;
import com.weima.aishangyi.activity.UserInviteActivity;
import com.weima.aishangyi.activity.UserSettingActivity;
import com.weima.aishangyi.activity.UserTimetableActivity;
import com.weima.aishangyi.adapter.HeadWheelAdapter;
import com.weima.aishangyi.adapter.RecommendClassAdapter;
import com.weima.aishangyi.adapter.RecommendGridAdapter;
import com.weima.aishangyi.adapter.TalentCircleAdapter;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.tabstrip.PagerSlidingTabStrip;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.widget.NestedGridView;
import com.weima.aishangyi.xlistview.XListView;

/**
 * 教师简介
 * Created by cgy on 16/7/18.
 */
public class DetailCommentFragment extends Fragment implements XListView.IXListViewListener{

    private XListView xListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_detail_comment, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initData();
    }

    private void initUI(View view) {
        xListView = (XListView) view.findViewById(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        xListView.addHeaderView(getHeaderView());
        xListView.setAdapter(new TalentCircleAdapter(getActivity()));
    }

    private View getHeaderView(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_comment, null);
        return view;
    }


    private void initData() {
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    break;
            }
        }
    };
}
