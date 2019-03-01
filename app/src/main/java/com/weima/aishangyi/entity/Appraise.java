package com.weima.aishangyi.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class Appraise implements Serializable{
    private String appraise;
    private float star;
    private User user;

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
