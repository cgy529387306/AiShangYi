package com.weima.aishangyi.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.weima.aishangyi.R;

/**
 * Created by cgy
 */
public class SelectSearchTypePop extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private Context mContext;
    private LinearLayout btn_teacher, btn_class,btn_org;
    private ImageView imv_teacher,imv_class,imv_org;
    private SelectListener selectListener;
    private int type = 1;
    public interface SelectListener {
        public void onSelected(int type);
    }

    public SelectSearchTypePop(Context context, SelectListener listener) {
        super(context);
        this.mContext = context;
        this.selectListener = listener;
        rootView = View.inflate(mContext, R.layout.pop_search_type, null);
        setFocusable(true);
        setOutsideTouchable(true);
        setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        setHeight(ViewPager.LayoutParams.WRAP_CONTENT);
        setContentView(rootView);
        setBackgroundDrawable(new BitmapDrawable());
        findComponent();
        initComponent();
    }

    private void findComponent() {
        btn_teacher = (LinearLayout) rootView.findViewById(R.id.btn_teacher);
        btn_class = (LinearLayout) rootView.findViewById(R.id.btn_class);
        btn_org = (LinearLayout) rootView.findViewById(R.id.btn_org);
        imv_teacher = (ImageView) rootView.findViewById(R.id.imv_teacher);
        imv_class = (ImageView) rootView.findViewById(R.id.imv_class);
        imv_org = (ImageView) rootView.findViewById(R.id.imv_org);
    }

    private void initComponent() {
        rootView.findViewById(R.id.outView).setOnClickListener(this);
        btn_teacher.setOnClickListener(this);
        btn_class.setOnClickListener(this);
        btn_org.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.outView){
            dismiss();
        }else if (id == R.id.btn_teacher) {
            type = 2;
            initType();
            selectListener.onSelected(type);
            dismiss();
        }else if (id == R.id.btn_class) {
            type = 3;
            initType();
            selectListener.onSelected(type);
            dismiss();
        }else if (id == R.id.btn_org) {
            type = 1;
            initType();
            selectListener.onSelected(type);
            dismiss();
        }
    }

    private void initType(){
        if (type == 1){
            imv_org.setImageResource(R.drawable.ic_checkbox_checked);
            imv_teacher.setImageResource(R.drawable.ic_checkbox_uncheck);
            imv_class.setImageResource(R.drawable.ic_checkbox_uncheck);
        }else if (type == 2) {
            imv_org.setImageResource(R.drawable.ic_checkbox_uncheck);
            imv_teacher.setImageResource(R.drawable.ic_checkbox_checked);
            imv_class.setImageResource(R.drawable.ic_checkbox_uncheck);
        }else if (type == 3) {
            imv_org.setImageResource(R.drawable.ic_checkbox_uncheck);
            imv_teacher.setImageResource(R.drawable.ic_checkbox_uncheck);
            imv_class.setImageResource(R.drawable.ic_checkbox_checked);
        }
    }

    public void show(View v) {
        showAsDropDown(v);
    }
}
