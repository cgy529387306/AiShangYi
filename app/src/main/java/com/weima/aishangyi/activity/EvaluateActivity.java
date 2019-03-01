package com.weima.aishangyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ClassOrderBean;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.TeacherBean;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;

/**
 * 课程订单评价
 *
 * @author cgy
 */
public class EvaluateActivity extends BaseActivity {
    private EditText mInputView;
    private RatingBar star;
    private ImageView imv_teacher_avater;
    private TextView txv_teacher_name,txv_teacher_goodat;
    private ClassOrderBean orderBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("评价");
        setContentView(R.layout.activity_evaluate);
        initUI();
        initData();
    }

    private void initUI() {
        orderBean = (ClassOrderBean) getIntent().getSerializableExtra("order");
        star = findView(R.id.star);
        imv_teacher_avater = findView(R.id.imv_teacher_avater);
        txv_teacher_name = findView(R.id.txv_teacher_name);
        txv_teacher_goodat = findView(R.id.txv_teacher_goodat);
        mInputView = findView(R.id.add_text_input);
        findView(R.id.btn_comfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doEvaluate();
            }
        });
    }

    private void initData(){
        if (Helper.isNotEmpty(orderBean) && Helper.isNotEmpty(orderBean.getTeacher())){
            if (Helper.isNotEmpty(orderBean.getTeacher().getIcon())){
                Picasso.with(EvaluateActivity.this).load(orderBean.getTeacher().getIcon()).placeholder(R.drawable.img_default).into(imv_teacher_avater);
            }
            txv_teacher_name.setText(ProjectHelper.getCommonText(orderBean.getTeacher().getNickname()));
            txv_teacher_goodat.setText(ProjectHelper.getCommonText(orderBean.getTeacher().getInterest()));
        }
    }

    private void doEvaluate() {
        String content = mInputView.getText().toString();
        if (Helper.isEmpty(content)){
            ToastHelper.showToast("请分享你的上课心得...");
             return;
        }
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("order_id", orderBean.getId());
        requestMap.put("appraise",content);
        requestMap.put("star",star.getRating());
        post(ProjectConstants.Url.ORDER_LESSON_EVALUATE, requestMap);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        if (super.onResponseSuccess(gact, response, extras)) {
            return true;
        }
        ProgressDialogHelper.dismissProgressDialog();
        CommonEntity entity = JsonHelper.fromJson(response, CommonEntity.class);
		if (Helper.isNotEmpty(entity)) {
			if ("200".equals(entity.getCode())) {
                LocalBroadcastManager.getInstance(EvaluateActivity.this).sendBroadcast(new Intent("classOrder"));
                ToastHelper.showToast("评价成功！");
				finish();
			}else{
                ToastHelper.showToast(entity.getMessage());
            }
		}
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ProgressDialogHelper.dismissProgressDialog();
        ToastHelper.showToast("请求失败");
        return true;
    }

}
