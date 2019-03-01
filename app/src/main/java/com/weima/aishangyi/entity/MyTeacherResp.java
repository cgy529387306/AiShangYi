package com.weima.aishangyi.entity;

import java.util.List;

/**
 * 作者：cgy on 17/2/9 00:44
 * 邮箱：593960111@qq.com
 */
public class MyTeacherResp extends CommonEntity{
    private DataBean data;
    public static class DataBean extends CommonListData{
        private List<MyTeacherBean> data;

        public List<MyTeacherBean> getData() {
            return data;
        }

        public void setData(List<MyTeacherBean> data) {
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
