package com.weima.aishangyi.entity;

import com.mb.android.utils.Helper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/25 0025.
 */
public class User implements Serializable {

    /**
     * id : 1
     * appid :
     * phone : 18650480850
     * nickname : 蔡教授
     * is_third : 0
     * icon : http://aishangyi.h5h5h5.cn/file/20170105/SaYH8bcc420170105082221.jpg
     * image_cover : http://aishangyi.h5h5h5.cn/file/20170106/a3dd7e80320170106063337.jpg
     * video_cover : http://aishangyi.h5h5h5.cn/file/20170106/IB0c1b68e20170106063326.jpg
     * major : 英语
     * amount : 0
     * name : 蔡桂有
     * alipay : null
     * username : null
     * bank_card : null
     * bank_name : null
     * city : null
     * age : 13
     * sex : 0
     * detail : 摸摸你
     * idCertification : 1
     * edCertification : 1
     * qlCertification : 1
     * star : 0
     * status : 0
     * collect_info : []
     * collect_active : []
     * my_student : [8]
     * longitude : null
     * latitude : null
     * created_at : 1482074054
     * updated_at : 1486565142
     * birth : 1990-10-05
     * bank_user : null
     * address : 福建农林大学(金山校区)
     * interest : 打篮球
     * is_test : 1
     * signature : 我就是神
     * test_price : 6666
     * message : dddd66666sdddddd摸摸图监控
     * test_brief : 哈哈哈哈啦啦
     * lesson_time : ["0","1","2"]
     * album : ["http://aishangyi.h5h5h5.cn/file/20170106/3kJCd695120170106024233.jpg","http://aishangyi.h5h5h5.cn/file/20170106/lgp13a28920170106024233.jpg","http://aishangyi.h5h5h5.cn/file/20170106/GtHGc959120170106024231.jpg","http://aishangyi.h5h5h5.cn/file/20170106/BVUw7cfdf20170106024229.jpg"]
     * bind_phone : 18650480850
     * fans_count : 2
     * follow_count : 0
     */
    private long id;
    private String appid;
    private String phone;
    private String nickname;
    private int is_third;
    private String icon;
    private String image_cover;
    private String video_cover;
    private String major;
    private double amount;
    private String name;
    private String alipay;
    private String username;
    private String bank_card;
    private String bank_name;
    private String city;
    private int age;
    private int sex;//1男 2女 0未知
    private String detail;
    private int idCertification;
    private int edCertification;
    private int qlCertification;
    private float star;
    private float status;
    private double longitude;
    private double latitude;
    private String created_at;
    private String updated_at;
    private String birth;
    private String address;
    private String interest;
    private int is_test;
    private String signature;
    private double test_price;
    private String message;
    private String test_brief;
    private String bind_phone;
    private int fans_count;
    private int follow_count;
    private int device;
    private double distance;
//    private List<?> collect_info;
//    private List<?> collect_active;
//    private List<Integer> my_student;
//    private List<String> lesson_time;
    private List<String> album;
    private int is_follow;//0未关注，1关注，2互相关注

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIs_third() {
        return is_third;
    }

    public void setIs_third(int is_third) {
        this.is_third = is_third;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImage_cover() {
        return image_cover;
    }

    public void setImage_cover(String image_cover) {
        this.image_cover = image_cover;
    }

    public String getVideo_cover() {
        return video_cover;
    }

    public void setVideo_cover(String video_cover) {
        this.video_cover = video_cover;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getIdCertification() {
        return idCertification;
    }

    public void setIdCertification(int idCertification) {
        this.idCertification = idCertification;
    }

    public int getEdCertification() {
        return edCertification;
    }

    public void setEdCertification(int edCertification) {
        this.edCertification = edCertification;
    }

    public int getQlCertification() {
        return qlCertification;
    }

    public void setQlCertification(int qlCertification) {
        this.qlCertification = qlCertification;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public float getStatus() {
        return status;
    }

    public void setStatus(float status) {
        this.status = status;
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

    public double getLatitude() {
        if (Helper.isEmpty(latitude)){
            return 0;
        }
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public int getIs_test() {
        return is_test;
    }

    public void setIs_test(int is_test) {
        this.is_test = is_test;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public double getTest_price() {
        return test_price;
    }

    public void setTest_price(double test_price) {
        this.test_price = test_price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTest_brief() {
        return test_brief;
    }

    public void setTest_brief(String test_brief) {
        this.test_brief = test_brief;
    }

    public String getBind_phone() {
        return bind_phone;
    }

    public void setBind_phone(String bind_phone) {
        this.bind_phone = bind_phone;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public List<String> getAlbum() {
        return album;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}