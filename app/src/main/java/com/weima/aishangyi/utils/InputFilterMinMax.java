package com.weima.aishangyi.utils;

import android.text.InputFilter;
import android.text.Spanned;

import com.mb.android.utils.ToastHelper;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public class InputFilterMinMax implements InputFilter {
    private int min, max;
    private String hint;

    public InputFilterMinMax(int min, int max,String text) {
        this.min = min;
        this.max = max;
        this.hint = text;
    }

    public InputFilterMinMax(String min, String max,String text) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
        this.hint = text;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        ToastHelper.showToast(hint);
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}