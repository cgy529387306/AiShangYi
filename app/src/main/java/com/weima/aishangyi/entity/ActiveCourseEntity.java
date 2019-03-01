package com.weima.aishangyi.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/18.
 */
public class ActiveCourseEntity {
    private int id;
    private String title;
    private String address;
    private String price;
    private Date start_time;
    private Date end_time;
    private int close_time;
    private int type;
    private int number;
    private int has_number;
    private String time_status;
    private String end_status;
    private int is_collect;
    private String content;
    private Date created_at;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public int getClose_time() {
        return close_time;
    }

    public void setClose_time(int close_time) {
        this.close_time = close_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHas_number() {
        return has_number;
    }

    public void setHas_number(int has_number) {
        this.has_number = has_number;
    }

    public String getTime_status() {
        return time_status;
    }

    public void setTime_status(String time_status) {
        this.time_status = time_status;
    }

    public String getEnd_status() {
        return end_status;
    }

    public void setEnd_status(String end_status) {
        this.end_status = end_status;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
