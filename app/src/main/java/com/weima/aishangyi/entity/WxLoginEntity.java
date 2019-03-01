package com.weima.aishangyi.entity;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class WxLoginEntity {

    /**
     * openid : okQzswVcaF0GXF1XoQdDtQmv_9SA
     * expires_in : 7200
     * scope : snsapi_userinfo
     * refresh_token : 4FbN7ckuK_422PBV7jDFTuqy8WzcnPTit1HfHcUaZc_TjrMbIuDmaimkpHvu2wzt3pTvqoIYpaV8-psTHCC_iVok7OsTwgKu6lgzKQfH0wc
     * access_token : 6GZpa5f8aCMJI7BpHCBgHNg-sX00SO3pbdP2iHbS0stTYgfurV-07xq4YwDvU0Mgfjo74ramPUKotch9lS-HaFZuwHGxC67fdWxDN_abm4w
     * unionid : o0wXmv9DjdDTkLk2SK0SQBWEbLvQ
     */

    private String openid;
    private long expires_in;
    private String scope;
    private String refresh_token;
    private String access_token;
    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
