package com.weima.aishangyi.entity;

/**
 * 作者：cgy on 17/2/16 00:50
 * 邮箱：593960111@qq.com
 */
public class NearBean {

    /**
     * signature : Ffff
     * icon : http://aishangyi.h5h5h5.cn/file/20170124/LmJh335c420170124022830.jpg
     * nickname : 13592623743
     * id : 1
     * sex : 1
     * device : 0
     * distance : null
     */

    private String signature;
    private String icon;
    private String nickname;
    private String distance;
    private long id;
    private int sex;
    private int device;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
