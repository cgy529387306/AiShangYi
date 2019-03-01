package com.weima.aishangyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.LessonBean;
import com.weima.aishangyi.entity.MemberBean;
import com.weima.aishangyi.entity.TimeBean;
import com.weima.aishangyi.utils.InputFilterMinMax;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.Date;
import java.util.HashMap;

/**
 * 购买课程
 *
 * @author cgy
 */
public class BuyClassActivity extends BaseActivity implements View.OnClickListener{
    private ImageView imv_icon;
    private TextView txv_name,txv_type,txv_desc,txv_class_number,txv_time;
    private TextView txv_number_rest,txv_address;
    private EditText edt_count;
    private int number = 1;
    private int restCount;
    private LessonBean lessonBean;
    private TimeBean timeBean;
    private String dateStr;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("购买课程");
        setContentView(R.layout.activity_buyclass);
        initUI();
        initData();
    }

    private void initUI() {
        restCount = MemberBean.getInstance().getNumber();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dateStr = getIntent().getStringExtra("dateStr");
        lessonBean = (LessonBean) getIntent().getSerializableExtra("lesson");
        timeBean = (TimeBean) getIntent().getSerializableExtra("timeBean");
        address = getIntent().getStringExtra("address");
        imv_icon = findView(R.id.imv_icon);
        txv_name = findView(R.id.txv_name);
        txv_type = findView(R.id.txv_type);
        txv_desc = findView(R.id.txv_desc);
        txv_address = findView(R.id.txv_address);
        txv_class_number = findView(R.id.txv_class_number);
        txv_time = findView(R.id.txv_time);
        txv_number_rest = findView(R.id.txv_number_rest);
        edt_count = findView(R.id.edt_count);
        Date date = Helper.string2Date(dateStr,"yyyy-MM-dd");
        if (Helper.isNotEmpty(date)){
            txv_time.setText(ProjectHelper.getWeek(date)+"  "+timeBean.getStart_time()+"-"+timeBean.getEnd_time());
        }
        setTotal();
        findView(R.id.btn_pay).setOnClickListener(this);
        findView(R.id.btnReduce).setOnClickListener(this);
        findView(R.id.btnAdd).setOnClickListener(this);
        edt_count.setSelection(ProjectHelper.getCommonSeletion(edt_count.getText().toString()));
        edt_count.setFilters(new InputFilter[]{ new InputFilterMinMax(1, (restCount-number+1),"超过本周可预约数")});
        edt_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Helper.isNotEmpty(edt_count.getText().toString())) {
                    setTotal();
                }
            }
        });
    }

    private void setTotal(){
        number = Integer.parseInt(edt_count.getText().toString());
        txv_number_rest.setText("本周剩余可预约数"+(restCount-number));
    }

    private void initData(){
        txv_address.setText(ProjectHelper.getCommonText(address));
        if (Helper.isNotEmpty(lessonBean)){
            if (Helper.isNotEmpty(lessonBean.getIcon())){
                Picasso.with(BuyClassActivity.this).load(lessonBean.getIcon()).placeholder(R.drawable.img_default).into(imv_icon);
            }
            txv_name.setText(ProjectHelper.getCommonText(lessonBean.getName()));
            txv_type.setText(lessonBean.getLesson_item() == 1 ? "一对一" : "拼课");
            txv_type.setBackgroundResource(lessonBean.getLesson_item() == 1 ? R.drawable.shape_rect_blue : R.drawable.shape_rect_orange);
            txv_desc.setText(ProjectHelper.getCommonText(lessonBean.getLesson_detail()));
            txv_class_number.setText(lessonBean.getNumber()+"课时");
        }
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        CommonEntity entity = JsonHelper.fromJson(response, CommonEntity.class);
        if ("200".equals(entity.getCode())){
            ToastHelper.showToast("预约成功");
            LocalBroadcastManager.getInstance(BuyClassActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_MEMBER));
            finish();
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



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_pay){
            requestComfirm();
        }else if (id ==R.id.btnReduce){
            if (number>1) {
                number--;
                edt_count.setText(number + "");
                edt_count.setSelection(ProjectHelper.getCommonSeletion(edt_count.getText().toString()));
                setTotal();
            }
        }else  if (id ==R.id.btnAdd){
            if (number<restCount){
                number++;
                edt_count.setText(number+"");
                edt_count.setSelection(ProjectHelper.getCommonSeletion(edt_count.getText().toString()));
                setTotal();
            }else{
                ToastHelper.showToast("超过本周可预约数");
            }
        }
    }

    private void requestComfirm(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("lesson_type",1);
        requestMap.put("lesson_mode",1);
        requestMap.put("number",number);
        requestMap.put("appoint_id", timeBean.getId());
        requestMap.put("date",dateStr);
        requestMap.put("lesson_id",lessonBean.getId());
        post(ProjectConstants.Url.ORDER_LESSON_NEW, requestMap);
    }
}
