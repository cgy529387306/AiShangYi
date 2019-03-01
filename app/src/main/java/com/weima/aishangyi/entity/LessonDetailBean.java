package com.weima.aishangyi.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class LessonDetailBean implements Serializable{
    private long id;
    private String name;
    private long item;
    private int lesson_item;
    private String lesson_brief;
    private String lesson_detail;
    private long teacher_id;
    private int status;
    private String address;
    private double longitude;
    private int collect_count;
    private double latitude;
    private String created_at;
    private String updated_at;
    private int number;
    private String icon;
    private TeacherBean teacher;
    private int is_collect;
    private List<String> images;

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

    public long getItem() {
        return item;
    }

    public void setItem(long item) {
        this.item = item;
    }

    public int getLesson_item() {
        return lesson_item;
    }

    public void setLesson_item(int lesson_item) {
        this.lesson_item = lesson_item;
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

    public long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public TeacherBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public static class TeacherBean implements Serializable{
        private long id;
        private String nickname;
        private String icon;
        private String detail;
        private String major;
        private String a_latitude;
        private String a_longitude;
        private int is_test;
        private String address;
        private long org_id;
        private double distance;
        private float star;
        private String org;
        private List<TimeBean> lesson_time;
        private List<?> appraise;

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

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
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

        public int getIs_test() {
            return is_test;
        }

        public void setIs_test(int is_test) {
            this.is_test = is_test;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getOrg_id() {
            return org_id;
        }

        public void setOrg_id(long org_id) {
            this.org_id = org_id;
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

        public List<TimeBean> getLesson_time() {
            return lesson_time;
        }

        public void setLesson_time(List<TimeBean> lesson_time) {
            this.lesson_time = lesson_time;
        }

        public List<?> getAppraise() {
            return appraise;
        }

        public void setAppraise(List<?> appraise) {
            this.appraise = appraise;
        }
    }
}
