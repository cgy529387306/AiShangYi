package com.weima.aishangyi.chat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.SysMsgActivity;
import com.weima.aishangyi.activity.UserContactActivity;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.chat.utils.APPConfig;
import com.weima.aishangyi.chat.utils.SharedPreferencesUtils;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.utils.NavigationHelper;

import java.util.List;

/**
 * 消息会话activity
 */

public class MyConversationListActivity extends BaseActivity {
    private EaseConversationListFragment easeConversationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_conversation_list);
        setCustomTitle("我的消息");
        setImageRightButton(R.drawable.ic_contacts, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ProjectConstants.BundleExtra.KEY_CONTACT_TYPE,0);
                NavigationHelper.startActivity(MyConversationListActivity.this, UserContactActivity.class, bundle, false);
            }
        });
        easeConversationListFragment=new EaseConversationListFragment();
        easeConversationListFragment.hideTitleBar();
        easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                SharedPreferencesUtils.setParam(MyConversationListActivity.this, APPConfig.USER_NAME, CurrentUser.getInstance().getNickname());
                SharedPreferencesUtils.setParam(MyConversationListActivity.this, APPConfig.USER_HEAD_IMG, CurrentUser.getInstance().getIcon());
                Bundle bundle=new Bundle();
                bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                bundle.putString(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                EaseUser user = EaseUserUtils.getUserInfo(conversation.conversationId());
                if (user != null) {
                    bundle.putString(EaseConstant.EXTRA_USER_NAME, user.getNick());
                }
                NavigationHelper.startActivity(MyConversationListActivity.this, MyChatActivity.class, bundle, false);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.my_conversation_list,easeConversationListFragment)
                .commit();
        findViewById(R.id.btn_sys_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.startActivity(MyConversationListActivity.this, SysMsgActivity.class, null, false);
            }
        });
    }

    EMMessageListener messageListener=new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //接收到新的消息
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                    // refresh conversation list
                    if (easeConversationListFragment != null) {
                        easeConversationListFragment.refresh();
                    }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }
}
