package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
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
import com.weima.aishangyi.activity.ImagesBroswerActivity;
import com.weima.aishangyi.activity.TalentCircleDetailAcitity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.TalentBean;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.widget.ExpandableTextView;
import com.weima.aishangyi.widget.GridListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TalentCircleAdapter extends BaseAdapter{
	private Activity activity;
	private List<TalentBean> dataList = new ArrayList<>();
	public TalentCircleAdapter(Activity act) {
		this.activity = act;
	}

	public void addMore(List<TalentBean> list){
		if (Helper.isNotEmpty(list)){
			dataList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void clear(){
		dataList.clear();
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return dataList == null ? 0 : dataList.size();
	}

	@Override
	public TalentBean getItem(int position) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.item_talentcircle, null);
			holder.expandableTextView = (ExpandableTextView) convertView.findViewById(R.id.expand_text_view);
			holder.txv_user_name = (TextView) convertView.findViewById(R.id.txv_user_name);
			holder.txv_time = (TextView) convertView.findViewById(R.id.txv_time);
			holder.txv_like = (TextView) convertView.findViewById(R.id.txv_like);
			holder.txv_comment = (TextView) convertView.findViewById(R.id.txv_comment);
			holder.imv_user_avater = (ImageView) convertView.findViewById(R.id.imv_user_avater);
			holder.picList = (GridListView) convertView.findViewById(R.id.picList);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		final TalentBean entity = dataList.get(position);
		if (Helper.isNotEmpty(entity.getImages())){
			holder.picList.setVisibility(View.VISIBLE);
			holder.picList.setData(entity.getImages());
		}else{
			holder.picList.setVisibility(View.GONE);
		}
		holder.expandableTextView.setText(ProjectHelper.getCommonText(entity.getContent()));
		holder.txv_time.setText(Helper.long2DateString(Long.parseLong(entity.getCreated_at()) * 1000, "yyyy-MM-dd HH:mm"));
		holder.txv_like.setText(entity.getThumb_up()+"");
		if (Helper.isEmpty(entity.getIs_thumb())){
			Drawable drawable= activity.getResources().getDrawable(R.drawable.ic_answer_good);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			holder.txv_like.setCompoundDrawables(drawable, null, null, null);
		}else{
			Drawable drawable= activity.getResources().getDrawable(R.drawable.ic_answer_good_press);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			holder.txv_like.setCompoundDrawables(drawable, null, null, null);
		}
		holder.txv_comment.setText(entity.getComment_count()+"");
		if (Helper.isNotEmpty(entity.getUser())){
			if (Helper.isNotEmpty(entity.getUser().getNickname())){
				holder.txv_user_name.setText(ProjectHelper.getCommonText(entity.getUser().getNickname()));
			}else{
				holder.txv_user_name.setText(ProjectHelper.getCommonText(entity.getUser().getPhone()));
			}
			if (Helper.isNotEmpty(entity.getUser().getIcon())){
				Picasso.with(activity).load(entity.getUser().getIcon()).placeholder(R.drawable.ic_avatar_default).into(holder.imv_user_avater);
			}
		}
		holder.imv_user_avater.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(ProjectConstants.BundleExtra.KEY_USER,entity.getUser());
				NavigationHelper.startActivity(activity, PersonInfoActivity.class, bundle, false);
			}
		});
		holder.picList.setItemHandler(new GridListView.ItemHandler() {
			@Override
			public void onItemClick(int pos) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(ImagesBroswerActivity.sImageList, (Serializable) entity.getImages());
				bundle.putInt(ImagesBroswerActivity.sImagePostion,pos);
				NavigationHelper.startActivity(activity,ImagesBroswerActivity.class,bundle,false);
			}
		});
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(ProjectConstants.BundleExtra.KEY_TALENT,entity);
//				bundle.putLong(ProjectConstants.BundleExtra.KEY_TALENT_ID,entity.getId());
				NavigationHelper.startActivity(activity, TalentCircleDetailAcitity.class, bundle, false);
			}
		});
		return convertView;
	}
	
	static class ViewHolder{
		GridListView picList;
		ExpandableTextView expandableTextView;
		ImageView imv_user_avater;
		TextView txv_user_name;
		TextView txv_time;
		TextView txv_like;
		TextView txv_comment;
	}
}
