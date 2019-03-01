package com.weima.aishangyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ClassTypeResp;
import com.weima.aishangyi.entity.CommonDataResp;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.pop.SelectAnswerTypePop;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：cgy on 16/11/28 22:02
 * 邮箱：593960111@qq.com
 * 提问
 */
public class AnswerQuestionAcitity extends BaseActivity implements View.OnClickListener{
    private TextView btn_select_type,txv_see_cost;
    private EditText edt_question_desc,edt_question_cost;
    private ImageView imv_checkbox;
    private long classType = 0;
    private boolean is_public = true;
    private SelectAnswerTypePop selectAnswerTypePop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        setCustomTitle("提问");
        initUI();
        initType();
        initCommonData();
    }

    private void initUI() {
        btn_select_type = findView(R.id.btn_select_type);
        edt_question_desc = findView(R.id.edt_question_desc);
        edt_question_cost = findView(R.id.edt_question_cost);
        imv_checkbox = findView(R.id.imv_checkbox);
        txv_see_cost = findView(R.id.txv_see_cost);
        btn_select_type.setOnClickListener(this);
//        imv_checkbox.setOnClickListener(this);
        findView(R.id.btn_comfirm).setOnClickListener(this);
    }

    private void initType(){
        try {
            List<ClassTypeResp.DataBean> classTypeList = null;
            String response = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CLASS_TYPE);
            if (Helper.isNotEmpty(response)){
                ClassTypeResp entity = JsonHelper.fromJson(response, ClassTypeResp.class);
                if ("200".equals(entity.getCode())) {
                    if (Helper.isNotEmpty(entity.getData())){
                        classTypeList = entity.getData();
                        classType = classTypeList.get(0).getId();
                        btn_select_type.setText(ProjectHelper.getCommonText(classTypeList.get(0).getName()));
                        selectAnswerTypePop = new SelectAnswerTypePop(AnswerQuestionAcitity.this, classTypeList, new SelectAnswerTypePop.SelectListener() {
                            @Override
                            public void onSelected(ClassTypeResp.DataBean typeData) {
                                if (Helper.isNotEmpty(typeData)){
                                    classType = typeData.getId();
                                    btn_select_type.setText(ProjectHelper.getCommonText(typeData.getName()));
                                }
                            }
                        });
                    }
                } else {
                    ToastHelper.showToast(entity.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initCommonData(){
        try {
            String response = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_COMMON_DATA);
            if (Helper.isNotEmpty(response)){
                CommonDataResp entity = JsonHelper.fromJson(response, CommonDataResp.class);
                if ("200".equals(entity.getCode())) {
                    if (Helper.isNotEmpty(entity.getData())){
                        double price = Double.parseDouble(entity.getData().getPrice())/2;
                        txv_see_cost.setText("允许他人产看答案，每次可获得"+price+"元收入");
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
        if (id == R.id.btn_select_type) {
            if (selectAnswerTypePop.isShowing()) {
                selectAnswerTypePop.dismiss();
            } else {
                selectAnswerTypePop.show(v);
            }
        }else if (id == R.id.imv_checkbox) {
            is_public = !is_public;
            imv_checkbox.setImageResource(is_public?R.drawable.ic_checkbox_checked:R.drawable.ic_checkbox_uncheck);
        }else if (id == R.id.btn_comfirm) {
            doCommit();
        }
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        ProgressDialogHelper.dismissProgressDialog();
        CommonEntity entity = JsonHelper.fromJson(response, CommonEntity.class);
        if ("200".equals(entity.getCode())){
            ToastHelper.showToast("提问成功！");
            NavigationHelper.startActivity(AnswerQuestionAcitity.this, OrderPayActivity.class, null, true);
        }else{
            ToastHelper.showToast(entity.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ProgressDialogHelper.dismissProgressDialog();
        ToastHelper.showToast("提问失败");
        return true;
    }

    private void doCommit(){
        String problem = edt_question_desc.getText().toString();
        String cost = edt_question_cost.getText().toString();
        if (Helper.isEmpty(problem)){
            ToastHelper.showToast("请输入问题描述");
            return;
        }
        if (Helper.isEmpty(cost) || Double.parseDouble(cost) == 0){
            ToastHelper.showToast("请输入悬赏金额");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt("type",3);
        bundle.putLong("item",classType);
        bundle.putDouble("price", Double.parseDouble(cost));
        bundle.putString("problem", problem);
        bundle.putInt("is_public", is_public?1:0);
        NavigationHelper.startActivity(AnswerQuestionAcitity.this, OrderPayActivity.class, bundle, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == ProjectConstants.ActivityRequestCode.REQUEST_SELECT_TYPE){
            ClassTypeResp.DataBean.ChildrenBean childrenBean = (ClassTypeResp.DataBean.ChildrenBean) data.getSerializableExtra(ProjectConstants.BundleExtra.KEY_CLASS_TYPE_ID);
            if (Helper.isNotEmpty(childrenBean)){
                classType = childrenBean.getId();
                btn_select_type.setText(ProjectHelper.getCommonText(childrenBean.getName()));
            }
        }
    }



}
