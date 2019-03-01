package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/18.
 */
public class PaymentResp extends CommonEntity{
    private DataBean data;
    public static class DataBean extends CommonListData{
        private List<PaymentBean> data;

        private String cash_in;
        private String cash_out;
        private String recharge_total;
        private String withdrawal_total;

        public List<PaymentBean> getData() {
            return data;
        }

        public void setData(List<PaymentBean> data) {
            this.data = data;
        }

        public String getCash_in() {
            return cash_in;
        }

        public void setCash_in(String cash_in) {
            this.cash_in = cash_in;
        }

        public String getCash_out() {
            return cash_out;
        }

        public void setCash_out(String cash_out) {
            this.cash_out = cash_out;
        }

        public String getRecharge_total() {
            return recharge_total;
        }

        public void setRecharge_total(String recharge_total) {
            this.recharge_total = recharge_total;
        }

        public String getWithdrawal_total() {
            return withdrawal_total;
        }

        public void setWithdrawal_total(String withdrawal_total) {
            this.withdrawal_total = withdrawal_total;
        }
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

}