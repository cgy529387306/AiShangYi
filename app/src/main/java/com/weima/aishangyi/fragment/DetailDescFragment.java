package com.weima.aishangyi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.weima.aishangyi.adapter.PlayAdapter;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.utils.NavigationHelper;

/**
 * 教师简介
 * Created by cgy on 16/7/18.
 */
public class DetailDescFragment extends Fragment {
    private ListView play_list;
    private PlayAdapter playAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_detail_desc, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
        play_list = (ListView) view.findViewById(R.id.play_list);
        playAdapter = new PlayAdapter(getActivity());
        play_list.setAdapter(playAdapter);
    }

}
