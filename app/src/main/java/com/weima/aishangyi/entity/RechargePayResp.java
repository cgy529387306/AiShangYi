package com.weima.aishangyi.entity;

/**
 * 作者：cgy on 17/2/26 01:43
 * 邮箱：593960111@qq.com
 */
public class RechargePayResp extends CommonEntity{
    private RechargeData data;


    public class RechargeData {
        private String recharge_no;
        private double money;

        public String getRecharge_no() {
            return recharge_no;
        }

        public void setRecharge_no(String recharge_no) {
            this.recharge_no = recharge_no;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }
    }

    public RechargeData getData() {
        return data;
    }

    public void setData(RechargeData data) {
        this.data = data;
    }
}
