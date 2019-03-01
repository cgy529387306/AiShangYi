package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.BaseTextAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.db.SearchHistoryDao;
import com.weima.aishangyi.pop.SelectSearchTypePop;
import com.weima.aishangyi.pop.SelectSexPop;
import com.weima.aishangyi.utils.NavigationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：cgy on 16/11/24 22:01
 * 邮箱：593960111@qq.com
 * 搜索
 */
public class HomeSearchActivity extends BaseActivity implements View.OnClickListener{
    private ListView listView;
    private EditText edt_home_search;
    private TextView txv_select_type;
    private SearchHistoryDao searchHistoryDao;
    private LinearLayout actionbar;
    private int currentType = 1;
    private SelectSearchTypePop selectSearchTypePop;
    private List<String> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_search);
        initUI();
        fillListData();
    }

    private void initUI() {
        searchHistoryDao = new SearchHistoryDao();
        actionbar = findView(R.id.actionbar);
        edt_home_search = findView(R.id.edt_home_search);
        txv_select_type = findView(R.id.txv_select_type);
        listView = findView(R.id.listView);
        listView.addHeaderView(getHeaderView());
        listView.addFooterView(getFooterView());
        txv_select_type.setOnClickListener(this);
        findView(R.id.lin_actionbar_back).setOnClickListener(this);
        findView(R.id.txv_search).setOnClickListener(this);
        selectSearchTypePop = new SelectSearchTypePop(HomeSearchActivity.this, new SelectSearchTypePop.SelectListener() {
            @Override
            public void onSelected(int type) {
                currentType = type;
                if (type == 1){
                    txv_select_type.setText("机构");
                    edt_home_search.setHint("搜索机构");
                }else if (type == 2) {
                    txv_select_type.setText("老师");
                    edt_home_search.setHint("搜索老师");
                }else if (type == 3) {
                    txv_select_type.setText("课程");
                    edt_home_search.setHint("搜索课程");
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - listView.getHeaderViewsCount();
                if (Helper.isNotEmpty(dataList) && dataList.size()>index){
                    Bundle bundle = new Bundle();
                    bundle.putString(ProjectConstants.BundleExtra.KEY_KEYWORD,dataList.get(index));
                    if (currentType == 1){
                        NavigationHelper.startActivity(HomeSearchActivity.this,OrgSearchActivity.class,bundle,false);
                    }else if(currentType == 2){
                        NavigationHelper.startActivity(HomeSearchActivity.this,TeacherSearchActivity.class,bundle,false);
                    }else if(currentType == 3){
                        NavigationHelper.startActivity(HomeSearchActivity.this,ClassSearchActivity.class,bundle,false);
                    }
                }
            }
        });
    }

    private void fillListData(){
        dataList = searchHistoryDao.getList();
        if (Helper.isNotEmpty(dataList)){
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new BaseTextAdapter(HomeSearchActivity.this,dataList));
        }else{
            listView.setVisibility(View.GONE);
        }
    }


    private View getHeaderView(){
        View view = LayoutInflater.from(HomeSearchActivity.this).inflate(R.layout.header_home_search, null);
        return view;
    }

    private View getFooterView(){
        View view = LayoutInflater.from(HomeSearchActivity.this).inflate(R.layout.footer_home_search, null);
        view.findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchHistoryDao.clear();
                fillListData();
            }
        });
        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lin_actionbar_back){
            finish();
        }else if (id == R.id.txv_search){
            if (Helper.isNotEmpty(edt_home_search.getText().toString())){
                searchHistoryDao.add(edt_home_search.getText().toString());
                fillListData();
                Bundle bundle = new Bundle();
                bundle.putString(ProjectConstants.BundleExtra.KEY_KEYWORD,edt_home_search.getText().toString());
                if (currentType == 1){
                    NavigationHelper.startActivity(HomeSearchActivity.this,OrgSearchActivity.class,bundle,false);
                }else if(currentType == 2){
                    NavigationHelper.startActivity(HomeSearchActivity.this,TeacherSearchActivity.class,bundle,false);
                }else if(currentType == 3){
                    NavigationHelper.startActivity(HomeSearchActivity.this,ClassSearchActivity.class,bundle,false);
                }
            }else{
                ToastHelper.showToast("请输入搜索的内容");
            }
        }else if (id == R.id.txv_select_type){
            selectSearchTypePop.show(actionbar);
        }
    }
}
