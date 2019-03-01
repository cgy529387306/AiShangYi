package com.weima.aishangyi.entity;

import java.io.Serializable;

/**
 * 作者：cgy on 17/2/10 23:53
 * 邮箱：593960111@qq.com
 */
public class InfoBean implements Serializable{

    private long id;
    private String title;
    private String image_url;
    private String content;
    private int collect_num;
    private int thumb_up;
    private int status;
    private int top;
    private int fine;
    private String created_at;
    private String updated_at;
    private int comment_count;
    private ThumbBean is_thumb;
    private int is_collect;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(int collect_num) {
        this.collect_num = collect_num;
    }

    public int getThumb_up() {
        return thumb_up;
    }

    public void setThumb_up(int thumb_up) {
        this.thumb_up = thumb_up;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
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

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public ThumbBean is_thumb() {
        return is_thumb;
    }

    public void setIs_thumb(ThumbBean is_thumb) {
        this.is_thumb = is_thumb;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }
}
