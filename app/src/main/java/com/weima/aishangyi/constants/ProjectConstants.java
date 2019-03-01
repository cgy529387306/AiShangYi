package com.weima.aishangyi.constants;

/**
 * 常量配置
 *
 * @author cgy
 */
public class ProjectConstants {
    /**
     * 请求页数
     */
    public static final int PAGE_INDEX = 1;
    /**
     * 分页大小
     */
    public static final int PAGE_SIZE = 20;

    /**
     * Bundle参数（k-v）
     */
    public class BundleExtra {
        public static final String KEY_WEB_DETAIL_URL = "KEY_WEB_DETAIL_URL";
        public static final String KEY_WEB_DETAIL_TITLE = "KEY_WEB_DETAIL_TITLE";
        public static final String KEY_IS_SET_TITLE = "KEY_IS_SET_TITLE";
        /**
         * 经度
         */
        public static final String KEY_ADDRESS_LAT = "KEY_ADDRESS_LAT";
        /**
         * 纬度
         */
        public static final String KEY_ADDRESS_LON = "KEY_ADDRESS_LON";
        /**
         * 用户id
         */
        public static final String KEY_USER_ID = "KEY_USER_ID";
        /**
         * 用户类型
         */
        public static final String KEY_USER_TYPE = "KEY_USER_TYPE";
        /**
         * 用户名
         */
        public static final String KEY_USER_NAME = "KEY_USER_NAME";
        /**
         * 用户
         */
        public static final String KEY_USER = "KEY_USER";
        /**
         * 城市名称
         */
        public static final String KEY_CITY_NAME = "KEY_CITY_NAME";
        /**
         * 联系人类型
         */
        public static final String KEY_CONTACT_TYPE = "KEY_CONTACT_TYPE";
        /**
         * 课程类型
         */
        public static final String KEY_TIMEABLE_TYPE = "KEY_TIMEABLE_TYPE";
        /**
         * 地址
         */
        public static final String KEY_ADDRESS_NAME = "KEY_ADDRESS_NAME";
        /**
         * 电话
         */
        public static final String KEY_BIND_PHONE = "KEY_BIND_PHONE";
        /**
         * 认证信息
         */
        public static final String KEY_CERTIFY_INFO = "KEY_CERTIFY_INFO";
        /**
         * 课程分类id
         */
        public static final String KEY_CLASS_TYPE_ID = "KEY_CLASS_TYPE_ID";
        /**
         * 课程分类名称
         */
        public static final String KEY_CLASS_TYPE_NAME = "KEY_CLASS_TYPE_NAME";
        /**
         * 课程
         */
        public static final String KEY_CLASS = "KEY_CLASS";
        /**
         * Talent Id
         */
        public static final String KEY_TALENT_ID = "KEY_TALENT_ID";
        /**
         * Talent
         */
        public static final String KEY_TALENT = "KEY_TALENT";
        /**
         * Activity Id
         */
        public static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
        /**
         * Activity
         */
        public static final String KEY_ACTIVITY = "KEY_ACTIVITY";
        /**
         * Info Id
         */
        public static final String KEY_INFO = "KEY_INFO";
        /**
         * Order
         */
        public static final String KEY_ORDER = "KEY_ORDER";
        /**
         * KEY_KEYWORD
         */
        public static final String KEY_KEYWORD = "KEY_KEYWORD";
        /**
         * KEY_AVATAR
         */
        public static final String KEY_AVATAR = "KEY_AVATAR";
        /**
         * KEY_NICKNAME
         */
        public static final String KEY_NICKNAME = "KEY_NICKNAME";
        /**
         * KEY_COUPONS
         */
        public static final String KEY_COUPONS = "KEY_COUPONS";
        /**
         * KEY_COUPONS_ID
         */
        public static final String KEY_COUPONS_ID = "KEY_COUPONS_ID";
    }

    /**
     * Activity 请求码
     *
     * @author cgy
     */
    public class ActivityRequestCode {
        public static final int REQUEST_HOME_LOCATION = 1001;
        public static final int REQUEST_SELECT_ADDRESS = 1002;
        public static final int REQUEST_BIND_PHONE = 1003;
        public static final int REQUEST_CERTIFY_INFO = 1004;
        public static final int REQUEST_SELECT_TYPE = 1005;
    }


    public static class Url {
        /**
         * APP的接口地址
         */
        public static final String INDEX_URL = "http://admin.artloveasy.com/api/";
//        /**
//         * APP的接口地址
//         */
//        public static final String INDEX_URL = "http://139.196.173.191:50111";
        /**
         * 获取验证码
         */
        public static final String ACCOUNT_CODE = INDEX_URL + "sendSmsCode";
        /**
         * 注册
         */
        public static final String ACCOUNT_REGISTER = INDEX_URL + "register";
        /**
         * 登录
         */
        public static final String ACCOUNT_LOGIN = INDEX_URL + "login";
        /**
         * 找回密码
         */
        public static final String ACCOUNT_FINDPWD = INDEX_URL + "refindPassword";
        /**
         * 意见反馈
         */
        public static final String ACCOUNT_FEEDBACK = INDEX_URL + "feedback";
        /**
         * 常见问题|老师指南|关于我们
         */
        public static final String ACCOUNT_ARTICLE = INDEX_URL + "article";
        /**
         * 重新获取token
         */
        public static final String ACCOUNT_REFRESH_TOKEN = INDEX_URL + "refreshToken";
        /**
         * 绑定手机号码
         */
        public static final String ACCOUNT_BIND_PHONE = INDEX_URL + "user/bindPhone";
        /**
         * 个人信息编辑
         */
        public static final String ACCOUNT_EDIT_INFO = INDEX_URL + "user/stuEdit";
        /**
         * 获取用户信息
         */
        public static final String  ACCOUNT_GET_USERINFO = INDEX_URL + "user/detail";
        /**
         * 获取粉丝关注老师学生数
         */
        public static final String  ACCOUNT_GET_NUM = INDEX_URL + "user/center";
        /**
         * 附件的人
         */
        public static final String USER_NEAR = INDEX_URL + "user/near";
        /**
         * 我的优惠券
         */
        public static final String USER_COUNPON = INDEX_URL + "user/coupon";
        /**
         * 资料认证
         */
        public static final String ACCOUNT_INFO_CERTIFY = INDEX_URL + "teacher/certify";
        /**
         * 认证资料获取
         */
        public static final String ACCOUNT_GET_CERTIFY_INFO = INDEX_URL + "teacher/getCertify";
        /**
         * 设置课程
         */
        public static final String ACCOUNT_SAVE_LESSON = INDEX_URL + "teacher/saveLesson";
        /**
         * 实时经纬度
         */
        public static final String LONGILATI = INDEX_URL + "longiLati";
        /**
         * 文件图片上传
         */
        public static final String FILE_UPLOAD = INDEX_URL + "upload";
        /**
         * 获取所有类别
         */
        public static final String GET_LESSON_TYPE = INDEX_URL + "itemAll";
        /**
         * 公共参数
         */
        public static final String GET_COMMON_DATA = INDEX_URL + "system/setting";
        /**
         * 获取首页广告
         */
        public static final String HOME_AD = INDEX_URL + "stu/index/ad";
        /**
         * 获取课程列表
         */
        public static final String LESSON_LIST = INDEX_URL + "teacher/lesson";
        /**
         * 根据类别获取课程
         */
        public static final String LESSON_LIST_BYID = INDEX_URL + "teacher/itemLesson";
        /**
         * 发布才艺圈
         */
        public static final String TALENT_PUBLISH = INDEX_URL + "talent/publish";
        /**
         * 才艺圈列表
         */
        public static final String TALENT_LIST = INDEX_URL + "talent/show";
        /**
         * 才艺圈详情
         */
        public static final String TALENT_DETAIl = INDEX_URL + "talent/detail";
        /**
         * 才艺圈删除
         */
        public static final String TALENT_DELETE = INDEX_URL + "talent/delete";
        /**
         * 才艺圈点赞
         */
        public static final String TALENT_UP = INDEX_URL + "talent/thumbsup";
        /**
         * 才艺圈评论列表
         */
        public static final String TALENT_COMMENT_LIST = INDEX_URL + "talent/comment";
        /**
         * 才艺圈评论
         */
        public static final String TALENT_COMMENT = INDEX_URL + "talent/publishComment";
        /**
         * 才艺资讯
         */
        public static final String INFO = INDEX_URL + "info";
        /**
         * 才艺咨询详情
         */
        public static final String INFO_DETAIL = INDEX_URL + "info/detail";
        /**
         * 才艺咨询点赞跟取消点赞
         */
        public static final String INFO_THUMB = INDEX_URL + "info/thumb";
        /**
         * 才艺咨询评论
         */
        public static final String INFO_COMMENT = INDEX_URL + "info/publishComment";

        /**
         * 才艺咨询评论列表
         */
        public static final String INFO_COMMENT_LIST = INDEX_URL + "info/comment";

        /**
         * 活动课堂列表
         */
        public static final String ACTIVITY_LIST = INDEX_URL + "active/search";
        /**
         * 活动课堂详情
         */
        public static final String ACTIVITY_DETAIl = INDEX_URL + "active/detail";
        /**
         * 关注的人
         */
        public static final String MY_FOLLOW_LIST = INDEX_URL + "user/follow_list";
        /**
         * 我的粉丝
         */
        public static final String MY_FANS_LIST = INDEX_URL + "user/fans";
        /**
         * 我的老师
         */
        public static final String MY_TEACHER_LIST = INDEX_URL + "user/myTeacher";
        /**
         * 我的课表
         */
        public static final String MY_LESSON_LIST = INDEX_URL + "user/myLesson";
        /**
         * 我的学生
         */
        public static final String MY_STUDENT_LIST = INDEX_URL + "user/myStudent";
        /**
         * 关注
         */
        public static final String FOLLOW = INDEX_URL + "user/follow";
        /**
         * 取消关注
         */
        public static final String FOLLOW_CANCEL = INDEX_URL + "user/cancelFollow";
        /**
         * 我的收藏 收藏类型 1收藏咨询 2收藏课程 3收藏老师 4收藏活动
         */
        public static final String MY_COLLECT = INDEX_URL + "user/myCollect";
        /**
         * 收藏  1收藏咨询 2收藏课程 3收藏老师 4收藏活动
         */
        public static final String COLLECT = INDEX_URL + "user/collect";
        /**
         * 提现账户管理添加修改
         */
        public static final String ACCOUNT_BILLEDIT = INDEX_URL + "user/billEdit";
        /**
         * 根据用户id获取才艺圈
         */
        public static final String USER_TALEENT = INDEX_URL + "talent/talentByUserId";
        /**
         * 活动订单
         */
        public static final String ORDER_ACTIVITY = INDEX_URL + "order/active";
        /**
         * 课程订单
         */
        public static final String ORDER_LESSON = INDEX_URL + "order/lesson";
        /**
         * 待支付去支付
         */
        public static final String ORDER_GOPAY = INDEX_URL + "order/goOrder";
        /**
         * 提出问题
         */
        public static final String ORDER_QUESTION = INDEX_URL + "order/orderQuest";
        /**
         * 查看答案
         */
        public static final String ORDER_SEE = INDEX_URL + "quest/see";
        /**
         * 活动订单列表 status：0未付款，1已付款，2待退款，3已完成，4已取消 ，5已超期
         */
        public static final String ORDER_ACTIVITY_LIST = INDEX_URL + "order/active_list";
        /**
         * 课程订单 status:0未付款，1已付款，2待退款，3已完成，4已超期 ，5订单已取消 6已退款，7待确认，8待评价
         */
        public static final String ORDER_LESSON_LIST = INDEX_URL + "order/lesson_list";
        /**
         * 我的课表
         */
        public static final String ORDER_MY_LESSON = INDEX_URL + "user/myLesson";
        /**
         * 取消订单
         */
        public static final String ORDER_LESSON_CANCEl = INDEX_URL + "ucenter/cancelOrder";
        /**
         * 订单确认
         */
        public static final String ORDER_LESSON_COMFIRM = INDEX_URL + "ucenter/confirm";
        /**
         * 删除订单
         */
        public static final String ORDER_LESSON_DELETE = INDEX_URL + "ucenter/delOrder";
        /**
         * 申请课程退款
         */
        public static final String ORDER_LESSON_REFOUND = INDEX_URL + "user/rebound";
        /**
         * 评价老师
         */
        public static final String ORDER_LESSON_EVALUATE = INDEX_URL + "ucenter/finish";
        /**
         * 问答搜索
         */
        public static final String QUESTION_SEARCH = INDEX_URL + "question/search";
        /**
         * 设置最佳答案
         */
        public static final String QUESTION_SETBEST = INDEX_URL + "quest/setBest";
        /**
         * 问题详情
         */
        public static final String QUESTION_DETAIL = INDEX_URL + "quest/questDetail";
        /**
         * 答案点赞取消
         */
        public static final String QUESTION_THUMB = INDEX_URL + "quest/thumb";
        /**
         * 我的提问
         */
        public static final String QUESTION_MY_ASK = INDEX_URL + "quest/myQuestion";
        /**
         * 我的围观
         */
        public static final String QUESTION_MY_SEE = INDEX_URL + "quest/mySee";
        /**
         * 充值
         */
        public static final String CASH_RECHARGE = INDEX_URL + "cash/recharge";
        /**
         * 提现申请
         */
        public static final String CASH_WITHDRAWAL = INDEX_URL + "cash/withdrawal";
        /**
         * 账户余额
         */
        public static final String CASH_BALANCE = INDEX_URL + "cash/balance";
        /**
         * 学生端问答首页收入支出
         */
        public static final String CASH_ANWSER_INOUT = INDEX_URL + "cash/stuinout";
        /**
         * 学生端问答首页收入支出
         */
        public static final String CASH_INOUT = INDEX_URL + "cash/stu";
        /**
         * 获取邀请码
         */
        public static final String USER_INVITE = INDEX_URL + "user/invite";
        /**
         * 设置邀请码
         */
        public static final String USER_SETINVITE = INDEX_URL + "user/setInvite";
        /**
         * 微信支付
         */
        public static final String WECHAT_PREPAY = INDEX_URL + "order/preOrder";
        /**
         * 课程订单推送消息列表
         */
        public static final String MSG_ORDER = INDEX_URL + "system/order_message";
        /**
         * 系统消息接口
         */
        public static final String MSG_SYSTEM = INDEX_URL + "system/message";
        /**
         * 获取会员类型
         */
        public static final String USER_MEMBER = INDEX_URL + "ucenter/member";
        /**
         * 获取会员类型
         */
        public static final String ORDER_MEMBER = INDEX_URL + "order/member";
        /**
         * 课程下单
         */
        public static final String ORDER_LESSON_NEW = INDEX_URL + "order/lessonOrder";
        /**
         * 课程订单列表
         */
        public static final String ORDER_LIST = INDEX_URL + "ucenter/stu_lesson";
        /**
         * 取消订单
         */
        public static final String ORDER_CANCEL = INDEX_URL + "ucenter/cancelOrder";
        /**
         * 确认授课
         */
        public static final String ORDER_COMFIRM = INDEX_URL + "ucenter/confirm";
        /**
         * 订单详情
         */
        public static final String ORDER_DETAIL = INDEX_URL + "ucenter/order_detail";
        /**
         * 客服中心
         */
        public static final String CUSTOMER = INDEX_URL + "system/customer";
        /**
         * 版本更新
         */
        public static final String UPDATE = INDEX_URL + "ucenter/batch_android";

        /**
         * 常见问题|老师指南|关于我们
         */
        public static final String ARTICLE = INDEX_URL + "article";

        /**
         * 常见问题|老师指南|关于我们 详情
         */
        public static final String ARTICLE_DETAIL = INDEX_URL + "article/detail/";
    }

    /**
     * 网络请求额外参数
     *
     * @author cgy
     */
    public static final class Extras {
        /**
         * 收藏
         */
        public static final int COLLECT = 10001;
    }

    /**
     * XML相关配置的键值
     *
     * @author cgy
     */
    public static final class Preferences {
        /**
         * 极光推送registrationid
         */
        public static final String KEY_REGISTRATION_ID = "KEY_REGISTRATION_ID";
        /**
         * TOKEN
         */
        public static final String KEY_CURRENT_TOKEN = "KEY_CURRENT_TOKEN";
        /**
         * 是否第一次
         */
        public static final String KEY_IS_FIRST_IN = "KEY_IS_FIRST_IN";
        /**
         * 课程类别数据
         */
        public static final String KEY_CLASS_TYPE = "KEY_CLASS_TYPE";
        /**
         * 课程类别数据
         */
        public static final String KEY_COMMON_DATA = "KEY_COMMON_DATA";
        /**
         * 纬度
         */
        public static final String KEY_LAT = "KEY_LAT";
        /**
         * 经度
         */
        public static final String KEY_LON = "KEY_LON";
        /**
         * 经度
         */
        public static final String KEY_CITY = "KEY_CITY";
        /**
         * 新才艺数
         */
        public static final String KEY_TALENT_COUNT = "KEY_TALENT_COUNT";
        /**
         * 新粉丝数
         */
        public static final String KEY_FANS_COUNT = "KEY_FANS_COUNT";
        /**
         * 会员信息
         */
        public static final String KEY_MEMBER_INFO = "KEY_MEMBER_INFO";

    }

    /**
     * 请求方式
     *
     * @author cgy
     */
    public static final class RequestType {
        /**
         * 头部
         */
        public static final int HEAD = 10001;
        /**
         * 底部
         */
        public static final int BOTTOM = 10002;
    }

    /**
     * 广播相关动作
     *
     * @author cgy
     */
    public static final class BroadCastAction {
        /**
         * 改变用户信息模块
         */
        public static final String CHANGE_USER_BLOCK = "CHANGE_USER_BLOCK";
        /**
         * 更新用户信息
         */
        public static final String UPDATE_USER_INFO = "UPDATE_USER_INFO";
        /**
         * 更新才艺列表
         */
        public static final String UPDATE_TALENT_LIST = "UPDATE_TALENT_LIST";
        /**
         * 更新课程列表
         */
        public static final String UPDATE_LESSON_LIST = "UPDATE_LESSON_LIST";
        /**
         * 更新老师列表
         */
        public static final String UPDATE_TEACHER_LIST = "UPDATE_TEACHER_LIST";
        /**
         * 更新粉丝列表
         */
        public static final String UPDATE_FANS_LIST = "UPDATE_FANS_LIST";
        /**
         * 更新活动列表
         */
        public static final String UPDATE_ACTIVITY_LIST = "UPDATE_ACTIVITY_LIST";
        /**
         * 更新资讯列表
         */
        public static final String UPDATE_INFO_LIST = "UPDATE_INFO_LIST";
        /**
         * 更新问答列表
         */
        public static final String UPDATE_QUESTION_LIST = "UPDATE_QUESTION_LIST";
        /**
         * 更新问答列表
         */
        public static final String UPDATE_BALANCE = "UPDATE_BALANCE";
        /**
         * 更新问答列表
         */
        public static final String UPDATE_TALENT_NEW = "UPDATE_TALENT_NEW";
        /**
         * 更新问答列表
         */
        public static final String UPDATE_FANS_NEW = "UPDATE_FANS_NEW";
        /**
         * 更新问答列表
         */
        public static final String UPDATE_MEMBER= "UPDATE_MEMBER";

    }

}
