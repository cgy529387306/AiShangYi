package com.weima.aishangyi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 作者：cgy on 17/3/14 01:18
 * 邮箱：593960111@qq.com
 */
public class RecyclerImageView extends ImageView{
    public RecyclerImageView(Context context) {
        super(context);
    }

    public RecyclerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }


}
