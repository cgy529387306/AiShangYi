package com.weima.aishangyi.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;

import java.util.ArrayList;
import java.util.List;

public class GridListView extends ViewGroup {
	private ItemHandler itemHandler;
	public void setItemHandler(ItemHandler itemHandler) {
		this.itemHandler = itemHandler;
	}

	public interface ItemHandler {
		void onItemClick(int pos);
	}

	/**
	 * 子项大小
	 */
	private int mItemWidth;
	/**
	 * 间隙大小
	 */
	private int mPadding = 0;
	/**
	 * 列数
	 */
	private int mClunNum = 0;
	/**
	 * 数据源
	 */
	private List<String> mData;

	public GridListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPadding = (int) (4 * getResources().getDisplayMetrics().density);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 宽度获取
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		mItemWidth = (widthSize - 2 * mPadding) / 3;

		// 测量子View
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);

			child.measure(MeasureSpec.makeMeasureSpec(mItemWidth,
					MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
					mItemWidth, MeasureSpec.EXACTLY));
		}
		// 测量当前View
		if (mClunNum == 0) {
			mClunNum = 1;
		}
		int rowNum = childCount / mClunNum;
		rowNum = childCount % mClunNum == 0 ? rowNum : rowNum + 1;
		int heightSize = (rowNum - 1) * mPadding + rowNum * mItemWidth;
		setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
				heightSize, MeasureSpec.EXACTLY));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		if (count == 1) {
			View view = getChildAt(0);
			view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		} else {
			layoutChild(count, mClunNum);
		}
	}

	private void layoutChild(int childCount, int clunNum) {
		if (clunNum == 0) {
			return;
		}
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			int xPosition = i % clunNum;
			int yPosition = i / clunNum;
			int l = mPadding * xPosition + mItemWidth * xPosition;
			int t = mPadding * yPosition + mItemWidth * yPosition;
			childView.layout(l, t, l + mItemWidth, t + mItemWidth);
		}
	}

	/**
	 * 设置数据源
	 * 
	 * @param photos
	 */
	public void setData(List<String> photos) {
		this.mData = photos;
		if (null == photos) {
			return;
		}
		int size = photos.size();
		if (size == 0) {
			return;
		}

		if (size == 1) {
			mClunNum = 1;
		} else {
			mClunNum = 3;
		}

		removeAllViews();
		// 添加子View
		List<String> paths = new ArrayList<String>();
		for (int i = 0; i < mData.size(); i++) {
			paths.add(mData.get(i));
		}
		for (int i = 0; i < mData.size(); i++) {
			final int index = i;
			RecyclerImageView imvItem = new RecyclerImageView(getContext());
			imvItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			if (Helper.isNotEmpty(mData.get(i))){
				Picasso.with(getContext()).load(mData.get(i)).config(Bitmap.Config.RGB_565).placeholder(R.drawable.img_default).into(imvItem);
			}
			imvItem.setScaleType(ScaleType.CENTER_CROP);
			imvItem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if (itemHandler != null) {
						itemHandler.onItemClick(index);
					}
				}
			});
			addView(imvItem);
		}
	}
}