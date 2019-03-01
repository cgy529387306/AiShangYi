package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.entity.ActivityOrderBean;
import com.weima.aishangyi.entity.ClassOrderBean;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.ProjectHelper;

/**
 * 作者：cgy on 16/11/28 22:02
 * 邮箱：593960111@qq.com
 * 课程订单详情
 */
public class ActvityOrderDetailAcitity extends BaseActivity implements ResponseListener {
    private ImageView imv_icon;
    private LinearLayout lin_paytime;
    private TextView txv_title,txv_content,txv_time,txv_status,txv_address;
    private TextView txv_price,txv_number,txv_all,txv_coupons,txv_real;
    private TextView txv_orderno,txv_ordertime,txv_paytime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityorder_detail);
        setCustomTitle("订单详情");
        initUI();
        initData();
    }


    private void initUI() {
        lin_paytime = findView(R.id.lin_paytime);
        imv_icon = findView(R.id.imv_icon);
        txv_title = findView(R.id.txv_title);
        txv_content = findView(R.id.txv_content);
        txv_time = findView(R.id.txv_time);
        txv_status = findView(R.id.txv_status);
        txv_price = findView(R.id.txv_price);
        txv_number = findView(R.id.txv_number);
        txv_all = findView(R.id.txv_all);
        txv_coupons = findView(R.id.txv_coupons);
        txv_real = findView(R.id.txv_real);
        txv_orderno = findView(R.id.txv_orderno);
        txv_ordertime = findView(R.id.txv_ordertime);
        txv_paytime = findView(R.id.txv_paytime);
        txv_address = findView(R.id.txv_address);
    }

    private void initData() {
        ActivityOrderBean entity = (ActivityOrderBean) getIntent().getSerializableExtra("order");
        if (Helper.isNotEmpty(entity)){
            if (Helper.isNotEmpty(entity.getActive_order()) && Helper.isNotEmpty(entity.getActive_order().getActive())){
                ActivityOrderBean.ActiveOrderBean.ActiveBean activeBean = entity.getActive_order().getActive();
                if (Helper.isNotEmpty(activeBean.getImages())){
                    Picasso.with(ActvityOrderDetailAcitity.this).load(activeBean.getImages().get(0)).placeholder(R.drawable.img_default).into(imv_icon);
                }
                txv_title.setText(ProjectHelper.getCommonText(activeBean.getTitle()));
                txv_content.setText(Helper.isEmpty(activeBean.getContent())?"": Html.fromHtml(activeBean.getContent()));
                txv_time.setText("活动时间："+Helper.long2DateString(activeBean.getStart_time() * 1000, "yyyy-MM-dd HH:mm"));
                txv_address.setText(ProjectHelper.getCommonText(activeBean.getAddress()));
            }
            txv_status.setText(ProjectHelper.getOrderStatus(entity.getStatus()));
            txv_price.setText("¥"+entity.getPrice());
            txv_all.setText("¥"+entity.getAmount());
            txv_coupons.setText("¥"+entity.getDiscount());
            double real = Double.parseDouble(entity.getAmount())-Double.parseDouble(entity.getDiscount());
            txv_real.setText("¥"+real);
            txv_number.setText(entity.getNumber()+"人次");
            txv_orderno.setText(ProjectHelper.getCommonText(entity.getOrder_no()));
            if (Helper.isNotEmpty(entity.getCreated_at())){
                String createTime = Helper.long2DateString(Long.parseLong(entity.getCreated_at()) * 1000, "yyyy-MM-dd HH:mm");
                txv_ordertime.setText(createTime);
            }
            if (Helper.isNotEmpty(entity.getPay_time())){
                lin_paytime.setVisibility(View.VISIBLE);
                String payTime = Helper.long2DateString(entity.getPay_time()* 1000, "yyyy-MM-dd HH:mm");
                txv_paytime.setText(payTime);
            }else{
                lin_paytime.setVisibility(View.GONE);
            }
       }
    }
}
