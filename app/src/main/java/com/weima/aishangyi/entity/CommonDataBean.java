package com.weima.aishangyi.entity;

/**
 * 作者：cgy on 17/3/12 14:21
 * 邮箱：593960111@qq.com
 */
public class CommonDataBean {


    private String id;
    private String price;
    private String lesson_discount;
    private String answer_discount;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLesson_discount() {
        return lesson_discount;
    }

    public void setLesson_discount(String lesson_discount) {
        this.lesson_discount = lesson_discount;
    }

    public String getAnswer_discount() {
        return answer_discount;
    }

    public void setAnswer_discount(String answer_discount) {
        this.answer_discount = answer_discount;
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
}
