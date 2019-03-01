package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.MemberTypeAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.MemberBean;
import com.weima.aishangyi.entity.MemberResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;

/**
 * 购买会员
 */
public class BuyMemberActivity extends BaseActivity {
    private TextView txv_status,txv_detail;
    private ListView listView;
    private MemberTypeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("会员充值");
        setContentView(R.layout.activity_buy_member);
        initUI();
        requestMember();
    }


    private void initUI() {
        adapter = new MemberTypeAdapter(this);
        txv_status = findView(R.id.txv_status);
        txv_detail = findView(R.id.txv_detail);
        listView = findView(R.id.listView);
        listView.setAdapter(adapter);
        findView(R.id.btn_comfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = adapter.getSelectIndex();
                if (adapter.getCount()>index){
                    MemberBean.ContentBean contentBean = adapter.getItem(index);
                    if (Helper.isNotEmpty(contentBean)){
                        Bundle bundle = new Bundle();
                        bundle.putInt("type",6);
                        bundle.putDouble("amount", contentBean.getMoney());
                        bundle.putLong("member_id", contentBean.getId());
                        NavigationHelper.startActivity(BuyMemberActivity.this, OrderPayActivity.class, bundle, true);
                    }
                }
            }
        });
    }

    private void initData(){
        if (Helper.isNotEmpty(MemberBean.getInstance())){
            MemberBean memberBean = MemberBean.getInstance();
            if (memberBean.getIs_member()==1){
                txv_status.setText("您当前会员状态：会员");
                String detail = "剩余课时数:"+memberBean.getNumber()+"  有效期:"+memberBean.getDate_line();
                txv_detail.setText(detail);
            }else{
                txv_status.setText("您当前会员状态：非会员");
                txv_detail.setText("赶紧成为爱尚艺会员，与小伙伴一起开心学艺术吧！");
            }
            if (Helper.isNotEmpty(memberBean.getContent())){
                adapter.addMore(memberBean.getContent());
            }
        }
    }

    private void requestMember(){
        final RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(ProjectConstants.Url.USER_MEMBER)
                .setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(new HashMap<String,Object>()).getRequestEntity();
        RequestHelper.post(entity, new ResponseListener() {
            @Override
            public boolean onResponseSuccess(int gact, String response, Object... extras) {
                try {
                    if (Helper.isNotEmpty(response)){
                        MemberResp entity = JsonHelper.fromJson(response,MemberResp.class);
                        if (Helper.isNotEmpty(entity) && "200".equals(entity.getCode())
                                && Helper.isNotEmpty(entity.getData())){
                            MemberBean.getInstance().born(entity.getData());
                            initData();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
                return false;
            }
        });
    }



}
