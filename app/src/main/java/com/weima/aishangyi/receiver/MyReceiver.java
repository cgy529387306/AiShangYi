package com.weima.aishangyi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.mb.android.utils.DialogHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.NotificationHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.HomeActivity;
import com.weima.aishangyi.activity.SysMsgActivity;
import com.weima.aishangyi.activity.UserClassroomOrderActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.utils.NavigationHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle,context));
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			PreferencesHelper.getInstance().putString(ProjectConstants.Preferences.KEY_REGISTRATION_ID, regId);
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			processCustomMessage(context, bundle);
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			openNotification(context, bundle);
		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

		} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle,Context context) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		Log.e("Jpush", sb.toString());
		return sb.toString();
	}

	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
		System.out.println("收到了自定义消息@@消息内容是:"+ content);
		System.out.println("收到了自定义消息@@消息extra是:"+ extra);

		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(extra);
			String type = jsonObject.getString("type");
			if (Helper.isNotEmpty(type)){
				if ("1".equals(type)){
					int count = PreferencesHelper.getInstance().getInt(ProjectConstants.Preferences.KEY_TALENT_COUNT,0);
					PreferencesHelper.getInstance().putInt(ProjectConstants.Preferences.KEY_TALENT_COUNT,count+1);
					LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_TALENT_NEW));
				}else if ("2".equals(type)){
					int count = PreferencesHelper.getInstance().getInt(ProjectConstants.Preferences.KEY_FANS_COUNT,0);
					PreferencesHelper.getInstance().putInt(ProjectConstants.Preferences.KEY_FANS_COUNT,count+1);
					LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_FANS_NEW));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void openNotification(Context context, Bundle bundle){
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		try {
			JSONObject jsonObject = new JSONObject(extras);
			String type = jsonObject.getString("type");
			if (Helper.isNotEmpty(type) ){
				if ("1".equals(type)){
					Intent mIntent = new Intent(context, UserClassroomOrderActivity.class);
					mIntent.putExtra("status",0);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(mIntent);
				}else if ("2".equals(type)){
					Intent mIntent = new Intent(context, SysMsgActivity.class);
					mIntent.putExtra("type",0);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(mIntent);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Unexpected: extras is not a valid json", e);
			return;
		}
	}
}
