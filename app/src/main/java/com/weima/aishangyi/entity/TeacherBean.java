package com.weima.aishangyi.entity;

import java.io.Serializable;

/**
 * 作者：cgy on 17/2/21 02:15
 * 邮箱：593960111@qq.com
 */
public class TeacherBean implements Serializable {
    private long id;
    private String nickname;
    private String icon;
    private String detail;
    private double distance;
    private float star;
    private String org;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
