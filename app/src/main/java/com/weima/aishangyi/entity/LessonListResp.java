package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class LessonListResp extends CommonEntity{
    private DataBean data;

    public static class DataBean extends CommonListData{
        private List<LessonBean> data;

        public List<LessonBean> getData() {
            return data;
        }

        public void setData(List<LessonBean> data) {
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
