package com.weima.aishangyi.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.entity.MsgBean;
import com.weima.aishangyi.fragment.MsgOrderFragment;
import com.weima.aishangyi.fragment.MsgSystemFragment;
import com.weima.aishangyi.tabstrip.PagerSlidingTabStrip;
import com.weima.aishangyi.utils.ProjectHelper;

/**
 * 系统消息
 */
public class MsgDetailActivity extends BaseActivity {
    private TextView txv_title,txv_time,txv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        setCustomTitle("消息详情");
        initUI();
        initData();
    }


    private void initUI() {
        txv_title = findView(R.id.txv_title);
        txv_time = findView(R.id.txv_time);
        txv_content = findView(R.id.txv_content);
    }

    private void initData(){
        MsgBean msgBean = (MsgBean) getIntent().getSerializableExtra("msg");
        if(Helper.isNotEmpty(msgBean)){
            txv_title.setText(ProjectHelper.getCommonText(msgBean.getTitle()));
            txv_time.setText(Helper.long2DateString(msgBean.getCreated_at() * 1000, "yyyy-MM-dd"));
            txv_content.setText(ProjectHelper.getCommonText(msgBean.getContent()));
        }
    }


}
