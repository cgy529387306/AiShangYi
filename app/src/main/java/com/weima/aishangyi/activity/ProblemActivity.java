package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.ProblemAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ArticleBean;
import com.weima.aishangyi.entity.ArticleResp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 常见问题
 *
 * @author cgy
 */
public class ProblemActivity extends BaseActivity {
    private ListView listView;
    private List<ArticleBean> list = new ArrayList<>();
    private ProblemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("常见问题");
        setContentView(R.layout.activity_problem);
        initUI();
        requestData();
    }

    private void initUI() {
        listView = findView(R.id.listView);
        adapter = new ProblemAdapter(ProblemActivity.this,list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        try {
            ArticleResp entity = JsonHelper.fromJson(response, ArticleResp.class);
            if ("200".equals(entity.getCode())){
                if (Helper.isNotEmpty(entity.getData())){
                    if (Helper.isNotEmpty(entity.getData().getData())){
                        adapter.updataList(entity.getData().getData());
                    }
                }
            }else{
                ToastHelper.showToast(entity.getMessage());
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
        return true;
    }

    private void requestData(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("type",2);
        post(ProjectConstants.Url.ARTICLE, requestMap);
    }

}
