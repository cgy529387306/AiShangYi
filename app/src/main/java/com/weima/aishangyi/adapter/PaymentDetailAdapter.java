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
import com.weima.aishangyi.entity.LunboEntity;
import com.weima.aishangyi.entity.PaymentBean;
import com.weima.aishangyi.entity.QuestionBean;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetailAdapter extends BaseAdapter{
	private Activity activity;
	private List<PaymentBean> dataList = new ArrayList<>();
	private int type = 1;
	public PaymentDetailAdapter(Activity act,int type) {
		this.activity = act;
		this.type = type;
	}

	public void addMore(List<PaymentBean> list){
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
	public PaymentBean getItem(int position) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.item_paymentdetail, null);
			holder.txv_type = (TextView) convertView.findViewById(R.id.txv_type);
			holder.txv_time = (TextView) convertView.findViewById(R.id.txv_time);
			holder.txv_money = (TextView) convertView.findViewById(R.id.txv_money);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		PaymentBean paymentBean = dataList.get(position);
		holder.txv_time.setText(Helper.long2DateString(paymentBean.getCreated_at() * 1000, "yyyy-MM-dd HH:mm"));
		holder.txv_money.setText(type==2?"-"+paymentBean.getMoney()+"元":"+"+paymentBean.getMoney());
		if (paymentBean.getType() == 1){
			holder.txv_type.setText("课程支付");
		}else if (paymentBean.getType() == 2){
			holder.txv_type.setText("课程退款");
		}else if (paymentBean.getType() == 3){
			holder.txv_type.setText("活动支付");
		}else if (paymentBean.getType() == 4){
			holder.txv_type.setText("提问题支出");
		}else if (paymentBean.getType() == 6){
			holder.txv_type.setText("取现金");
		}else if (paymentBean.getType() == 7){
			holder.txv_type.setText("充值");
		}else if (paymentBean.getType() == 8){
			holder.txv_type.setText("看答案支出");
		}
		return convertView;
	}
	
	static class ViewHolder{
		TextView txv_type;
		TextView txv_time;
		TextView txv_money;
	}
}
