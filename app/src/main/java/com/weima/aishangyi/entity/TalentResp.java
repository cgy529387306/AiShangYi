package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class TalentResp extends CommonEntity{
    private DataBean data;

    public static class DataBean extends CommonListData{
        private List<TalentBean> data;

        public List<TalentBean> getData() {
            return data;
        }

        public void setData(List<TalentBean> data) {
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
