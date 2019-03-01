package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.ClassDetailAcitity;
import com.weima.aishangyi.activity.TeacherDetailAcitity;
import com.weima.aishangyi.entity.TeacherBean;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

public class RecommendTeacherAdapter extends BaseAdapter{
	private Activity activity;
	private List<TeacherBean> dataList = new ArrayList<>();
	public RecommendTeacherAdapter(Activity act) {
		this.activity = act;
	}

	public void addMore(List<TeacherBean> list){
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
	public TeacherBean getItem(int position) {
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
			holder = new  ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(R.layout.item_recomment_teacher, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tvDistance = (TextView) convertView.findViewById(R.id.tv_distance);
			holder.tvOrg = (TextView) convertView.findViewById(R.id.tv_org);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
			holder.tvScore = (TextView) convertView.findViewById(R.id.tv_score);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.star = (RatingBar) convertView.findViewById(R.id.star);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
        final TeacherBean entity = dataList.get(position);
		holder.tvName.setText(ProjectHelper.getCommonText(entity.getNickname()));
		holder.tvDesc.setText("简介："+ProjectHelper.getEmptyText(entity.getDetail()));
		holder.tvOrg.setText("机构："+ProjectHelper.getEmptyText(entity.getOrg()));
		holder.tvDistance.setText(ProjectHelper.formatDecimal(entity.getDistance()));
		if (Helper.isNotEmpty(entity.getIcon())){
			Picasso.with(activity).load(entity.getIcon()).placeholder(R.drawable.img_default).into(holder.ivIcon);
		}
		holder.star.setRating(entity.getStar());
		holder.tvScore.setText(entity.getStar()+"");
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent intent = new Intent(activity, TeacherDetailAcitity.class);
                intent.putExtra("id", entity.getId());
                activity.startActivity(intent);
			}
		});
		return convertView;
	}

	static class ViewHolder{
		TextView tvName;
		TextView tvDesc;
		TextView tvOrg;
		TextView tvScore;
		TextView tvDistance;
		ImageView ivIcon;
		RatingBar star;
	}
}
