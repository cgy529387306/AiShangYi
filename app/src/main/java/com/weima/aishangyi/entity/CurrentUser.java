package com.weima.aishangyi.entity;

import android.util.Log;

import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;

import java.util.List;

/**
 * 作者：cgy on 16/12/26 22:53
 * 邮箱：593960111@qq.com
 */
public class CurrentUser {

    /**
     * birth : null
     * detail : null
     * sex : null
     * bank_name : null
     * phone : 18650480850
     * collect_info : false
     * appid : 
     * edCertification : 0
     * city : null
     * amount : 0
     * id : 1
     * username : null
     * collect_active : false
     * age : 0
     * name : null
     * created_at : 1482074054
     * longitude : null
     * image_cover : null
     * major : null
     * qlCertification : 0
     * icon : null
     * status : 0
     * nickname : 18650480850
     * star : 0
     * video_cover : null
     * bank_user : null
     * bank_card : null
     * updated_at : 1482637343
     * alipay : null
     * address : null
     * my_student : null
     * is_third : 0
     * latitude : null
     * idCertification : 0
     */

    private String birth;
    private String detail;
    private String sex;
    private String bank_name;
    private String phone;
    private String appid;
    private int edCertification;
    private String city;
    private double amount;
    private long id;
    private String username;
    private int age;
    private String name;
    private String created_at;
    private String longitude;
    private String image_cover;
    private String major;
    private int qlCertification;
    private String icon;
    private int status;
    private String nickname;
    private int star;
    private String video_cover;
    private String bank_user;
    private String bank_card;
    private String updated_at;
    private String alipay;
    private String address;
    //    private String my_student;
    private int is_third;
    private String latitude;
    private int idCertification;
    private String interest;
    private String signature;
    private List<String> album;
    private int is_test;
    private String test_brief;
    private String test_price;
    private List<TimeBean> lesson_time;
    private String message;
    private String bind_phone;
    private int device;
    private String a_latitude;
    private String a_longitude;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<TimeBean> getLesson_time() {
        return lesson_time;
    }

    public void setLesson_time(List<TimeBean> lesson_time) {
        this.lesson_time = lesson_time;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAppid() {
        return appid;
    }

    public List<String> getAlbum() {
        return album;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public int getEdCertification() {
        return edCertification;
    }

    public void setEdCertification(int edCertification) {
        this.edCertification = edCertification;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getA_latitude() {
        return a_latitude==null?"0":a_latitude;
    }

    public void setA_latitude(String a_latitude) {
        this.a_latitude = a_latitude;
    }

    public String getA_longitude() {
        return a_longitude==null?"0":a_longitude;
    }

    public void setA_longitude(String a_longitude) {
        this.a_longitude = a_longitude;
    }

    public String getLongitude() {
        if (Helper.isEmpty(longitude)){
            return "0";
        }
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage_cover() {
        return image_cover;
    }

    public void setImage_cover(String image_cover) {
        this.image_cover = image_cover;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getQlCertification() {
        return qlCertification;
    }

    public void setQlCertification(int qlCertification) {
        this.qlCertification = qlCertification;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getVideo_cover() {
        return video_cover;
    }

    public void setVideo_cover(String video_cover) {
        this.video_cover = video_cover;
    }

    public String getBank_user() {
        return bank_user;
    }

    public void setBank_user(String bank_user) {
        this.bank_user = bank_user;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBind_phone() {
        if (Helper.isEmpty(bind_phone)){
            return phone;
        }else{
            return bind_phone;
        }
    }

    public void setBind_phone(String bind_phone) {
        this.bind_phone = bind_phone;
    }
//    public String getMy_student() {
//        return my_student;
//    }
//
//    public void setMy_student(String my_student) {
//        this.my_student = my_student;
//    }

    public int getIs_third() {
        return is_third;
    }

    public void setIs_third(int is_third) {
        this.is_third = is_third;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        if (Helper.isEmpty(latitude)){
            return "0";
        }
        return latitude;
    }

    public int getIdCertification() {
        return idCertification;
    }

    public void setIdCertification(int idCertification) {
        this.idCertification = idCertification;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    public int getIs_test() {
        return is_test;
    }

    public void setIs_test(int is_test) {
        this.is_test = is_test;
    }

    public String getTest_brief() {
        return test_brief;
    }

    public void setTest_brief(String test_brief) {
        this.test_brief = test_brief;
    }

    public String getTest_price() {
        return test_price;
    }

    public void setTest_price(String test_price) {
        this.test_price = test_price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    //region 单例
    private static final String TAG = CurrentUser.class.getSimpleName();
    private static final String USER = "userinfo";
    private static CurrentUser me;
    /**
     * 单例
     * @return 当前用户对象
     */
    public static CurrentUser getInstance() {
        if (me == null) {
            me = new CurrentUser();
        }
        return me;
    }
    /**
     * 出生
     * <p>尼玛！终于出生了！！！</p>
     * <p>调用此方法查询是否登录过</p>
     * @return 出生与否
     */
    public boolean born() {
        String json = PreferencesHelper.getInstance().getString(USER);
        me = JsonHelper.fromJson(json, CurrentUser.class);
        return me != null;
    }

    public boolean born(CurrentUser entity) {
        boolean born = false;
        String json = "";
        if (entity != null) {
            me.setId(entity.getId());
            me.setName(entity.getName());
            me.setNickname(entity.getNickname());
            me.setUsername(entity.getUsername());
            me.setBirth(entity.getBirth());
            me.setDetail(entity.getDetail());
            me.setSex(entity.getSex());
            me.setBank_name(entity.getBank_name());
            me.setLongitude(entity.getLongitude());
            me.setLatitude(entity.getLatitude());
            me.setPhone(entity.getPhone());
            me.setAppid(entity.getAppid());
            me.setEdCertification(entity.getEdCertification());
            me.setCity(entity.getCity());
            me.setAmount(entity.getAmount());
            me.setAge(entity.getAge());
            me.setCreated_at(entity.getCreated_at());
            me.setImage_cover(entity.getImage_cover());
            me.setMajor(entity.getMajor());
            me.setQlCertification(entity.getQlCertification());
            me.setIcon(entity.getIcon());
            me.setVideo_cover(entity.getVideo_cover());
            me.setBank_user(entity.getBank_user());
            me.setBank_card(entity.getBank_card());
            me.setUpdated_at(entity.getUpdated_at());
            me.setAlipay(entity.getAlipay());
            me.setAddress(entity.getAddress());
//            me.setMy_student(entity.getMy_student());
            me.setIs_third(entity.getIs_third());
            me.setIdCertification(entity.getIdCertification());
            me.setInterest(entity.getInterest());
            me.setSignature(entity.getSignature());
            me.setAlbum(entity.getAlbum());
            me.setIs_test(entity.getIs_test());
            me.setTest_brief(entity.getTest_brief());
            me.setTest_price(entity.getTest_price());
            me.setLesson_time(entity.getLesson_time());
            me.setMessage(entity.getMessage());
            me.setBind_phone(entity.getBind_phone());
            me.setDevice(entity.getDevice());
            me.setA_latitude(entity.getA_latitude());
            me.setA_longitude(entity.getA_longitude());
            json = JsonHelper.toJson(me);
            born = me != null;
        }
        // 生完了
        if (!born) {
            Log.e(TAG, "尼玛，流产了！！！");
        } else {
            PreferencesHelper.getInstance().putString(USER,json);
        }
        return born;
    }

    // endregion 单例

    /**
     * 退出登录清理用户信息
     */
    public void loginOut() {
        me = null;
        PreferencesHelper.getInstance().putString(USER, "");
    }
}
