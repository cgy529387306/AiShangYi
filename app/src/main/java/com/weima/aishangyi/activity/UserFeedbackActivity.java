package com.weima.aishangyi.activity;

import android.os.Bundle;
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

import java.util.HashMap;

/**
 * 意见反馈
 *
 * @author cgy
 */
public class UserFeedbackActivity extends BaseActivity {
    private EditText mInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("意见反馈");
        setContentView(R.layout.activity_user_feedback);
        initUI();
    }

    private void initUI() {
        mInputView = findView(R.id.add_text_input);
        findView(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mInputView.getText().toString().trim();
                if (Helper.isEmpty(content)) {
                    ToastHelper.showToast("请输入反馈内容");
                } else {
                    ProgressDialogHelper.showProgressDialog(UserFeedbackActivity.this, "加载中...");
                    HashMap<String, Object> requestMap = new HashMap<String, Object>();
                    requestMap.put("content", content);
                    post(ProjectConstants.Url.ACCOUNT_FEEDBACK, requestMap);
                }
            }
        });
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        ProgressDialogHelper.dismissProgressDialog();
        CommonEntity entity = JsonHelper.fromJson(response, CommonEntity.class);
		if (Helper.isNotEmpty(entity)) {
			if ("200".equals(entity.getCode())) {
                ToastHelper.showToast("反馈成功");
				finish();
			}else{
                ToastHelper.showToast(entity.getMessage());
            }
			return true;
		}
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ProgressDialogHelper.dismissProgressDialog();
        ToastHelper.showToast("反馈失败");
        return true;
    }

//    private void requestData(){
//        HashMap<String, Object> requestMap = new HashMap<String, Object>();
//        post(ProjectConstants.Url.ACCOUNT_ARTICLE, requestMap);
//    }

}
