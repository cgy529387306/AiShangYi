package com.weima.aishangyi.pop;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.ClassFirstAdapter;
import com.weima.aishangyi.adapter.ClassSecondAdapter;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ClassTypeResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgy
 */
public class SelectFilterTypePop extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private Context mContext;
    private ListView listFirst;
    private GridView listSecond;
    private ClassFirstAdapter firstAdapter;
    private ClassSecondAdapter secondAdapter;
    private List<ClassTypeResp.DataBean> firstBeans = new ArrayList<>();
    private List<ClassTypeResp.DataBean.ChildrenBean> secondBeans = new ArrayList<>();

    private SelectListener selectListener;
    public interface SelectListener {
        public void onSelected(long type,String name);
    }

    public SelectFilterTypePop(Context context, SelectListener listener) {
        super(context);
        this.mContext = context;
        this.selectListener = listener;
        rootView = View.inflate(mContext, R.layout.pop_filter_type, null);
        setFocusable(false);
        setOutsideTouchable(false);
        setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        setHeight(ViewPager.LayoutParams.WRAP_CONTENT);
        setContentView(rootView);
        setBackgroundDrawable(new BitmapDrawable());
        initComponent();
        findComponent();

    }

    private void findComponent() {
        listFirst = (ListView) rootView.findViewById(R.id.listFirst);
        listSecond = (GridView) rootView.findViewById(R.id.listSecond);
        firstAdapter = new ClassFirstAdapter(mContext, firstBeans);
        secondAdapter = new ClassSecondAdapter(mContext, secondBeans);
        rootView.findViewById(R.id.outView).setOnClickListener(this);
        listFirst.setAdapter(firstAdapter);
        listSecond.setAdapter(secondAdapter);

        listFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - listFirst.getHeaderViewsCount();
                firstAdapter.setTempIndex(index);
                ClassTypeResp.DataBean dataBean = firstBeans.get(index);
                secondBeans = dataBean.getChildren();
                secondAdapter.updateList(secondBeans);
            }
        });

        listSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassTypeResp.DataBean.ChildrenBean childrenBean = secondAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(ProjectConstants.BundleExtra.KEY_CLASS_TYPE_ID, childrenBean);
                selectListener.onSelected(childrenBean.getId(),childrenBean.getName());
            }
        });
    }

    private void initComponent() {
        try {
            String response = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CLASS_TYPE);
            if (Helper.isNotEmpty(response)){
                ClassTypeResp entity = JsonHelper.fromJson(response, ClassTypeResp.class);
                if ("200".equals(entity.getCode())) {
                    if (Helper.isNotEmpty(entity.getData())){
                        firstBeans = entity.getData();
                        if (Helper.isNotEmpty(entity.getData().get(0).getChildren())){
                            secondBeans = entity.getData().get(0).getChildren();
                        }
                        firstAdapter.updateList(firstBeans);
                        if (Helper.isNotEmpty(entity.getData().get(0).getChildren())){
                            secondAdapter.updateList(entity.getData().get(0).getChildren());
                        }
                    }
                } else {
                    ToastHelper.showToast(entity.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
