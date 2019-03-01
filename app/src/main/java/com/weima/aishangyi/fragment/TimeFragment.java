package com.weima.aishangyi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mb.android.utils.Helper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.TimePickActivity;
import com.weima.aishangyi.adapter.TimeAdapter;
import com.weima.aishangyi.entity.TimeBean;
import com.weima.aishangyi.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间表
 */
public class TimeFragment extends Fragment {
    private XListView xListView;
    private static final String TYPE = "TYPE";
    private int type;
    private TimeAdapter adapter;
    public static TimeFragment newInstance(int type) { //对外提供创建实例的方法，你给我需要显示的内容，我给你Fragment实例
        TimeFragment fragment = new TimeFragment();
        Bundle b = new Bundle();
        b.putInt(TYPE, type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_listview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initData();
    }

    private void initUI(View view) {
        adapter = new TimeAdapter(getActivity());
        xListView = (XListView) view.findViewById(R.id.xListView);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(false);
    }

    private void initData(){
        List<TimeBean> dataList = new ArrayList<>();
        List<TimeBean> list = ((TimePickActivity)getActivity()).getTimeList();
        if (Helper.isNotEmpty(list)){
            for (int i=0; i<list.size(); i++){
                TimeBean timeBean = list.get(i);
                if (i%7==type && Helper.isNotEmpty(timeBean.getStart_time())
                        && Helper.isNotEmpty(timeBean.getEnd_time()) && timeBean.getStatus()==1){
                    dataList.add(timeBean);
                }
            }
            adapter.addMore(dataList);
        }
    }

    public TimeBean getTimeSelect(){
        TimeBean timeBean = null;
        if (adapter.getCount() != 0 && adapter.getSelectIndex()<adapter.getCount()){
            timeBean = adapter.getItem(adapter.getSelectIndex());
        }else{
            ToastHelper.showToast("请选择预约时间");
        }
        return timeBean;
    }

}
