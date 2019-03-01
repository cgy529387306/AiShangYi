package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/18.
 */
public class ArticleResp extends CommonEntity{
    private DataBean data;

    public static class DataBean extends CommonListData{
        private List<ArticleBean> data;

        public List<ArticleBean> getData() {
            return data;
        }

        public void setData(List<ArticleBean> data) {
            this.data = data;
        }
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
