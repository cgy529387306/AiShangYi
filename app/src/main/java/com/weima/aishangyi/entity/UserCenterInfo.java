package com.weima.aishangyi.entity;

/**
 * 作者：cgy on 17/2/15 23:42
 * 邮箱：593960111@qq.com
 */
public class UserCenterInfo {
    private long id;
    private String icon;
    private String signature;
    private String interest;
    private String nickname;
    private int fans_count;
    private int follow_count;
    private int is_follow;
    private String city;
    private int device;
    private int stu_teach_num;
    private String birth;
    private int sex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public int getStu_teach_num() {
        return stu_teach_num;
    }

    public void setStu_teach_num(int stu_teach_num) {
        this.stu_teach_num = stu_teach_num;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
