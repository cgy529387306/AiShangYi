package com.weima.aishangyi.entity;

/**
 * 作者：cgy on 17/2/26 01:43
 * 邮箱：593960111@qq.com
 */
public class OrderPayResp extends CommonEntity{
    private OrderData data;

    public class OrderData {
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


    public OrderData getData() {
        return data;
    }

    public void setData(OrderData data) {
        this.data = data;
    }
}
