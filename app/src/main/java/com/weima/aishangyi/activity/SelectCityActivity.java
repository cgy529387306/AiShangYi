package com.weima.aishangyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.CityAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.constants.UrlConstants;
import com.weima.aishangyi.entity.CityBean;
import com.weima.aishangyi.entity.CityResp;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.LessonListResp;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 城市选择
 *
 * @author cgy
 */
public class SelectCityActivity extends BaseActivity implements View.OnClickListener,AMapLocationListener,LocationSource {
	private ListView listView;
	private TextView txv_current_location;
	private LocationSource.OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	private List<CityBean> list = new ArrayList<>();
	private CityAdapter adapter;
	private String city = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_city);
		setCustomTitle("选择城市");
		initUI();
		initMap();
		requestData();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		deactivate();
	}

	private void initUI() {
		listView = findView(R.id.listView);
		listView.addHeaderView(getHeaderView());
		adapter = new CityAdapter(SelectCityActivity.this,list);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onResponseSuccess(int gact, String response,
									 Object... extras) {
		CityResp entity = JsonHelper.fromJson(response, CityResp.class);
		if ("200".equals(entity.getCode())){
			if(Helper.isNotEmpty(entity.getData())){
				list = entity.getData();
				adapter.updataList(list);
			}
		}else{
			ToastHelper.showToast(entity.getMessage());
		}
		return true;
	}

	@Override
	public boolean onResponseError(int gact, String response,
								   VolleyError error, Object... extras) {
		return true;
	}

	private void initMap(){
		mlocationClient = new AMapLocationClient(this);
		mLocationOption = new AMapLocationClientOption();
		//设置定位监听
		mlocationClient.setLocationListener(this);
		//设置为高精度定位模式
		mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		//设置定位参数
		mlocationClient.setLocationOption(mLocationOption);
		mlocationClient.startLocation();
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(LocationSource.OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			mlocationClient.startLocation();
		}
	}
	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	private View getHeaderView(){
		View view = LayoutInflater.from(SelectCityActivity.this).inflate(R.layout.header_select_city, null);
		txv_current_location = (TextView) view.findViewById(R.id.txv_current_location);
		txv_current_location.setOnClickListener(this);
		txv_current_location.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(ProjectConstants.BundleExtra.KEY_CITY_NAME, ProjectHelper.getCommonText(city));
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		return view;
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.txv_current_location){
			if(!"定位中...".equals(txv_current_location.getText().toString())){
				Intent intent = new Intent();
				intent.putExtra(ProjectConstants.BundleExtra.KEY_CITY_NAME, txv_current_location.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null) {
			if (amapLocation.getErrorCode() == 0) {
				city = Helper.isEmpty(amapLocation.getCity())?"":amapLocation.getCity();
				txv_current_location.setText(city);
				deactivate();
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
				txv_current_location.setText(errText);
				Log.e("AmapErr", errText);
			}
		}
	}

	private void requestData(){
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		post(UrlConstants.HOME_CITY_ALL_URL, requestMap);
	}

}
