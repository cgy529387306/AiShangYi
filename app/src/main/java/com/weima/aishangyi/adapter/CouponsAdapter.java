package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.ClassroomOrderDetailAcitity;
import com.weima.aishangyi.activity.EvaluateActivity;
import com.weima.aishangyi.entity.CouponBean;
import com.weima.aishangyi.entity.LunboEntity;
import com.weima.aishangyi.entity.QuestionBean;
import com.weima.aishangyi.utils.NavigationHelper;

import java.util.ArrayList;
import java.util.List;

public class CouponsAdapter extends BaseAdapter {
    private Activity activity;
    private List<CouponBean> dataList = new ArrayList<>();

    public CouponsAdapter(Activity act) {
        this.activity = act;
    }

    public void addMore(List<CouponBean> list){
        if (Helper.isNotEmpty(list)){
            dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        dataList.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public CouponBean getItem(int position) {
        return dataList.get(position);
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_coupuns, null);
            holder.imv_coupon_bg = (ImageView) convertView.findViewById(R.id.imv_coupon_bg);
            holder.txv_money = (TextView) convertView.findViewById(R.id.txv_money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CouponBean entity = dataList.get(position);
        holder.txv_money.setText("¥"+entity.getMoney());
        holder.imv_coupon_bg.setImageResource(entity.getStatus()==0?R.drawable.ic_coupuns_red:R.drawable.ic_coupuns_grey);
        return convertView;
    }

    static class ViewHolder {
        ImageView imv_coupon_bg;
        TextView txv_money;
    }
}
