package com.weima.aishangyi.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public class AddOrderBean implements Serializable{

    /**
     * order_no : A218030275255369
     * real_money : 50
     * total_money : 50
     * type : 0
     */

    private String order_no;
    private double real_money;
    private double total_money;
    private int type;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getReal_money() {
        return real_money;
    }

    public void setReal_money(double real_money) {
        this.real_money = real_money;
    }

    public double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(double total_money) {
        this.total_money = total_money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
