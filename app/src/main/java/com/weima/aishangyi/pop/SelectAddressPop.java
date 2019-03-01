package com.weima.aishangyi.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.amap.api.services.core.PoiItem;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgy
 */
public class SelectAddressPop extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private Context mContext;
    private ListView listView;
    private SelectListener selectListener;
    private List<PoiItem> poiItems = new ArrayList<>();
    private int type = 1;
    public interface SelectListener {
        public void onSelected(PoiItem poiItem);
    }

    public SelectAddressPop(Context context, List<PoiItem> list, SelectListener listener) {
        super(context);
        this.mContext = context;
        this.selectListener = listener;
        this.poiItems = list;
        rootView = View.inflate(mContext, R.layout.pop_select_address, null);
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
        listView = (ListView) rootView.findViewById(R.id.listView);
    }

    private void initComponent() {
        AddressAdapter adapter = new AddressAdapter(mContext,poiItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int index = i-listView.getHeaderViewsCount();
                PoiItem poiItem = poiItems.get(index);
                selectListener.onSelected(poiItem);
            }
        });
        rootView.findViewById(R.id.outView).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.outView){
            dismiss();
        }
    }

    public void show(View v) {
        showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

}
