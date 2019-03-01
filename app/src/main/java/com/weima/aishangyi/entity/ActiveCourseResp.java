package com.weima.aishangyi.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/10.
 */
public class ActiveCourseResp {
    private int code;
    private Map<String, Object> data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
