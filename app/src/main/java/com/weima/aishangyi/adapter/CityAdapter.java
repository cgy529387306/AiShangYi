package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CityBean;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：cgy on 17/2/23 00:15
 * 邮箱：593960111@qq.com
 */
public class CityAdapter extends BaseAdapter{
    private Activity activity;
    private List<CityBean> dataList = new ArrayList<>();
    public CityAdapter(Activity act, List<CityBean> list) {
        this.activity = act;
        this.dataList = list;
    }

    public void updataList(List<CityBean> list){
        this.dataList = list;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (Helper.isNull(convertView)) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_base_text, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CityBean entity = dataList.get(position);
        holder.textView.setText(ProjectHelper.getCommonText(entity.getCity()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ProjectConstants.BundleExtra.KEY_CITY_NAME, ProjectHelper.getCommonText(entity.getCity()));
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });
        return convertView;
    }

    static class ViewHolder{
        TextView textView;
    }
}
