package com.weima.aishangyi.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mb.android.utils.BitmapUtil;
import com.mb.android.utils.DialogHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.BuyMemberActivity;
import com.weima.aishangyi.activity.TeacherDetailAcitity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.entity.QQBean;
import com.weima.aishangyi.entity.UserInfoResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectHelper {
    public static boolean isLogin(){
        return Helper.isNotEmpty(PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CURRENT_TOKEN));
    }

    /**
     * 防止控件被连续点击
     * @param view
     */
    public static void disableViewDoubleClick(final View view) {
        if(Helper.isNull(view)) {
            return;
        }
        view.setEnabled(false);
        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 3000);
    }

    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }

    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    public static boolean isHttp(String input){
        Matcher mer = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+$").matcher(input);
        return mer.find();
    }

    /**
     * 取得UserAgent1
     * (网站)
     *
     * @return
     */
    public static String getUserAgent1() {
        StringBuilder userInfo = new StringBuilder();
        if (isLogin()) {
            String token = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CURRENT_TOKEN);
            // 用户信息
            userInfo.append("device=1").append("&token=").append(token == null ? "" : token);
        }else{
            userInfo.append("device=1");
        }
        return userInfo.toString();
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    /**
     * 手机验证
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum) {
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    /**
     * 电话号码验证
     * @author ：shijing
     * 2016年12月5日下午4:34:21
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isPhone(final String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 密码格式验证
     * @param pwd
     * @return
     */
    public static boolean isPwdValid(String pwd) {
        String regex = "^[a-zA-Z0-9]{6,30}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 手机验证
     * @param idCard
     * @return
     */
    public static boolean isIdcard(String idCard) {
        String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    /**
     * 用Intent打开url(即处理外部链接地址)
     *
     * @param context
     * @param url
     */
    public static void openUrlWithIntent(Context context, String url) {
        if (Helper.isNull(context) || Helper.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变父空间触摸事件拦截状态
     *
     * @param parentView
     * @param isDisallow
     */
    public static void changeParentDisallowInterceptState(
            ViewParent parentView, boolean isDisallow) {
        if (Helper.isNull(parentView)) {
            return;
        }
        if (Helper.isNull(parentView.getParent())) {
            return;
        }
        // 改变触摸拦截状态
        parentView.requestDisallowInterceptTouchEvent(isDisallow);
        changeParentDisallowInterceptState(parentView.getParent(), isDisallow);
    }


    /**
     * 保存图片到相册
     *
     * @param context 上下文
     * @param bmp     待保存图片
     * @return 是否保存成功
     * @see <a
     * href="http://changshizhe.blog.51cto.com/6250833/1295241">source</a>
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        if (Helper.isNull(context)) {
            return false;
        }
        if (Helper.isNull(bmp)) {
            ToastHelper.showToast("图片保存失败！");
            return false;
        }
        String uriStr = MediaStore.Images.Media.insertImage(
                context.getContentResolver(), bmp, "", "");

        if (Helper.isEmpty(uriStr)) {
            ToastHelper.showToast("图片保存失败！");
            return false;
        }
        String picPath = getFilePathByContentResolver(context,
                Uri.parse(uriStr));
        if (Helper.isNotEmpty(picPath)) {
            ToastHelper.showToast("图片保存成功！");
        }
        return true;
    }

    /**
     * 获取插入图片路径
     *
     * @param context
     * @param uri
     * @return
     * @see <a
     * href="http://blog.csdn.net/xiaanming/article/details/8990627">source</a>
     */
    private static String getFilePathByContentResolver(Context context, Uri uri) {
        if (Helper.isNull(uri) || Helper.isNull(context)) {
            return null;
        }
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);
        String filePath = null;
        if (Helper.isNull(cursor)) {
            throw new IllegalArgumentException("Query on " + uri
                    + " returns null result.");
        }
        try {
            if ((cursor.getCount() != 1) || !cursor.moveToFirst()) {
            } else {
                filePath = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaColumns.DATA));
            }
        } finally {
            cursor.close();
        }
        return filePath;
    }


    /**
     * 浏览器打开指定Url
     *
     * @param context 上下文
     * @param url     链接地址
     */
    public static void openUrlByBrowse(Context context, String url) {
        if (Helper.isEmpty(context) || Helper.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }


    /**
     * 隐藏View（透明度变化）
     *
     * @param view
     */
    public static void hideViewAlpha(final View view) {
        if (Helper.isNull(view)) {
            return;
        }
        AlphaAnimation anim = new AlphaAnimation(1, 0);
        anim.setDuration(800);
        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        view.startAnimation(anim);
    }

    public static void showViewAlpha(final View view) {
        if (Helper.isNull(view)) {
            return;
        }
        AlphaAnimation anim = new AlphaAnimation(0, 1);
        anim.setDuration(800);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(anim);
    }

    /**
     * @param time 发布、创建时间-格式：2014-11-13 12:06:46
     * @return
     */
    public static String formatTime(String time) {
        String result = time;
        if (TextUtils.isEmpty(time)) {
            return result;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(time);

            Calendar publishTime = Calendar.getInstance();
            publishTime.setTime(date);
            Calendar currentTime = Calendar.getInstance();
            currentTime.setTime(new Date(System.currentTimeMillis()));

            int days = currentTime.get(Calendar.DAY_OF_YEAR) - publishTime.get(Calendar.DAY_OF_YEAR);
            int hours = currentTime.get(Calendar.HOUR_OF_DAY) - publishTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE) - publishTime.get(Calendar.MINUTE);
            if (days == 0) {
                if (hours <= 0) {
                    if (minute <= 2) {
                        result = "刚刚";
                    } else if (minute < 60) {
                        result = minute + "分钟前";
                    }
                } else if (days < 24) {
                    result = hours + "小时前";
                }
            } else if (days == 1) {
                result = "昨天";
            } else if (days == 2) {
                result = "前天";
            } else if (days >= 3 && days < 31) {
                result = days + "天前";
            } else {
                SimpleDateFormat resultFormat = new SimpleDateFormat("yyyy-MM-dd");
                return resultFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }

        return result;
    }


    /**
     * 更新用户信息
     * @param context
     */
    public static void updateUserInfo(final Context context){
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(30000).setUrl(ProjectConstants.Url.ACCOUNT_GET_USERINFO)
                .setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(new HashMap<String, Object>()).getRequestEntity();
        RequestHelper.post(entity, new ResponseListener() {
            @Override
            public boolean onResponseSuccess(int gact, String response, Object... extras) {
                UserInfoResp entity = JsonHelper.fromJson(response, UserInfoResp.class);
                if ("200".equals(entity.getCode())) {
                    CurrentUser.getInstance().born(entity.getData());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_USER_INFO));
                } else {
                    ToastHelper.showToast(entity.getMessage());
                }
                return true;
            }

            @Override
            public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
                return false;
            }
        });
    }

    public static String getEmptyText(String text){
        return text==null?"暂无":text;
    }

    public static String getCommonText(String text){
        return text==null?"":text;
    }

    public static int getCommonSeletion(String text){
        return text==null?0:text.length();
    }

    /**
     *  //  "status" : "0", //0待确认，1待授课，2待评价，3已完成，4取消订单
     * @param status
     * @return
     */
    public static String getOrderStatus(int status){
        String statusStr;
        if (status == 1){
            statusStr = "待授课";
        }else if (status == 2){
            statusStr = "待评价";
        }else if (status == 3){
            statusStr = "已完成";
        }else if (status == 4){
            statusStr = "已取消";
        }else{
           statusStr = "待确认";
       }
        return statusStr;
    }

    public static String getLessonStatus(int status){
        String statusStr;
        if (status == 1){
            statusStr = "待确认";
        }else if (status == 2){
            statusStr = "待评价";
        }else if (status == 3){
            statusStr = "已完成";
        }else{
            statusStr = "进行中";
        }
        return statusStr;
    }

    public static String formatDecimal(double decimal){
        try {
            DecimalFormat df = new DecimalFormat("#0.00");//这样为保持2位
            double dd = decimal>1000?decimal/1000:decimal;
            String result = df.format(dd);
            String unit = decimal>1000?"km":"m";
            return result+unit;
        }catch (Exception e){
            e.printStackTrace();
            return "0.00m";
        }
    }


    public static String formatDecimal(String dec){
        try {
            double decimal = Double.parseDouble(dec);
            DecimalFormat df = new DecimalFormat("#0.00");//这样为保持2位
            double dd = decimal>1000?decimal/1000:decimal;
            String result = df.format(dd);
            String unit = decimal>1000?"km":"m";
            return result+unit;
        }catch (Exception e){
            e.printStackTrace();
            return "0.00m";
        }
    }

    public static String formatLongTime(long time){
        String result = "";
        try {
            result = Helper.long2DateString(time * 1000, "yyyy-MM-dd HH:mm");
        }catch (Exception e){
            return "2017-10-10";
        }
        return result;
    }

    public static String formatLongTime(String time){
        String result = "";
        try {
            result = Helper.long2DateString(Long.parseLong(time) * 1000, "yyyy-MM-dd HH:mm");
        }catch (Exception e){
            return "2017-10-10";
        }
        return result;
    }


    public static boolean isMine(int device,long userId){
        boolean isMine = false;
        if (Helper.isNotEmpty(device) && Helper.isNotEmpty(userId)){
            if (device==CurrentUser.getInstance().getDevice() && userId == CurrentUser.getInstance().getId()){
                isMine = true;
            }
        }
        return isMine;
    }

    /**
     * 把网络资源图片转化成bitmap
     * @param url  网络资源图片
     * @return  Bitmap
     */
    public static Bitmap getLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length);
            bitmap = BitmapUtil.zoomBitmap(b,80,80);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    public static int getAge(String birth) {
        int age;
        try {
            Date date = Helper.string2Date(birth,"yyyy-MM-dd");
            age = new Date().getYear() - date.getYear();
        } catch (Exception e) {
            age = 0;
            e.printStackTrace();
        }
        return age;
    }

    public static String formatBadgeNumber(int value) {
        if (value <= 0) {
            return "0";
        }
        if (value < 100) {
            return Integer.toString(value);
        }
        return "99+";
    }

    /**
     * 判断当前日期是星期几
     * @param  date     设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String getWeek(Date date) {
        String week = "周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            week += "六";
        }
        return week;
    }

    /**
     * 判断当前日期是星期几
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String getWeekStr(int postion) {
        String week = "周";
        switch (postion){
            case 0:
                week += "一";
                break;
            case 1:
                week += "二";
                break;
            case 2:
                week += "三";
                break;
            case 3:
                week += "四";
                break;
            case 4:
                week += "五";
                break;
            case 5:
                week += "六";
                break;
            case 6:
                week += "日";
                break;
            default:
                week += "一";
                break;
        }
        return week;
    }


    /**
     * 判断当前日期是星期几
     * @param  date     设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int getWeekNum(Date date) {
        if (Helper.isEmpty(date)){
            return 0;
        }
        int week = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            week = 0;
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            week = 1;
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            week = 2;
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            week = 3;
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            week = 4;
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            week = 5;
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week = 6;
        }
        return week;
    }

    /**
     * 根据日期获得所在周的日期
     * @return
     */
    public static List<String> getAllweekDays(Date d) {//根据日期来获取其所在周的每一天
        Calendar c = Calendar.getInstance();
        List<String> lst=new ArrayList();
        c.setTime(d);
        while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            c.add(Calendar.DATE, -1);
        }
        for (int i = 0; i < 7; i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
            String day = dateFormat.format(c.getTime());
            lst.add(day);
            c.add(Calendar.DATE, 1);
        }
        return lst;
    }

    public static void callTel(final Activity activity){
        final String tel = Helper.isEmpty(QQBean.getInstance().getPhone())?"18650480850":QQBean.getInstance().getPhone();
        DialogHelper.showConfirmDialog(activity, "温馨提示", "是否拨打客服电话\n"+tel, true,
                R.string.dialog_positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tel));
                        activity.startActivity(intent);
                    }
                }, R.string.dialog_negative, null);
    }
}
