package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseWebViewActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.AdvertEntity;
import com.weima.aishangyi.entity.LunboEntity;
import com.weima.aishangyi.utils.NavigationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 头部轮播适配器
 * @author cgy
 *
 */
public class HeadWheelAdapter extends PagerAdapter {
	private Context mContext;
	private List<AdvertEntity> dataList;
	public HeadWheelAdapter(Context context, List<AdvertEntity> list) {
		this.mContext = context;
		this.dataList = list;
	}

	@Override
	public int getCount() {
		return dataList == null ? 0 : dataList.size();
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView imvItem = obtainHeadWheelItems(dataList.get(position%dataList.size()));
        ((ViewPager) container).addView(imvItem);
		final AdvertEntity entity = dataList.get(position);
		imvItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (entity.getIs_jump() == 1 && Helper.isNotEmpty(entity.getUrl())){
					Bundle bundle1 = new Bundle();
					bundle1.putString(ProjectConstants.BundleExtra.KEY_WEB_DETAIL_URL, entity.getUrl());
					bundle1.putBoolean(ProjectConstants.BundleExtra.KEY_IS_SET_TITLE, false);
					NavigationHelper.startActivity((Activity) mContext, BaseWebViewActivity.class, bundle1, false);
				}
			}
		});
        return imvItem;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	
	/**
	 * 获取头部轮播子项
	 * @return
	 */
	private ImageView obtainHeadWheelItems(final AdvertEntity ent) {
			ImageView imvItem = new ImageView(mContext);
			imvItem.setScaleType(ScaleType.CENTER_CROP);
			imvItem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//TODO
				}
			});
			Picasso.with(mContext).load(ent.getImage_url()).placeholder(R.drawable.img_default).into(imvItem);
			return imvItem;
	}
	
}