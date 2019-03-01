package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseWebViewActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ArticleBean;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近的人所有性别的adapter
 * Created by hanj on 14-9-25.
 */
public class ProblemAdapter extends BaseAdapter {
    private Context context;
    private List<ArticleBean> dataList = new ArrayList<>();

    public ProblemAdapter(Context context, List<ArticleBean> list) {
        this.context = context;
        this.dataList = list;
    }

    public void updataList(List<ArticleBean> list){
        this.dataList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_problem, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ArticleBean articleBean = dataList.get(position);
        holder.tv_title.setText(ProjectHelper.getCommonText(articleBean.getTitle()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ProjectConstants.BundleExtra.KEY_WEB_DETAIL_URL, ProjectConstants.Url.ARTICLE_DETAIL+articleBean.getId());
                bundle.putBoolean(ProjectConstants.BundleExtra.KEY_IS_SET_TITLE, true);
                bundle.putString(ProjectConstants.BundleExtra.KEY_WEB_DETAIL_TITLE,articleBean.getTitle());
                NavigationHelper.startActivity((Activity) context, BaseWebViewActivity.class, bundle, false);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tv_title;
    }
}
