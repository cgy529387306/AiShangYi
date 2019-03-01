package com.weima.aishangyi.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.AllSexAdapter;

/**
 * Created by cgy
 */
public class AllSexPop extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private ListView listView;
    private Context mContext;
    private AllSexAdapter adapter;

    private SelectListener selectListener;
    public interface SelectListener {
        public void onSelected(int type);
    }

    public AllSexPop(Context context, SelectListener listener) {
        super(context);
        this.mContext = context;
        this.selectListener = listener;
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
        adapter = new AllSexAdapter(mContext, null);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setTempIndex(position);
                selectListener.onSelected(position);
                dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.outView){
            selectListener.onSelected(-1);
            dismiss();
        }
    }

    public void show(View v) {
        showAsDropDown(v);
    }
}
