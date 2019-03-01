package com.weima.aishangyi.entity;

import java.util.List;

/**
 * 作者：cgy on 17/2/10 23:54
 * 邮箱：593960111@qq.com
 */
public class InfoResp extends CommonEntity{
    private DataBean data;

    public static class DataBean extends CommonListData{
        private List<InfoBean> data;

        public List<InfoBean> getData() {
            return data;
        }

        public void setData(List<InfoBean> data) {
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
