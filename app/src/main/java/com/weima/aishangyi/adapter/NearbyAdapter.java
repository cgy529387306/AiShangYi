package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.PersonInfoActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.NearBean;
import com.weima.aishangyi.entity.User;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

public class NearbyAdapter extends BaseAdapter{
	private Activity activity;
	private List<NearBean> dataList = new ArrayList<>();
	public NearbyAdapter(Activity act) {
		this.activity = act;
	}

	public void clear(){
		dataList.clear();
		notifyDataSetChanged();
	}

	public void addMore(List<NearBean> list){
		if (Helper.isNotEmpty(list)){
			dataList.addAll(list);
			notifyDataSetChanged();
		}
	}


	@Override
	public int getCount() {
		return dataList == null ? 0 : dataList.size();
	}

	@Override
	public NearBean getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (Helper.isNull(convertView)) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(R.layout.item_nearby, null);
			holder.imv_user_avater = (ImageView) convertView.findViewById(R.id.imv_user_avater);
			holder.imv_user_sex = (ImageView) convertView.findViewById(R.id.imv_user_sex);
			holder.txv_user_name = (TextView) convertView.findViewById(R.id.txv_user_name);
			holder.txv_user_sign = (TextView) convertView.findViewById(R.id.txv_user_sign);
			holder.txv_distance = (TextView) convertView.findViewById(R.id.txv_distance);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		final NearBean entity = dataList.get(position);
		if (Helper.isNotEmpty(entity.getIcon())){
			Picasso.with(activity).load(entity.getIcon()).placeholder(R.drawable.ic_avatar_default).into(holder.imv_user_avater);
		}
		holder.txv_user_name.setText(ProjectHelper.getCommonText(entity.getNickname()));
		holder.txv_user_sign.setText(ProjectHelper.getCommonText(entity.getSignature()));
		holder.txv_distance.setText(ProjectHelper.formatDecimal(entity.getDistance()));
		if (entity.getSex()==1){
			holder.imv_user_sex.setImageResource(R.drawable.ic_nan);
		}else{
			holder.imv_user_sex.setImageResource(R.drawable.ic_nv);
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User user = new User();
				user.setId(entity.getId());
				user.setDevice(entity.getDevice());
				Bundle bundle = new Bundle();
				bundle.putInt("type",1);
				bundle.putSerializable(ProjectConstants.BundleExtra.KEY_USER, user);
				NavigationHelper.startActivity(activity, PersonInfoActivity.class, bundle, false);
			}
		});
		return convertView;
	}
	
	static class ViewHolder{
		ImageView imv_user_sex;
		ImageView imv_user_avater;
		TextView txv_user_name;
		TextView txv_user_sign;
		TextView txv_distance;
	}
}
