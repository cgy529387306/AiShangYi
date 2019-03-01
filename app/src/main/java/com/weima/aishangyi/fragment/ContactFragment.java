package com.weima.aishangyi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.ContactAdapter;
import com.weima.aishangyi.adapter.QuestionUnanswerAdapter;
import com.weima.aishangyi.entity.LunboEntity;
import com.weima.aishangyi.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人
 */
public class ContactFragment extends Fragment implements XListView.IXListViewListener {
    private XListView xListView;
    private LinearLayout emptyView;
    private LoadingView loadingView;
    private int currentPage = 1;
    private int type = 0;
    private static final String TYPE = "TYPE";

    public static ContactFragment newInstance(int type) { //对外提供创建实例的方法，你给我需要显示的内容，我给你Fragment实例
        ContactFragment fragment = new ContactFragment();
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
    }

    private void initUI(View view) {
        xListView = (XListView) view.findViewById(R.id.xListView);
        emptyView = (LinearLayout) view.findViewById(R.id.emptyview);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
//        xListView.setAdapter(new ContactAdapter(getActivity(), getTestData()));

    }

    private List<LunboEntity> getTestData() {
        List<LunboEntity> list = new ArrayList<>();
        LunboEntity entity1 = new LunboEntity();
        entity1.setTitle("陈秋梅");
        list.add(entity1);

        LunboEntity entity2 = new LunboEntity();
        entity2.setTitle("蔡桂有");
        list.add(entity2);

        LunboEntity entity3 = new LunboEntity();
        entity3.setTitle("陈秋婷");
        list.add(entity3);

        LunboEntity entity4 = new LunboEntity();
        entity4.setTitle("陈秋敏");
        list.add(entity4);

        return list;
    }

    private void finshLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }


    @Override
    public void onRefresh() {
        currentPage = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finshLoad();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        currentPage++;
    }
}
