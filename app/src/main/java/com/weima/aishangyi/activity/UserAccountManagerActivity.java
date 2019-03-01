package com.weima.aishangyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;

/**
 * 账号管理
 */
public class UserAccountManagerActivity extends BaseActivity {
    private EditText edit_zhifubaousername, edit_zhifubaoaccount, edit_cardusername, edit_cardaccount, edit_cardname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("账号管理");
        setContentView(R.layout.activity_user_accountmanager);
        initUI();
        initData();
    }


    private void initUI() {
        findView(R.id.btn_save).setOnClickListener(mClickListener);
        edit_zhifubaousername = findView(R.id.edit_zhifubaousername);//支付宝 姓名
        edit_zhifubaoaccount = findView(R.id.edit_zhifubaoaccount);//支付宝账号
        edit_cardusername = findView(R.id.edit_cardusername);//银行卡 姓名
        edit_cardaccount = findView(R.id.edit_cardaccount);// 银行卡号
        edit_cardname = findView(R.id.edit_cardname);// 银行名称
    }

    private void initData(){
        CurrentUser currentUser = CurrentUser.getInstance();
        if (Helper.isNotEmpty(currentUser)){
            edit_zhifubaousername.setText(ProjectHelper.getCommonText(currentUser.getUsername()));
            edit_zhifubaousername.setSelection(ProjectHelper.getCommonSeletion(currentUser.getUsername()));
            edit_zhifubaoaccount.setText(ProjectHelper.getCommonText(currentUser.getAlipay()));
            edit_zhifubaoaccount.setSelection(ProjectHelper.getCommonSeletion(currentUser.getAlipay()));
            edit_cardusername.setText(ProjectHelper.getCommonText(currentUser.getBank_user()));
            edit_cardusername.setSelection(ProjectHelper.getCommonSeletion(currentUser.getBank_user()));
            edit_cardaccount.setText(ProjectHelper.getCommonText(currentUser.getBank_card()));
            edit_cardaccount.setSelection(ProjectHelper.getCommonSeletion(currentUser.getBank_card()));
            edit_cardname.setText(ProjectHelper.getCommonText(currentUser.getBank_name()));
            edit_cardname.setSelection(ProjectHelper.getCommonSeletion(currentUser.getBank_name()));
        }
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        ProgressDialogHelper.dismissProgressDialog();
        CommonEntity entity = JsonHelper.fromJson(response, CommonEntity.class);
        if ("200".equals(entity.getCode())){
            LocalBroadcastManager.getInstance(UserAccountManagerActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_USER_INFO));
            ToastHelper.showToast("保存成功");
            finish();
        }else{
            ToastHelper.showToast(entity.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ProgressDialogHelper.dismissProgressDialog();
        return true;
    }



    private void doSave() {
        String aliOwnerName = edit_zhifubaousername.getText().toString().trim();
        String aliAccount = edit_zhifubaoaccount.getText().toString().trim();
        String cardOwnerName = edit_cardusername.getText().toString().trim();
        String cardAccount = edit_cardaccount.getText().toString().trim();
        String cardBankName = edit_cardname.getText().toString().trim();

        if (Helper.isEmpty(aliOwnerName)) {
            ToastHelper.showToast("请输入支付宝所有人姓名");
            return;
        }else if (Helper.isEmpty(aliAccount)) {
            ToastHelper.showToast("请输入支付宝账号");
            return;
        }else if (Helper.isEmpty(cardOwnerName)) {
            ToastHelper.showToast("请输入银行卡持卡人");
            return;
        }else if (Helper.isEmpty(cardAccount)) {
            ToastHelper.showToast("请输入银行卡卡号");
            return;
        }else if (Helper.isEmpty(cardBankName)) {
            ToastHelper.showToast("请输入银行卡银行名称");
            return;
        }
        ProgressDialogHelper.showProgressDialog(this, "加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("username", aliOwnerName);
        requestMap.put("alipay", aliAccount);
        requestMap.put("bank_user",cardOwnerName);
        requestMap.put("bank_card",cardAccount);
        requestMap.put("bank_name", cardBankName);
        post(ProjectConstants.Url.ACCOUNT_BILLEDIT, requestMap);
    }

    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save://保存
                    ProjectHelper.disableViewDoubleClick(v);
                    doSave();
                    break;
                default:
                    break;
            }
        }
    };


}
