package com.weima.aishangyi.entity;

/**
 * 作者：cgy on 17/3/27 22:58
 * 邮箱：593960111@qq.com
 */
public class MyTeacherBean {

    private String icon;
    private long id;
    private String name;
    private String nickname;
    private int is_follow;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }
}
