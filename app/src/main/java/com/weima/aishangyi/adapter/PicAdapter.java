package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.ImagesBroswer1Activity;
import com.weima.aishangyi.activity.ImagesBroswerActivity;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PicAdapter extends BaseAdapter {
	private Activity mActivity;
	private List<String> mlistData = new ArrayList<String>();
	private DelImg listener = null;

	public PicAdapter(Activity activity, List<String> list, DelImg listener) {
		this.mlistData = list;
		this.mActivity = activity;
		this.listener = listener;
	}
	
	public void updateList(List<String> list) {
		this.mlistData = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mlistData.size();
	}

	@Override
	public Object getItem(int position) {
		return mlistData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_pic_grid, null);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.image);
			viewHolder.del = (ImageView) convertView.findViewById(R.id.delete);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String pathStr = mlistData.get(position);
		if (Helper.isNotEmpty(pathStr)){
			if (ProjectHelper.isInteger(pathStr)) {
				int imageId = Integer.parseInt(pathStr);
				Picasso.with(mActivity).load(imageId).into(viewHolder.img);
			}else {
				Picasso.with(mActivity).load(pathStr).placeholder(R.drawable.img_default).into(viewHolder.img);
			}
		}
		viewHolder.del.setOnClickListener(new ClickListener(position));
		viewHolder.img.setOnClickListener(new ClickListener(position));
		if (position == mlistData.size() - 1) {
			viewHolder.del.setVisibility(View.GONE);
		} else {
			viewHolder.del.setVisibility(View.VISIBLE);
		}
		return convertView;
	}


	class ViewHolder {
		ImageView img, del;
	}

	public interface DelImg {
		public void delImg(int position);

		public void addImg();
	}

	class ClickListener implements OnClickListener {
		int position = -1;

		public ClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.image:
				if (position == mlistData.size() - 1) {
					if (listener != null)
						listener.addImg();
				}else{
					Bundle bundle = new Bundle();
					bundle.putSerializable(ImagesBroswerActivity.sImageList, (Serializable) mlistData);
					bundle.putInt(ImagesBroswerActivity.sImagePostion,position);
					NavigationHelper.startActivity(mActivity, ImagesBroswer1Activity.class, bundle, false);
				}
				break;
			case R.id.delete:
				if (listener != null)
					listener.delImg(position);
				break;
			}
		}

	}
}
