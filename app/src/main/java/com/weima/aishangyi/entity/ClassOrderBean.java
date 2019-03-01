package com.weima.aishangyi.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/6 0006.
 */
public class ClassOrderBean implements Serializable{
    private long id;
    private long lesson_id;
    private int number;
    private long user_id;
    private int status;
    private String order_no;
    private long teacher_id;
    private TeacherBean teacher;
    private LessonBean lesson;
    private TimeBean appoint_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public TeacherBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public LessonBean getLesson() {
        return lesson;
    }

    public void setLesson(LessonBean lesson) {
        this.lesson = lesson;
    }

    public static class TeacherBean implements Serializable{
        private long id;
        private String nickname;
        private String icon;
        private String interest;
        private String address;

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

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class LessonBean implements Serializable{
        private long id;
        private String name;
        private String icon;
        private String lesson_brief;
        private long lesson_item;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public long getLesson_item() {
            return lesson_item;
        }

        public void setLesson_item(long lesson_item) {
            this.lesson_item = lesson_item;
        }

        public String getLesson_brief() {
            return lesson_brief;
        }

        public void setLesson_brief(String lesson_brief) {
            this.lesson_brief = lesson_brief;
        }
    }

    public TimeBean getAppoint_time() {
        return appoint_time;
    }

    public void setAppoint_time(TimeBean appoint_time) {
        this.appoint_time = appoint_time;
    }
}
