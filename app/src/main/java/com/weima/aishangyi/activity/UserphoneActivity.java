package com.weima.aishangyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.GetCodeResp;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.widget.ClearableEditText;

import java.util.HashMap;

/**
 * 手机号
 * @author cgy
 */
public class UserphoneActivity extends BaseActivity {
	private ClearableEditText edt_user_account;
	private ClearableEditText edt_user_code;
	private ClearableEditText edt_user_password;
	private Button btn_user_comfirm;
	private Button btn_get_code;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_phone);
		setCustomTitle("手机号");
		initUI();
	}




	private void initUI() {
		edt_user_account = findView(R.id.edt_user_account);
		edt_user_code = findView(R.id.edt_user_code);
		edt_user_password = findView(R.id.edt_user_password);
		btn_user_comfirm = findView(R.id.btn_user_comfirm);
		btn_get_code = findView(R.id.btn_get_code);
		btn_user_comfirm.setOnClickListener(mClickListener);
		btn_get_code.setOnClickListener(mClickListener);
		btn_user_comfirm.setClickable(false);
		edt_user_account.addTextChangedListener(myTextWatcher);
		edt_user_code.addTextChangedListener(myTextWatcher);
		edt_user_password.addTextChangedListener(myTextWatcher);
	}

	private TextWatcher myTextWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void afterTextChanged(Editable editable) {
			isButtonEnbale();
		}
	};

	private void isButtonEnbale(){
		String phone = edt_user_account.getText().toString().trim();
		String password = edt_user_password.getText().toString().trim();
		String code = edt_user_code.getText().toString().trim();
		if (Helper.isNotEmpty(phone) && ProjectHelper.isMobiPhoneNum(phone) && Helper.isNotEmpty(code)
				&& Helper.isNotEmpty(password) ){
			btn_user_comfirm.setClickable(true);
			btn_user_comfirm.setBackgroundResource(R.drawable.shape_btn_orange);
		}else {
			btn_user_comfirm.setClickable(false);
			btn_user_comfirm.setBackgroundResource(R.drawable.shape_btn_gray);
		}
	}

	@Override
	public boolean onResponseSuccess(int gact, String response,
									 Object... extras) {
		super.onResponseSuccess(gact, response, extras);
		ProgressDialogHelper.dismissProgressDialog();
		int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
		if (requestType == 1){
			//获取验证码
			GetCodeResp entity = JsonHelper.fromJson(response, GetCodeResp.class);
			if ("200".equals(entity.getCode())){
				ToastHelper.showToast("验证码已发送，请注意查收");
			}else{
				ToastHelper.showToast(entity.getMessage());
			}
		}else{
			CommonEntity entity = JsonHelper.fromJson(response,CommonEntity.class);
			if ("200".equals(entity.getCode())){
				ToastHelper.showToast("绑定成功！");
				Intent intent = new Intent();
				intent.putExtra(ProjectConstants.BundleExtra.KEY_BIND_PHONE, edt_user_account.getText().toString());
				setResult(RESULT_OK, intent);
				LocalBroadcastManager.getInstance(UserphoneActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_USER_INFO));
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
		return true;
	}

	private View.OnClickListener mClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_user_comfirm:
					doRegister();
					break;
				case R.id.btn_get_code:
					if (Helper.isEmpty(edt_user_account.getText().toString().trim()) ||
							!ProjectHelper.isMobiPhoneNum(edt_user_account.getText().toString().trim())) {
						ToastHelper.showToast("请输入正确的手机号码");
					}else {
						new TimeCount(60000, 1000).start();
						requestData();
					}
					break;
				default:
					break;
			}
		}
	};


	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btn_get_code.setText("获取验证码");
			btn_get_code.setSelected(false);
			btn_get_code.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btn_get_code.setSelected(true);
			btn_get_code.setClickable(false);
			btn_get_code.setText(millisUntilFinished / 1000 + "秒后重新获取");
		}
	}

	private void doRegister() {
		String phone = edt_user_account.getText().toString().trim();
		String password = edt_user_password.getText().toString().trim();
		String code = edt_user_code.getText().toString().trim();
		if (Helper.isEmpty(phone)) {
			ToastHelper.showToast("请输入手机号码");
			return;
		}else if (Helper.isEmpty(code)) {
			ToastHelper.showToast("请输入验证码");
			return;
		}else if (Helper.isEmpty(password)) {
			ToastHelper.showToast("请输入密码");
			return;
		}else if (Helper.isEmpty(password)) {
			ToastHelper.showToast("请再次输入密码");
			return;
		}else if (!ProjectHelper.isMobiPhoneNum(phone)) {
			ToastHelper.showToast("手机号码错误");
			return;
		}else if (!ProjectHelper.isPwdValid(password)) {
			ToastHelper.showToast("密码格式错误");
			return;
		}
		ProgressDialogHelper.showProgressDialog(this, "加载中...");
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("phone", phone);
		requestMap.put("password",password);
		requestMap.put("code", code);
		requestMap.put("device", 1);
		post(ProjectConstants.Url.ACCOUNT_BIND_PHONE, requestMap, 2);
	}


	/**
	 * 请求获取验证码
	 */
	private void requestData(){
		ProgressDialogHelper.showProgressDialog(this, "加载中...");
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("phone", edt_user_account.getText().toString().trim());
		post(ProjectConstants.Url.ACCOUNT_CODE, requestMap, 1);
	}

}
