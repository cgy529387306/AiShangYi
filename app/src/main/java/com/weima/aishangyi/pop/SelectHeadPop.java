package com.weima.aishangyi.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.weima.aishangyi.R;

/**
 * Created by cgy
 */
public class SelectHeadPop extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private Context mContext;
    private Button btn_takephone, btn_phone, btn_cannel;
    private SelectListener selectListener;

    public interface SelectListener {
        public void onSelected(int type);
    }

    public SelectHeadPop(Context context, SelectListener listener) {
        super(context);
        this.mContext = context;
        this.selectListener = listener;
        rootView = View.inflate(mContext, R.layout.pop_head, null);
        setFocusable(true);
        setOutsideTouchable(true);
        setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        setHeight(ViewPager.LayoutParams.MATCH_PARENT);
        setContentView(rootView);
        setBackgroundDrawable(new BitmapDrawable());
        findComponent();
        initComponent();
    }

    private void findComponent() {
        btn_takephone = (Button) rootView.findViewById(R.id.btn_takephone);
        btn_phone = (Button) rootView.findViewById(R.id.btn_phone);
        btn_cannel = (Button) rootView.findViewById(R.id.btn_cannel);
    }

    private void initComponent() {
        btn_takephone.setOnClickListener(this);
        btn_phone.setOnClickListener(this);
        btn_cannel.setOnClickListener(this);
        rootView.findViewById(R.id.outView).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cannel) {
            dismiss();
        } else if (id == R.id.outView) {
            dismiss();
        } else if (id == R.id.btn_takephone) {
            selectListener.onSelected(0);
            dismiss();
        } else if (id == R.id.btn_phone) {
            selectListener.onSelected(1);
            dismiss();
        }
    }

    public void show(View v) {
        showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }
}
