package com.weima.aishangyi.entity;

import java.io.Serializable;

/**
 * 作者：cgy on 17/3/11 17:42
 * 邮箱：593960111@qq.com
 */
public class AnswerBean implements Serializable{
    private long id;
    private String answer;
    private int status;
    private long question_id;
    private long teacher_id;
    private String created_at;
    private String updated_at;

    private TeacherBean teacher;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    public long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(long teacher_id) {
        this.teacher_id = teacher_id;
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

    public TeacherBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public static class TeacherBean implements Serializable{
        private long id;
        private String icon;
        private String nickname;
        private String good_at;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGood_at() {
            return good_at;
        }

        public void setGood_at(String good_at) {
            this.good_at = good_at;
        }
    }
}
