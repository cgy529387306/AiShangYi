package com.weima.aishangyi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.TeacherListActivity;
import com.weima.aishangyi.entity.LunboEntity;
import com.weima.aishangyi.entity.StuCategoryEntity;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.widget.CircleTransform;

public class RecommendGridAdapter extends BaseAdapter{
	private Activity activity;
	private List<StuCategoryEntity> mData;
	public RecommendGridAdapter(Activity act,List<StuCategoryEntity> list) {
		this.activity = act;
		this.mData = list;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public LunboEntity getItem(int position) {
		return null;
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.item_recomment_grid, null);
			holder.txv_title = (TextView) convertView.findViewById(R.id.txv_title);
			holder.imv_icon = (ImageView) convertView.findViewById(R.id.imv_icon);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		final StuCategoryEntity entity = mData.get(position);
		holder.txv_title.setText(entity.getName());
		//holder.imv_icon.setImageResource(R.drawable.ic_test_music);
		if (Helper.isNotEmpty(entity.getIcon())){
			Picasso.with(activity).load(entity.getIcon()).placeholder(R.drawable.ic_avatar_default).into(holder.imv_icon);
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putLong("id",entity.getId());
				bundle.putString("name",entity.getName());
				NavigationHelper.startActivity(activity, TeacherListActivity.class, bundle, false);
			}
		});
		return convertView;
	}
	
	static class ViewHolder{
		TextView txv_title;
		ImageView imv_icon;
	}
}
