package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.base.BaseWebViewActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.GetCodeResp;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.widget.ClearableEditText;

import java.util.HashMap;

/**
 * 用户注册
 * @author cgy
 *
 */
public class UserRegisterActivity extends BaseActivity{
	private ClearableEditText edt_user_account;
	private ClearableEditText edt_user_code;
	private ClearableEditText edt_user_password;
	private ClearableEditText edt_user_repassword;
	private ClearableEditText edt_invite_code;
	private Button btn_user_comfirm;
	private Button btn_get_code;
	private ImageView imv_cheched;
	private boolean isAgree = true;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_register);
		setCustomTitle("注册");
		initUI();
	}


	private void initUI() {
		imv_cheched = findView(R.id.imv_cheched);
		edt_user_account = findView(R.id.edt_user_account);
		edt_user_code = findView(R.id.edt_user_code);
		edt_user_password = findView(R.id.edt_user_password);
		edt_user_repassword = findView(R.id.edt_user_repassword);
		edt_invite_code = findView(R.id.edt_invite_code);
		btn_user_comfirm = findView(R.id.btn_user_comfirm);
		btn_get_code = findView(R.id.btn_get_code);
		imv_cheched.setOnClickListener(mClickListener);
		btn_user_comfirm.setOnClickListener(mClickListener);
		btn_get_code.setOnClickListener(mClickListener);
		btn_user_comfirm.setClickable(false);
		edt_user_account.addTextChangedListener(myTextWatcher);
		edt_user_code.addTextChangedListener(myTextWatcher);
		edt_user_password.addTextChangedListener(myTextWatcher);
		edt_user_repassword.addTextChangedListener(myTextWatcher);
		edt_invite_code.addTextChangedListener(myTextWatcher);
		findView(R.id.txv_user_agreement).setOnClickListener(mClickListener);
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
		String repassword = edt_user_repassword.getText().toString().trim();
		String code = edt_user_code.getText().toString().trim();
		if (isAgree && Helper.isNotEmpty(phone) && ProjectHelper.isMobiPhoneNum(phone) && Helper.isNotEmpty(code)
				&& Helper.isNotEmpty(password) && Helper.isNotEmpty(repassword)){
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
		ProgressDialogHelper.dismissProgressDialog();
		if (super.onResponseSuccess(gact, response, extras)) {
			return true;
		}
		int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
		if (requestType == 1){
			//获取验证码
			GetCodeResp entity = JsonHelper.fromJson(response,GetCodeResp.class);
			if ("200".equals(entity.getCode())){
				ToastHelper.showToast("验证码已发送，请注意查收");
			}else{
				ToastHelper.showToast(entity.getMessage());
			}
		}else{
			//注册
			CommonEntity entity = JsonHelper.fromJson(response,CommonEntity.class);
			if ("200".equals(entity.getCode())){
				ToastHelper.showToast("注册成功");
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

	private OnClickListener mClickListener = new OnClickListener() {
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
				case R.id.imv_cheched:
					imv_cheched.setImageResource(isAgree?R.drawable.ic_checkbox_uncheck:R.drawable.ic_checkbox_checked);
					isAgree = !isAgree;
					isButtonEnbale();
					break;
				case R.id.txv_user_agreement:
					Bundle bundle1 = new Bundle();
					bundle1.putString(ProjectConstants.BundleExtra.KEY_WEB_DETAIL_URL, "http://aishangyi.h5h5h5.cn/api/article/agreement");
					bundle1.putString(ProjectConstants.BundleExtra.KEY_WEB_DETAIL_TITLE, "用户协议");
					bundle1.putBoolean(ProjectConstants.BundleExtra.KEY_IS_SET_TITLE, true);
					NavigationHelper.startActivity(UserRegisterActivity.this, BaseWebViewActivity.class, bundle1, false);
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
			btn_get_code.setBackgroundResource(R.drawable.shape_edt_white);
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
		String repassword = edt_user_repassword.getText().toString().trim();
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
		}else if (!password.equals(repassword)) {
			ToastHelper.showToast("两次输入的密码不一致");
			return;
		}
		ProgressDialogHelper.showProgressDialog(this, "加载中...");
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("phone", phone);
		requestMap.put("password", password);
		requestMap.put("password_confirmation",repassword);
		requestMap.put("code", code);
		requestMap.put("device", 0);
		if (Helper.isNotEmpty(edt_invite_code.getText().toString())){
			requestMap.put("invite", edt_invite_code.getText().toString());
		}
		post(ProjectConstants.Url.ACCOUNT_REGISTER, requestMap, 2);
	}


	/**
	 * 请求获取验证码
	 */
	private void requestData(){
		ProgressDialogHelper.showProgressDialog(UserRegisterActivity.this, "加载中");
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("phone", edt_user_account.getText().toString().trim());
		post(ProjectConstants.Url.ACCOUNT_CODE, requestMap, 1);
	}
}
