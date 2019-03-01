package com.weima.aishangyi.entity;

import com.mb.android.utils.Helper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/18.
 */
public class TeacherDetailEntity implements Serializable{
    private double distance;
    private String detail;
    private String phone;
    private String appid;
    private double amount;
    private long id;
    private String interest;
    private int age;
    private String name;
    private String created_at;
    private String signature;
    private String major;
    private String icon;
    private int status;
    private String nickname;
    private String star;
    private String address;
    private int device;
    private int is_third;
    private int is_collect;
    private int is_test;
    private List<LessonBean> lesson;
    private List<TimeBean> lesson_time;
    private String a_latitude;
    private String a_longitude;
    private String image_cover;
    private String org;
    private long org_id;
    private List<Appraise> appraise;
    private List<String> album;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public int getIs_third() {
        return is_third;
    }

    public void setIs_third(int is_third) {
        this.is_third = is_third;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getIs_test() {
        return is_test;
    }

    public void setIs_test(int is_test) {
        this.is_test = is_test;
    }

    public List<LessonBean> getLesson() {
        return lesson;
    }

    public void setLesson(List<LessonBean> lesson) {
        this.lesson = lesson;
    }

    public List<TimeBean> getLesson_time() {
        return lesson_time;
    }

    public void setLesson_time(List<TimeBean> lesson_time) {
        this.lesson_time = lesson_time;
    }

    public String getA_latitude() {
        return a_latitude;
    }

    public void setA_latitude(String a_latitude) {
        this.a_latitude = a_latitude;
    }

    public String getA_longitude() {
        return a_longitude;
    }

    public void setA_longitude(String a_longitude) {
        this.a_longitude = a_longitude;
    }

    public String getImage_cover() {
        return image_cover;
    }

    public void setImage_cover(String image_cover) {
        this.image_cover = image_cover;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public long getOrg_id() {
        return org_id;
    }

    public void setOrg_id(long org_id) {
        this.org_id = org_id;
    }

    public List<Appraise> getAppraise() {
        return appraise;
    }

    public void setAppraise(List<Appraise> appraise) {
        this.appraise = appraise;
    }


    public List<String> getAlbum() {
        return album;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }
}
