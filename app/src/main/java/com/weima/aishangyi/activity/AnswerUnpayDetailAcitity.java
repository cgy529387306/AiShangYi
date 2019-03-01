package com.weima.aishangyi.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.AnswerBean;
import com.weima.aishangyi.entity.CommonDataResp;
import com.weima.aishangyi.entity.QuestionBean;
import com.weima.aishangyi.entity.User;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

/**
 * 作者：cgy on 16/11/28 22:02
 * 邮箱：593960111@qq.com
 * 问题详情
 */
public class AnswerUnpayDetailAcitity extends BaseActivity implements View.OnClickListener{
    private QuestionBean questionBean;
    private ImageView imv_user_avater,imv_teacher_avater;
    private TextView txv_user_name,txv_create_time,txv_money;
    private TextView txv_problem,txv_qustion_count;
    private TextView txv_teacher_name,txv_teacher_goodat,txv_see_cost;
    private TextView txv_words_count,txv_good_count,txv_see_count;
    private double seeCost = 0.5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_unpay_detail);
        setCustomTitle("问题");
        initUI();
        initData();
        initCommonData();
    }

    private void initUI() {
        imv_teacher_avater = findView(R.id.imv_teacher_avater);
        imv_user_avater = findView(R.id.imv_user_avater);
        txv_user_name = findView(R.id.txv_user_name);
        txv_create_time = findView(R.id.txv_create_time);
        txv_money = findView(R.id.txv_money);
        txv_problem = findView(R.id.txv_problem);
        txv_qustion_count = findView(R.id.txv_qustion_count);

        txv_teacher_name = findView(R.id.txv_teacher_name);
        txv_teacher_goodat = findView(R.id.txv_teacher_goodat);
        txv_see_cost = findView(R.id.txv_see_cost);
        txv_words_count = findView(R.id.txv_words_count);
        txv_good_count = findView(R.id.txv_good_count);
        txv_see_count = findView(R.id.txv_see_count);
        txv_see_cost.setOnClickListener(this);
    }


    private void initData(){
        questionBean = (QuestionBean) getIntent().getSerializableExtra("question");
        if (Helper.isNotEmpty(questionBean)){
            txv_problem.setText(ProjectHelper.getCommonText(questionBean.getProblem()));
            txv_money.setText(questionBean.getPrice() + "");
            txv_create_time.setText(ProjectHelper.formatLongTime(questionBean.getCreated_at()));
            txv_qustion_count.setText("共" + questionBean.getAnswer_count() + "人参与回答");
            txv_words_count.setText(questionBean.getAnswer_count()+"");
            txv_good_count.setText(questionBean.getThumb_count()+"");
            txv_see_count.setText(questionBean.getSee_count() + "");
            if (Helper.isEmpty(questionBean.getIs_thumb())){
                Drawable drawable= getResources().getDrawable(R.drawable.ic_answer_good);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                txv_good_count.setCompoundDrawables(drawable, null, null, null);
            }else{
                Drawable drawable= getResources().getDrawable(R.drawable.ic_answer_good_press);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                txv_good_count.setCompoundDrawables(drawable, null, null, null);
            }

            if (Helper.isNotEmpty(questionBean.getUser())){
                User user = questionBean.getUser();
                txv_user_name.setText(ProjectHelper.getCommonText(user.getNickname()));
                if (Helper.isNotEmpty(user.getIcon())){
                    Picasso.with(AnswerUnpayDetailAcitity.this).load(user.getIcon()).placeholder(R.drawable.ic_avatar_default).into(imv_user_avater);
                }
            }

            if (Helper.isNotEmpty(questionBean.getBest())){
                if (Helper.isNotEmpty(Helper.isNotEmpty(questionBean.getBest().getTeacher()))){
                    AnswerBean.TeacherBean teacherBean = questionBean.getBest().getTeacher();
                    txv_teacher_name.setText(ProjectHelper.getCommonText(teacherBean.getNickname()));
                    txv_teacher_goodat.setText(Helper.isEmpty(teacherBean.getGood_at())?"听说该老师是全能型的哦":teacherBean.getGood_at());
                    if (Helper.isNotEmpty(teacherBean.getIcon())){
                        Picasso.with(AnswerUnpayDetailAcitity.this).load(teacherBean.getIcon()).placeholder(R.drawable.ic_avatar_default).into(imv_teacher_avater);
                    }
                }
            }
        }
    }

    private void initCommonData(){
        try {
            String response = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_COMMON_DATA);
            if (Helper.isNotEmpty(response)){
                CommonDataResp entity = JsonHelper.fromJson(response, CommonDataResp.class);
                if ("200".equals(entity.getCode())) {
                    if (Helper.isNotEmpty(entity.getData())){
                        seeCost = Double.parseDouble(entity.getData().getPrice());
                        txv_see_cost.setText("¥"+seeCost+"偷偷看");
                    }
                } else {
                    ToastHelper.showToast(entity.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.txv_see_cost){
            Bundle bundle = new Bundle();
            bundle.putInt("type",4);
            bundle.putLong("question_id",questionBean.getId());
            bundle.putDouble("price", seeCost);
            NavigationHelper.startActivity(AnswerUnpayDetailAcitity.this, OrderPayActivity.class, bundle, true);
        }
    }
}
