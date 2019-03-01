package com.weima.aishangyi.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class ThumbBean implements Serializable {
    private long thumb_id;
    private long user_id;

    public long getThumb_id() {
        return thumb_id;
    }

    public void setThumb_id(long thumb_id) {
        this.thumb_id = thumb_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
