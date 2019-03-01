package com.weima.aishangyi.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.AnswerTypeAdapter;
import com.weima.aishangyi.adapter.FilterSortAdapter;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ClassTypeResp;

import java.util.List;

/**
 * Created by cgy
 */
public class SelectAnswerTypePop extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private ListView listView;
    private Context mContext;
    private AnswerTypeAdapter adapter;
    private List<ClassTypeResp.DataBean> classTypeList;
    private SelectListener selectListener;
    public interface SelectListener {
        public void onSelected(ClassTypeResp.DataBean type);
    }

    public SelectAnswerTypePop(Context context, List<ClassTypeResp.DataBean> classTypeList, SelectListener listener) {
        super(context);
        this.mContext = context;
        this.selectListener = listener;
        this.classTypeList = classTypeList;
        rootView = View.inflate(mContext, R.layout.pop_filter_sort, null);
        setFocusable(false);
        setOutsideTouchable(false);
        setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        setHeight(ViewPager.LayoutParams.WRAP_CONTENT);
        setContentView(rootView);
        setBackgroundDrawable(new BitmapDrawable());
        findComponent();
        initComponent();
    }

    private void findComponent() {
        listView = (ListView) rootView.findViewById(R.id.listView);
    }

    private void initComponent() {
        rootView.findViewById(R.id.outView).setOnClickListener(this);
        adapter = new AnswerTypeAdapter(mContext, classTypeList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setTempIndex(position);
                selectListener.onSelected(adapter.getItem(position));
                dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.outView){
            dismiss();
        }
    }

    public void show(View v) {
        showAsDropDown(v);
    }
}
