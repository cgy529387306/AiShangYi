package com.weima.aishangyi.entity;

import java.io.Serializable;

/**
 * 作者：cgy on 17/4/9 22:37
 * 邮箱：593960111@qq.com
 */
public class MsgBean implements Serializable{
    private String title;
    private String content;
    private String id;
    private long created_at;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
}
