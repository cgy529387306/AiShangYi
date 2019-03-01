package com.weima.aishangyi.entity;

import com.mb.android.utils.Helper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/13 0013.
 */
public class ActivityBean implements Serializable{

    /**
     * id : 1
     * title : eqewq
     * number : 50
     * address : dasdasdas
     * price : 50
     * url : http://123123123
     * start_time : 1465465465
     * end_time : 1465465465
     * close_time : 1465463212
     * status : 0
     * images : []
     * content : 313123123131
     * created_at : 321312321
     * updated_at : 321312321
     * type : 1
     * has_number : 0
     * start : 18825988
     * time_status : 已结束
     * end_status : 报名已结束
     * is_collect : false
     */

    private long id;
    private String title;
    private int number;
    private String address;
    private double price;
    private String url;
    private long start_time;
    private long end_time;
    private long close_time;
    private int status;
    private String content;
    private String created_at;
    private String updated_at;
    private int type;
    private int has_number;
    private long start;
    private String time_status;
    private String end_status;
    private int is_collect;
    private List<String> images;
    private double latitude;
    private double longitude;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getClose_time() {
        return close_time;
    }

    public void setClose_time(long close_time) {
        this.close_time = close_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHas_number() {
        return has_number;
    }

    public void setHas_number(int has_number) {
        this.has_number = has_number;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
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

    public int is_collect() {
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

    public double getLatitude() {
        if (Helper.isEmpty(latitude)){
            return 0;
        }
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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
}
