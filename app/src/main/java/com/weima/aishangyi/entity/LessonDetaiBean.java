package com.weima.aishangyi.entity;

import com.mb.android.utils.Helper;

import java.util.List;

/**
 * 作者：cgy on 17/2/21 22:10
 * 邮箱：593960111@qq.com
 */
public class LessonDetaiBean {
    private long id;
    private String name;
    private int item;
    private int lesson_item;
    private int is_student;
    private double student_price;
    private int is_teacher;
    private double teacher_price;
    private String lesson_brief;
    private String lesson_detail;
    private long teacher_id;
    private int status;
    private String address;
    private double longitude;
    private double latitude;
    private String created_at;
    private String updated_at;
    private String icon;
    private double distance;
    private TeacherDetailEntity teacher;
    private int is_collect;
    private List<String> images;
//    private List<?> appraise;

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

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getLesson_item() {
        return lesson_item;
    }

    public void setLesson_item(int lesson_item) {
        this.lesson_item = lesson_item;
    }

    public int getIs_student() {
        return is_student;
    }

    public void setIs_student(int is_student) {
        this.is_student = is_student;
    }

    public double getStudent_price() {
        return student_price;
    }

    public void setStudent_price(double student_price) {
        this.student_price = student_price;
    }

    public int getIs_teacher() {
        return is_teacher;
    }

    public void setIs_teacher(int is_teacher) {
        this.is_teacher = is_teacher;
    }

    public double getTeacher_price() {
        return teacher_price;
    }

    public void setTeacher_price(double teacher_price) {
        this.teacher_price = teacher_price;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public TeacherDetailEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDetailEntity teacher) {
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
        if (Helper.isEmpty(longitude)){
            return 0;
        }
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        if (Helper.isEmpty(latitude)){
            return 0;
        }
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }



//    public List<?> getAppraise() {
//        return appraise;
//    }
//
//    public void setAppraise(List<?> appraise) {
//        this.appraise = appraise;
//    }

}
