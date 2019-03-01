package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.utils.NavigationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 * @author cgy
 */
public class MsgActivity extends BaseActivity implements View.OnClickListener{
	private ListView listView;
	private List<String> dataList = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg);
		setCustomTitle("我的消息");
		setImageRightButton(R.drawable.ic_contacts, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NavigationHelper.startActivity(MsgActivity.this, UserContactActivity.class, null, false);
			}
		});
		initUI();
	}

	private void initUI() {
		listView = findView(R.id.listView);
		listView.addHeaderView(getHeaderView());
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getTestData()));
	}

	private List<String> getTestData() {
		List<String> list = new ArrayList<>();
		for (int i=0;i<10;i++){
			list.add("城市"+i);
		}
		return list;
	}

	private View getHeaderView(){
		View view = LayoutInflater.from(MsgActivity.this).inflate(R.layout.header_msg, null);
		view.findViewById(R.id.btn_sys_msg).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NavigationHelper.startActivity(MsgActivity.this, SysMsgActivity.class, null, false);
			}
		});
		return view;
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
	}
}
