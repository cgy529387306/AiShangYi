package com.weima.aishangyi.entity;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class WxUserInfoEntity {

    /**
     * sex : 1
     * nickname : 诠释一种信仰
     * unionid : o0wXmv9DjdDTkLk2SK0SQBWEbLvQ
     * privilege : []
     * province : Fujian
     * openid : okQzswVcaF0GXF1XoQdDtQmv_9SA
     * language : zh_CN
     * headimgurl : http://wx.qlogo.cn/mmopen/Q3auHgzwzM4amDwkSiawk7d14AZ2jXyR9Q0YuTAY1Zicy8K4pAJGIJs5W54Y42MKLzJw9vKrn5M9xwKOmibrIgdW1uLjD1pe8ahuZOqGXR9pr8/0
     * country : CN
     * city : Fuzhou
     */

    private int sex;
    private String nickname;
    private String unionid;
    private String province;
    private String openid;
    private String language;
    private String headimgurl;
    private String country;
    private String city;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
