package com.weima.aishangyi.entity;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/13.
 */
public class StuCategoryEntity {
    private long id;
    private String name;
    private int parent_id;
    private int is_cover;
    private int status;
    private String icon;
    /*private Date created_at;
    private Date updated_at;*/

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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getIs_cover() {
        return is_cover;
    }

    public void setIs_cover(int is_cover) {
        this.is_cover = is_cover;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /*public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }*/
}
