package com.weima.aishangyi.map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.mb.android.utils.app.MBApplication;
import com.weima.aishangyi.R;

/**
 * Created by Teprinciple on 2016/8/23.
 * 地图上自定义的infowindow的适配器
 */
public class InfoWinAdapter implements AMap.InfoWindowAdapter{
    private Context mContext = MBApplication.getInstance().getApplicationContext();
    private LatLng latLng;

    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View view = initView();
        return view;
    }
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
        latLng = marker.getPosition();
    }

    @NonNull
    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_mark, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpPoint();
            }
        });
        return view;
    }

    public void jumpPoint() {
        try{
            Uri uri = Uri.parse("geo:"+latLng.latitude+","+latLng.longitude);
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            mContext.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}