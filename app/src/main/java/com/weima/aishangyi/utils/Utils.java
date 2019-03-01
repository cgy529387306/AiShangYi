package com.weima.aishangyi.utils;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public class Utils {
    public static boolean notEmpty(String str){
        return null != str && !"".equals(str.trim()) && !"null".equals(str);
    }

    public static boolean isEmpty(String str){
        return !notEmpty(str);
    }

    public static boolean notEmpty(List list){
        return null != list && list.size() > 0;
    }

    public static boolean isEmpty(List list){
        return !notEmpty(list);
    }

    public static boolean notEmpty(String[] arr){
        return null != arr && arr.length > 0;
    }

    public static int dip2px(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

}
