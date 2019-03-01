package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;

/**
 * 用户帮助
 * @author cgy
 */
public class UserHelpActivity extends BaseActivity {
	private EditText mInputView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustomTitle("帮助");
		setContentView(R.layout.activity_user_help);
		initUI();
	}
	
	private void initUI() {
		findView(R.id.btn_question).setOnClickListener(mClickListener);
		findView(R.id.btn_about).setOnClickListener(mClickListener);
		findView(R.id.btn_call).setOnClickListener(mClickListener);
    }

	/**
	 * 点击事件
	 */
	private View.OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_question:
					break;

				case R.id.btn_about:
					break;

				case R.id.btn_call:
					break;

				default:
					break;
			}
		}
	};

}
