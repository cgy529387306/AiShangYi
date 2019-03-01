package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.weima.aishangyi.activity.ActivityClassDetailActivity;
import com.weima.aishangyi.activity.TeacherDetailAcitity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ActiveCourseEntity;
import com.weima.aishangyi.entity.ActivityBean;
import com.weima.aishangyi.entity.LunboEntity;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecommendClassAdapter extends PagerAdapter {
	private Activity activity;
	private List<ActivityBean> dataList = new ArrayList<>();
	public RecommendClassAdapter(Activity act, List<ActivityBean> courseList) {
		this.activity = act;
		this.dataList = courseList;
	}

	@Override
	public int getCount() {
		return dataList == null ? 0 : dataList.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View view = LayoutInflater.from(activity).inflate(R.layout.item_recomment_class, null);
		ImageView imv_image = (ImageView) view.findViewById(R.id.imv_image);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvTime = (TextView) view.findViewById(R.id.tv_time);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
		final ActivityBean entity = dataList.get(position);
		tvTitle.setText(entity.getTitle());
		tvPrice.setText("¥"+entity.getPrice());
		if (Helper.isNotEmpty(entity.getImages())){
			Picasso.with(activity).load(entity.getImages().get(0)).placeholder(R.drawable.img_default).into(imv_image);
		}
		String start = Helper.long2DateString(entity.getStart_time() * 1000, "yyyy-MM-dd HH:mm");
		String end = Helper.long2DateString(entity.getEnd_time() * 1000, "yyyy-MM-dd HH:mm");
		tvTime.setText(start+"至"+end);
		((ViewPager) container).addView(view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(ProjectConstants.BundleExtra.KEY_ACTIVITY,entity);
				NavigationHelper.startActivity(activity, ActivityClassDetailActivity.class, bundle, false);
			}
		});
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}


}
