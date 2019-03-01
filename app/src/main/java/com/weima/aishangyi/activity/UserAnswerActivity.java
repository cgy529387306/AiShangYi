package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.AnswerInoutResp;
import com.weima.aishangyi.entity.BalanceResp;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;

/**
 * 我的问答
 */
public class UserAnswerActivity extends BaseActivity {
    private TextView txv_income,txv_expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("问答主页");
        setContentView(R.layout.activity_user_answer);
        initUI();
        requestData();
    }


    private void initUI() {
        txv_income = findView(R.id.txv_income);//总收入
        txv_expenses = findView(R.id.txv_expenses);//总支出
        findView(R.id.btn_paymentdetail).setOnClickListener(mClickListener);
        findView(R.id.btn_question).setOnClickListener(mClickListener);
        findView(R.id.btn_onlookers).setOnClickListener(mClickListener);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        AnswerInoutResp entity = JsonHelper.fromJson(response, AnswerInoutResp.class);
        if ("200".equals(entity.getCode())){
            if (Helper.isNotEmpty(entity.getData())){
                txv_income.setText("¥"+entity.getData().getIn());
                txv_expenses.setText("¥"+entity.getData().getOut());
            }
        }else{
            ToastHelper.showToast(entity.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        return true;
    }


    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_paymentdetail://收支明细
                    NavigationHelper.startActivity(UserAnswerActivity.this, UserPaymentDetailActivity.class, null, false);
                    break;

                case R.id.btn_question://我的提问
                    NavigationHelper.startActivity(UserAnswerActivity.this, UserQuestionActivity.class, null, false);
                    break;

                case R.id.btn_onlookers://我的围观
                    NavigationHelper.startActivity(UserAnswerActivity.this, UserAnswerSeeActivity.class, null, false);
                    break;
                default:
                    break;
            }
        }
    };

    private void requestData(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        post(ProjectConstants.Url.CASH_ANWSER_INOUT, requestMap);
    }


}
