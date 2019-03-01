package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.AppHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;

/**
 * 关于我们
 *
 * @author cgy
 */
public class AboutUsActivity extends BaseActivity {
    private TextView txv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("关于我们");
        setContentView(R.layout.activity_user_aboutus);
        txv_version = findView(R.id.txv_version);
        txv_version.setText("版本：V"+ AppHelper.getCurrentVersionName());
    }

}
