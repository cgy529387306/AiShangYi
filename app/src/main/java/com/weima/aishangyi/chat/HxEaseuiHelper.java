package com.weima.aishangyi.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.text.TextUtils;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.mb.android.utils.Helper;
import com.weima.aishangyi.chat.db.InviteMessgeDao;
import com.weima.aishangyi.chat.db.UserDao;
import com.weima.aishangyi.chat.domain.RobotUser;
import com.weima.aishangyi.chat.utils.APPConfig;
import com.weima.aishangyi.chat.utils.PreferenceManager;
import com.weima.aishangyi.chat.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/12.
 */

public class HxEaseuiHelper {

    private static HxEaseuiHelper instance = null;
    protected EMMessageListener messageListener = null;
    private Context appContext;
    private String username;
    private EaseUI easeUI;
    private InviteMessgeDao inviteMessgeDao;
    private UserDao userDao;
    private Map<String, EaseUser> contactList;
    private Map<String, RobotUser> robotList;
    private DemoModel demoModel = null;

    private String TAG="HxEaseuiHelper";

    public synchronized static HxEaseuiHelper getInstance() {
        if (instance == null) {
            instance = new HxEaseuiHelper();
        }
        return instance;
    }


    public void init(Context context) {
        demoModel = new DemoModel(context);
        EMOptions options = initChatOptions();
        //use default options if options is null
        if (EaseUI.getInstance().init(context, options)) {
            appContext = context;
            //获取easeui实例
            easeUI = EaseUI.getInstance();
            //初始化easeui
            easeUI.init(appContext,options);
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            EMClient.getInstance().setDebugMode(true);
            // enabled fixed sample rate
            // Offline call push
            EMClient.getInstance().callManager().getCallOptions().setIsSendPushIfOffline(getModel().isPushCall());
            setEaseUIProviders();
            //设置全局监听
            setGlobalListeners();
//            broadcastManager = LocalBroadcastManager.getInstance(appContext);
            initDbDao();
        }
    }


    private EMOptions initChatOptions(){
        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);
        //set custom servers, commonly used in private deployment
        if(demoModel.isCustomServerEnable() && demoModel.getRestServer() != null && demoModel.getIMServer() != null) {
            options.setRestServer(demoModel.getRestServer());
            options.setIMServer(demoModel.getIMServer());
            if(demoModel.getIMServer().contains(":")) {
                options.setIMServer(demoModel.getIMServer().split(":")[0]);
                options.setImPort(Integer.valueOf(demoModel.getIMServer().split(":")[1]));
            }
        }

        if (demoModel.isCustomAppkeyEnabled() && demoModel.getCutomAppkey() != null && !demoModel.getCutomAppkey().isEmpty()) {
            options.setAppKey(demoModel.getCutomAppkey());
        }

        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());

        return options;
    }

    public DemoModel getModel(){
        return (DemoModel) demoModel;
    }

    protected void setEaseUIProviders() {
        // set profile provider if you want easeUI to handle avatar and nickname
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    private EaseUser getUserInfo(String username){
        if (Helper.isEmpty(username)){
            username = "匿名用户";
        }
        //获取 EaseUser实例, 这里从内存中读取
        //如果你是从服务器中读读取到的，最好在本地进行缓存
        EaseUser user = null;
        //如果用户是本人，就设置自己的头像
        if(username.equals(EMClient.getInstance().getCurrentUser())){
            user=new EaseUser(username);
            user.setAvatar((String) SharedPreferencesUtils.getParam(appContext, APPConfig.USER_HEAD_IMG, "http://img5.duitang.com/uploads/item/201507/21/20150721172011_mGYkh.thumb.224_0.jpeg"));
            user.setNick((String)SharedPreferencesUtils.getParam(appContext, APPConfig.USER_NAME, "nike"));
            return user;
        }
//        if (user==null && getRobotList()!=null){
//            user=getRobotList().get(username);
//        }

        //收到别人的消息，设置别人的头像
        if (contactList!=null && contactList.containsKey(username)){
            user=contactList.get(username);
        }else { //如果内存中没有，则将本地数据库中的取出到内存中
            contactList=getContactList();
            user=contactList.get(username);
        }

        //如果用户不是你的联系人，则进行初始化
        if(user == null){
            user = new EaseUser(username);
            EaseCommonUtils.setUserInitialLetter(user);
        }else {
            if (TextUtils.isEmpty(user.getAvatar())){//如果名字为空，则显示环信号码
                user.setNick(user.getUsername());
            }
        }
        //Log.i("zcb","头像："+user.getAvatar());

        return user;
    }

    private void initDbDao() {
        inviteMessgeDao = new InviteMessgeDao(appContext);
        userDao = new UserDao(appContext);
    }

    public Map<String, RobotUser> getRobotList() {
        if (isLoggedIn() && robotList == null) {
            robotList = demoModel.getRobotList();
        }
        return robotList;
    }

    /**
     * get current user's id
     */
    public String getCurrentUsernName(){
        if(username == null){
            username = (String)SharedPreferencesUtils.getParam(appContext, Constant.HX_CURRENT_USER_ID, "");
        }
        return username;
    }

    /**
     *获取所有的联系人信息
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        if (isLoggedIn() && contactList == null) {
            contactList = demoModel.getContactList();
        }

        // return a empty non-null object to avoid app crash
        if(contactList == null){
            return new Hashtable<String, EaseUser>();
        }

        return contactList;
    }
    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * set global listener
     */
    protected void setGlobalListeners(){
        registerMessageListener();
    }

    /**
     * Global listener
     * If this event already handled by an activity, you don't need handle it again
     * activityList.size() <= 0 means all activities already in background or not in Activity Stack
     */
    protected void registerMessageListener() {
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());

                    //接收并处理扩展消息
                    String userName=message.getStringAttribute(Constant.USER_NAME,"");
                    long userId=message.getLongAttribute(Constant.USER_ID,1);
                    String userPic=message.getStringAttribute(Constant.HEAD_IMAGE_URL,"");
                    String hxIdFrom=message.getFrom();
                    System.out.println("helper接收到的用户名："+userName+"helper接收到的id："+userId+"helper头像："+userPic);
                    EaseUser easeUser=new EaseUser(hxIdFrom);
                    easeUser.setAvatar(userPic);
                    easeUser.setNick(userName);

                    //存入内存
                    getContactList();
                    contactList.put(hxIdFrom,easeUser);

                    //存入db
                    UserDao dao=new UserDao(appContext);
                    List<EaseUser> users=new ArrayList<EaseUser>();
                    users.add(easeUser);
                    dao.saveContactList(users);



                    // in background, do not refresh UI, notify it in notification bar
//                    if(!easeUI.hasForegroundActivies()){
//                        getNotifier().onNewMsg(message);
//                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                    //get message body
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

}