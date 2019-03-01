package com.weima.aishangyi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.weima.aishangyi.xlistview.XListView;

/**
 * 解决listview与ScrollView嵌套显示不全问题
 *
 * @author LeiKelong
 * @ClassName: CustomListView
 * @date 2015年5月26日 下午3:58:00
 */
public class CustomListView extends XListView {

    //public boolean isOnMeasure;

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        //isOnMeasure = true;
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    /*@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }*/
}
