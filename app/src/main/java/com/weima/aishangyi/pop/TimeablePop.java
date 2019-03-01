package com.weima.aishangyi.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.TimeableAdapter;
import com.weima.aishangyi.widget.LineGridView;

import java.util.List;


/**
 * Created by cgy
 */
public class TimeablePop extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private Context mContext;
    private LineGridView timeableGrid;
    private TimeableAdapter timeableAdapter;
    private SelectListener selectListener;

    public interface SelectListener {
        public void onSelected(int type);
    }

    public TimeablePop(Context context, SelectListener listener) {
        super(context);
        this.mContext = context;
        this.selectListener = listener;
        rootView = View.inflate(mContext, R.layout.pop_timeable, null);
        setFocusable(false);
        setOutsideTouchable(false);
        setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        setHeight(ViewPager.LayoutParams.MATCH_PARENT);
        setContentView(rootView);
        setBackgroundDrawable(new BitmapDrawable());
        findComponent();
        initComponent();
    }

    public void setSelectTime(List<String> list){
        timeableAdapter.setSelectTime(list);
    }

    private void findComponent() {
        timeableGrid = (LineGridView) rootView.findViewById(R.id.timeableGrid);
        timeableAdapter = new TimeableAdapter((Activity) mContext);
        timeableGrid.setAdapter(timeableAdapter);
    }

    private void initComponent() {
        rootView.findViewById(R.id.btn_close).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_close) {
            dismiss();
        }
    }

    public void show(View v) {
        showAtLocation(v, Gravity.CENTER, 0, 0);
    }
}
