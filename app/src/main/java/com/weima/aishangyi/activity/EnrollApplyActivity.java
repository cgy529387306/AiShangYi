package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ActivityBean;
import com.weima.aishangyi.utils.InputFilterMinMax;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

/**
 * 报名申请
 *
 * @author cgy
 */
public class EnrollApplyActivity extends BaseActivity implements View.OnClickListener{
    private TextView txv_activity_title,txv_activity_cost,txv_all_cost;
    private EditText edit_count,edit_user_name,edit_user_phone,edit_user_remark;
    private ActivityBean activityBean;
    private int number = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("报名申请");
        setContentView(R.layout.activity_enroll_apply);
        initUI();
        initData();
    }

    private void initUI() {
        activityBean = (ActivityBean) getIntent().getSerializableExtra(ProjectConstants.BundleExtra.KEY_ACTIVITY);
        txv_activity_title = findView(R.id.txv_activity_title);
        txv_activity_cost = findView(R.id.txv_activity_cost);
        txv_all_cost = findView(R.id.txv_all_cost);
        edit_count = findView(R.id.edit_count);
        edit_user_name = findView(R.id.edit_user_name);
        edit_user_phone = findView(R.id.edit_user_phone);
        edit_user_remark = findView(R.id.edit_user_remark);
        findView(R.id.btnReduce).setOnClickListener(this);
        findView(R.id.btnAdd).setOnClickListener(this);
        findView(R.id.btn_commit).setOnClickListener(this);
        edit_count.setSelection(ProjectHelper.getCommonSeletion(edit_count.getText().toString()));
        edit_count.setFilters(new InputFilter[]{ new InputFilterMinMax(1, activityBean.getNumber(),"超过报名人数限制")});
        edit_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               if (Helper.isNotEmpty(edit_count.getText().toString())){
                   number = Integer.parseInt(edit_count.getText().toString());
                   txv_all_cost.setText("¥"+number*activityBean.getPrice());
               }
            }
        });
    }

    private void initData(){
        if (Helper.isNotEmpty(activityBean)){
            txv_activity_title.setText(ProjectHelper.getCommonText(activityBean.getTitle()));
            txv_activity_cost.setText("¥"+activityBean.getPrice());
            txv_all_cost.setText("¥"+activityBean.getPrice());
        }
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id ==R.id.btnReduce){
            if (number>0) {
                number--;
                edit_count.setText(number+"");
                edit_count.setSelection(ProjectHelper.getCommonSeletion(edit_count.getText().toString()));
                txv_all_cost.setText("¥"+number*activityBean.getPrice());
            }
        }else  if (id ==R.id.btnAdd){
            if (number<activityBean.getNumber()){
                number++;
                edit_count.setText(number+"");
                edit_count.setSelection(ProjectHelper.getCommonSeletion(edit_count.getText().toString()));
                txv_all_cost.setText("¥"+number*activityBean.getPrice());
            }else{
                ToastHelper.showToast("超过报名人数限制");
            }
        }else if (id == R.id.btn_commit){
            ProjectHelper.disableViewDoubleClick(view);
            doApply();
        }
    }

    private void doApply() {
        String numberStr = edit_count.getText().toString().trim();
        String contacts = edit_user_name.getText().toString().trim();
        String phone = edit_user_phone.getText().toString().trim();
        String remark = edit_user_remark.getText().toString().trim();
        if (Helper.isEmpty(numberStr)) {
            ToastHelper.showToast("请输入报名人数");
            return;
        } else if (Helper.isEmpty(contacts)) {
            ToastHelper.showToast("请输入联系人");
            return;
        } else if (Helper.isEmpty(phone)) {
            ToastHelper.showToast("请输入联系电话");
            return;
        }else if (!ProjectHelper.isMobiPhoneNum(phone) && !ProjectHelper.isPhone(phone)){
            ToastHelper.showToast("联系电话格式错误");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("type",2);
        bundle.putLong("active_id",activityBean.getId());
        bundle.putInt("number", number);
        bundle.putDouble("price", activityBean.getPrice());
        bundle.putDouble("amount", number*activityBean.getPrice());
        bundle.putString("quote", remark);
        bundle.putString("contacts", contacts);
        bundle.putString("phone", phone);
        NavigationHelper.startActivity(EnrollApplyActivity.this, OrderPayActivity.class, bundle, false);
    }
}
