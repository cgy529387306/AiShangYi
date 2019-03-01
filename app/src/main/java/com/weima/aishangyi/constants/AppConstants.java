package com.weima.aishangyi.constants;

public class AppConstants {
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
//    public static final String APP_ID="1105601536";//QQ
    public static final String APP_ID="1105878079";//QQ
    //微信申请的开发appid
    public static final String WX_APP_ID = "wx7e0044ca97a9edec";//微信wx48548a71fa92efed
    public static final String WX_APP_SECRET = "8e541a6e637d9c55fbb67d4ac81df19e";//微信

    //新浪申请的开发appid
    public static final String SINA_APP_KEY = "1303507625";
    public static final String SINA_REDIRECT_URL = "http://www.sina.com.cn";
    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     *
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     *
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     *
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }


    public static final String PARTNER = "2017021605710742";
    // 商户收款账号
    public static final String SELLER = "hdciax9942@sandbox.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMFtGxG2YraRcV9C" +
            "6AHVMdsUkqTqKPYI3+l3FPHC1MqK31axZOLY0/T3NON4+Lnkp5BH6FuJ81GjNA9X" +
            "Em3XTfWwUYkNeVoaFQPUHrb80wV8SVRU+rKX3d80P1qcDpi/GFXxjT1YiMUM8pds" +
            "/6PwI8zRqSVcJy++V6OrYWRbBJiPAgMBAAECgYBNCyBVFciL37LAtu2ijUCmerNO" +
            "bNaT6UUGwR/ejDQl2NrMtSvyD1udnzm5wV2Q7bqwhXPPwkrgoZkC4zO7/J5YJFfr" +
            "vhsqdTAheww4XYdO+XsoQjnHgtZQYiDGvbhzLZcXqwznr3hh8AvgqHmEqK8oyjmP" +
            "s9o3Cd8eGNu3ykcZQQJBAPHfzXYLHEXHkZcEP1Qw3voifjAz6hUXJ4zSHesmKJm4" +
            "bDofZVdLMV3OdIGiTQa3TCti658ts3JHQf1+UKpO0psCQQDMuPgtrvYyMDIH7uQl" +
            "OUoyp8zVR/t2Ko8+wX1aYSwQ4CLPoH/HLtA/XRaPKH0ZFlgTEaQ6P8DTu0H7xoNG" +
            "BYcdAkEA3aCXsEk/78SM6BaxPKDHP+XpQJvhKtAbolJJrdU6MJinOp7YCiPRKUZq" +
            "xPoFoFjGh57Njf6fReB7XB6pJVSybwJAFVw/WNTmjzoEHlvIpjGiEkL71k0aaavr" +
            "Iksh/yogZOpd93fxnSC/Tb0yu2EoHFP+gI7YqdM5FV52dgSycb3l6QJBAJPjfB/M" +
            "qDTASgirY/mx54OM2BywV3/CjnpJf2m66Np3EE8d3Mem2gK0JwHyJlm9VF4R4kCP" +
            "NaG+VWSZ1VNfmMY=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBbRsRtmK2kXFfQugB1THbFJKk" +
            "6ij2CN/pdxTxwtTKit9WsWTi2NP09zTjePi55KeQR+hbifNRozQPVxJt1031sFGJ" +
            "DXlaGhUD1B62/NMFfElUVPqyl93fND9anA6YvxhV8Y09WIjFDPKXbP+j8CPM0akl" +
            "XCcvvlejq2FkWwSYjwIDAQAB";

//    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
    //服务端回调
    public static final String NOTIFY_URL = ProjectConstants.Url.INDEX_URL+
            "order/notify";
}
