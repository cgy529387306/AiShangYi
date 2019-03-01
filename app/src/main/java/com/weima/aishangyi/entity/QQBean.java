package com.weima.aishangyi.entity;

import android.util.Log;

import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;

/**
 * 作者：cgy on 17/3/15 02:54
 * 邮箱：593960111@qq.com
 */
public class QQBean {
    private int id;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //region 单例
    private static final String TAG = QQBean.class.getSimpleName();
    private static final String INFO = "memberInfo";
    private static QQBean me;
    /**
     * 单例
     * @return 当前用户对象
     */
    public static QQBean getInstance() {
        if (me == null) {
            me = new QQBean();
        }
        return me;
    }
    /**
     * 出生
     * <p>尼玛！终于出生了！！！</p>
     * <p>调用此方法查询是否登录过</p>
     * @return 出生与否
     */
    public boolean born() {
        String json = PreferencesHelper.getInstance().getString(INFO);
        me = JsonHelper.fromJson(json, QQBean.class);
        return me != null;
    }

    public boolean born(QQBean entity) {
        boolean born = false;
        String json = "";
        if (entity != null) {
            json = JsonHelper.toJson(me);
            me.setId(entity.getId());
            me.setPhone(entity.getPhone());
            born = me != null;
        }
        // 生完了
        if (!born) {
            Log.e(TAG, "尼玛，流产了！！！");
        } else {
            PreferencesHelper.getInstance().putString(INFO,json);
        }
        return born;
    }
    // endregion 单例
}
