package com.weima.aishangyi.map;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class AddressActivity extends BaseActivity implements AMap.OnMapClickListener, AMap.OnMarkerClickListener  {
    private MapView mapView;
    private AMap aMap;
    private String latitude;
    private String longitude;
    private UiSettings uiSettings;
    private InfoWinAdapter adapter;
    private Marker oldMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("爱尚艺");
        setContentView(R.layout.activity_address);
        mapView = findView(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initMap();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            aMap.setOnMapClickListener(this);
        }
        LatLng latLng = null;
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        if (latitude==null||longitude==null){
            latLng = new LatLng(26.08,119.3);
        }else{
            latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        }

        uiSettings.setZoomControlsEnabled(false); //隐藏缩放控件
        //自定义InfoWindow
        aMap.setOnMarkerClickListener(this);
        adapter = new InfoWinAdapter();
        aMap.setInfoWindowAdapter(adapter);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                latLng, 18, 30, 0)));
        addMarkerToMap(latLng,"爱尚艺","");
    }


    //地图的点击事件
    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图上没marker 的地方，隐藏inforwindow
        if (oldMarker != null) {
            oldMarker.hideInfoWindow();
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal));
        }
    }

    //maker的点击事件
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (oldMarker != null) {
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal));
        }
        oldMarker = marker;
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal));
        return false; //返回 “false”，除定义的操作之外，默认操作也将会被执行
    }

    private void addMarkerToMap(LatLng latLng, String title, String snippet) {
        aMap.clear();
        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latLng)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal))
        );
    }




}
