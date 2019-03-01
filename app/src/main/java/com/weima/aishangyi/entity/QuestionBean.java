package com.weima.aishangyi.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class QuestionBean implements Serializable{
    private long id;
    private String problem;
    private int item;
    private double price;
    private int is_public;
    private long user_id;
    private int process;
    private int status;
    private long best_answer;
    private String created_at;
    private String updated_at;
    private int answer_count;
    private int thumb_count;
    private int see_count;
    private AnswerBean best;
    private User user;
    private SeeBean is_see;
    private ThumbBean is_thumb;
    private List<AnswerBean> answer;

    public List<AnswerBean> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerBean> answer) {
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIs_public() {
        return is_public;
    }

    public void setIs_public(int is_public) {
        this.is_public = is_public;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getBest_answer() {
        return best_answer;
    }

    public void setBest_answer(long best_answer) {
        this.best_answer = best_answer;
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

    public int getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    public int getThumb_count() {
        return thumb_count;
    }

    public void setThumb_count(int thumb_count) {
        this.thumb_count = thumb_count;
    }

    public int getSee_count() {
        return see_count;
    }

    public void setSee_count(int see_count) {
        this.see_count = see_count;
    }

    public AnswerBean getBest() {
        return best;
    }

    public void setBest(AnswerBean best) {
        this.best = best;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SeeBean getIs_see() {
        return is_see;
    }

    public void setIs_see(SeeBean is_see) {
        this.is_see = is_see;
    }

    public ThumbBean getIs_thumb() {
        return is_thumb;
    }

    public void setIs_thumb(ThumbBean is_thumb) {
        this.is_thumb = is_thumb;
    }


}
