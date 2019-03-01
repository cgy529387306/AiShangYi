package com.weima.aishangyi.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/20 0020.
 */

public class WxPayResp extends CommonEntity{

    @SerializedName("data")
    private WechatPay data;

    public WechatPay getData() {
        return data;
    }

    public void setData(WechatPay data) {
        this.data = data;
    }


    public class WechatPay{

        /**
         * timestamp : 1440401116
         * nonce_str : 111111
         * package_str : wx2015082415251627b42eaa890736780367
         * sign_type : MD5
         * pay_sign : 7B4228C5B0020BB31BA8E5A31FBB14D6
         * appid : wx4e33ddf238f95c84
         * partnerid : 1250366501
         */

        private String timestamp;
        private String noncestr;
        @SerializedName("package")
        private String package_str;
        private String sign_type;
        private String sign;
        private String appid;
        private String partnerid;
        private String prepayid;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackage_str() {
            return package_str;
        }

        public void setPackage_str(String package_str) {
            this.package_str = package_str;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }
    }


}
