package com.weima.aishangyi.entity;

import android.util.Log;

import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/22.
 */

public class MemberBean {
    private int is_member;
    private String date_line;
    private int number;
    private List<ContentBean> content;

    public int getIs_member() {
        return is_member;
    }

    public void setIs_member(int is_member) {
        this.is_member = is_member;
    }

    public String getDate_line() {
        return date_line;
    }

    public void setDate_line(String date_line) {
        this.date_line = date_line;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable{
        private long id;
        private int month;
        private double money;
        private int number;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    //region 单例
    private static final String TAG = MemberBean.class.getSimpleName();
    private static final String INFO = "memberInfo";
    private static MemberBean me;
    /**
     * 单例
     * @return 当前用户对象
     */
    public static MemberBean getInstance() {
        if (me == null) {
            me = new MemberBean();
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
        String json = PreferencesHelper.getInstance().getString(INFO);
        me = JsonHelper.fromJson(json, MemberBean.class);
        return me != null;
    }

    public boolean born(MemberBean entity) {
        boolean born = false;
        String json = "";
        if (entity != null) {
            json = JsonHelper.toJson(me);
            me.setNumber(entity.getNumber());
            me.setContent(entity.getContent());
            me.setDate_line(entity.getDate_line());
            me.setIs_member(entity.getIs_member());
            born = me != null;
        }
        // 生完了
        if (!born) {
            Log.e(TAG, "尼玛，流产了！！！");
        } else {
            PreferencesHelper.getInstance().putString(INFO,json);
        }
        return born;
    }
    // endregion 单例
}
