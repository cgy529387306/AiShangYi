package com.weima.aishangyi.entity;

/**
 * 作者：cgy on 17/2/9 00:48
 * 邮箱：593960111@qq.com
 */
public class FollowBean {

    /**
     * id : 8
     * follow_id : 6
     * type : 0
     * user_id : 1
     * device : 1
     * created_at : 1486571992
     * updated_at : 1486571992
     * is_follow : 0
     * user : {"id":1,"appid":"","phone":"18650480850","nickname":"蔡教授","is_third":0,"icon":"http://aishangyi.h5h5h5.cn/file/20170105/SaYH8bcc420170105082221.jpg","image_cover":"http://aishangyi.h5h5h5.cn/file/20170106/a3dd7e80320170106063337.jpg","video_cover":"http://aishangyi.h5h5h5.cn/file/20170106/IB0c1b68e20170106063326.jpg","major":"英语","amount":0,"name":"蔡桂有","alipay":null,"username":null,"bank_card":null,"bank_name":null,"city":null,"age":13,"sex":0,"detail":"摸摸你","idCertification":1,"edCertification":1,"qlCertification":1,"star":0,"status":0,"collect_info":[],"collect_active":[],"my_student":[8],"longitude":null,"latitude":null,"created_at":"1482074054","updated_at":"1486565142","birth":"1990-10-05","bank_user":null,"address":"福建农林大学(金山校区)","interest":"打篮球","is_test":1,"signature":"我就是神","test_price":6666,"message":"dddd66666sdddddd摸摸图监控","test_brief":"哈哈哈哈啦啦","lesson_time":["0","1","2"],"album":["http://aishangyi.h5h5h5.cn/file/20170106/3kJCd695120170106024233.jpg","http://aishangyi.h5h5h5.cn/file/20170106/lgp13a28920170106024233.jpg","http://aishangyi.h5h5h5.cn/file/20170106/GtHGc959120170106024231.jpg","http://aishangyi.h5h5h5.cn/file/20170106/BVUw7cfdf20170106024229.jpg"],"bind_phone":"18650480850","fans_count":2,"follow_count":2}
     */

    private long id;
    private long follow_id;
    private int type;
    private int user_id;
    private int device;
    private String created_at;
    private String updated_at;
    private int is_follow;
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(long follow_id) {
        this.follow_id = follow_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
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

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
