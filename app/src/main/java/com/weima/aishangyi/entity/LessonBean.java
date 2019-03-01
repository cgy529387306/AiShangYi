package com.weima.aishangyi.entity;

import com.mb.android.utils.Helper;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class LessonBean implements Serializable{
    private long id;
    private String name;
    private String number;
    private String icon;
    private int lesson_item;
    private String nickname;
    private String org;
    private double distance;
    private String star;
    private String lesson_brief;
    private String lesson_detail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        if (Helper.isEmpty(number)){
            return "0";
        }
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getLesson_item() {
        return lesson_item;
    }

    public void setLesson_item(int lesson_item) {
        this.lesson_item = lesson_item;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getStar() {
        if (Helper.isEmpty(star)){
            return "0";
        }
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getLesson_brief() {
        return lesson_brief;
    }

    public void setLesson_brief(String lesson_brief) {
        this.lesson_brief = lesson_brief;
    }


    public String getLesson_detail() {
        return lesson_detail;
    }

    public void setLesson_detail(String lesson_detail) {
        this.lesson_detail = lesson_detail;
    }

    public static class ItemBean {
        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
