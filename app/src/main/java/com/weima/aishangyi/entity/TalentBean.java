package com.weima.aishangyi.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class TalentBean implements Serializable{
    private long id;
    private String content;
    private int comment_count;
    private int thumb_up;
    private long user_id;
    private int type;
    private String address;
    private int status;
    private String created_at;
    private String updated_at;
    private ThumbBean is_thumb;
    private List<String> images;
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getThumb_up() {
        return thumb_up;
    }

    public void setThumb_up(int thumb_up) {
        this.thumb_up = thumb_up;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ThumbBean getIs_thumb() {
        return is_thumb;
    }

    public void setIs_thumb(ThumbBean is_thumb) {
        this.is_thumb = is_thumb;
    }
}
