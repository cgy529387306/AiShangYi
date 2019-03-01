package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
public class RecommendTeacherResp {
    private int code;
    private List<TeacherBean> data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<TeacherBean> getData() {
        return data;
    }

    public void setData(List<TeacherBean> data) {
        this.data = data;
    }
}
