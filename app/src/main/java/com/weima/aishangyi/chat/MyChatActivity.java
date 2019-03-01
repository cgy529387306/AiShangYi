package com.weima.aishangyi.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.weima.aishangyi.R;
import com.weima.aishangyi.chat.fragment.MyChatFragment;
import com.weima.aishangyi.utils.ProjectHelper;

public class MyChatActivity extends EaseBaseActivity {
    public static MyChatActivity activityInstance;
    private MyChatFragment chatFragment;
    String toChatUsername;
    private TextView txv_actionbar_title;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        txv_actionbar_title = (TextView) findViewById(R.id.txv_actionbar_title);
        String nickName = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_NAME,"匿名用戶");
        txv_actionbar_title.setText(ProjectHelper.getCommonText(nickName));
        //user or group id
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        chatFragment = new MyChatFragment();
        //set arguments
        chatFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        findViewById(R.id.lin_actionbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public String getToChatUsername(){
        return toChatUsername;
    }
}
