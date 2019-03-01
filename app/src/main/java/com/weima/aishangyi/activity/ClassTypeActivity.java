package com.weima.aishangyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.ClassFirstAdapter;
import com.weima.aishangyi.adapter.ClassSecondAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ClassTypeResp;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程分类
 *
 * @author cgy
 */
public class ClassTypeActivity extends BaseActivity {
    private ListView listFirst;
    private GridView listSecond;
    private List<ClassTypeResp.DataBean> firstBeans = new ArrayList<>();
    private List<ClassTypeResp.DataBean.ChildrenBean> secondBeans = new ArrayList<>();
    private ClassFirstAdapter firstAdapter;
    private ClassSecondAdapter secondAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("课程分类");
        setContentView(R.layout.activity_class_type);
        initUI();
        initData();
    }

    private void initUI() {
        listFirst = findView(R.id.listFirst);
        listSecond = findView(R.id.listSecond);

        firstAdapter = new ClassFirstAdapter(ClassTypeActivity.this, firstBeans);
        listFirst.setAdapter(firstAdapter);
        secondAdapter = new ClassSecondAdapter(ClassTypeActivity.this, secondBeans);
        listSecond.setAdapter(secondAdapter);

        listFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - listFirst.getHeaderViewsCount();
                firstAdapter.setTempIndex(index);
                ClassTypeResp.DataBean dataBean = firstBeans.get(index);
                secondBeans = dataBean.getChildren();
                secondAdapter.updateList(secondBeans);
            }
        });

        listSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassTypeResp.DataBean.ChildrenBean childrenBean = secondAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(ProjectConstants.BundleExtra.KEY_CLASS_TYPE_ID, childrenBean);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initData(){
       try {
           String response = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CLASS_TYPE);
           if (Helper.isNotEmpty(response)){
               ClassTypeResp entity = JsonHelper.fromJson(response, ClassTypeResp.class);
               if ("200".equals(entity.getCode())) {
                   if (Helper.isNotEmpty(entity.getData())){
                       firstBeans = entity.getData();
                       firstAdapter.updateList(firstBeans);
                       if (Helper.isNotEmpty(entity.getData().get(0).getChildren())){
                           secondAdapter.updateList(entity.getData().get(0).getChildren());
                       }
                   }
               } else {
                   ToastHelper.showToast(entity.getMessage());
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }





}
