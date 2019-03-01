package com.weima.aishangyi.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自适应子View高度的viewPager
 * 
 * @author hellsam
 * 
 */
public class AutoHeightViewPager extends ViewPager {

    int page=0;
    AutoHeightViewPager vp;
	public AutoHeightViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       /* vp=this;
        vp.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        vp.setBackgroundColor(Color.parseColor("#FF0000"));
                        break;
                    case 1:
                        vp.setBackgroundColor(Color.parseColor("#836FFF"));
                        break;
                    case 2:
                        vp.setBackgroundColor(Color.parseColor("#836FFF"));
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
		int height = 0;
		// 下面遍历所有child的高度
        page=2-getChildCount();
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec,
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
            Log.e("Height","h="+h);
            Log.e("Height","i="+i);
            Log.e("Height","page="+page);
            if(i==page){
                height=h;
            }
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
				MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
