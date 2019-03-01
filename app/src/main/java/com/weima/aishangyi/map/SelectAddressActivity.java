package com.weima.aishangyi.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.mb.android.utils.AppHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.pop.SelectAddressPop;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.widget.ClearableEditText;

import java.util.List;

/**
 * 选择地址
 *
 * @author cgy
 */
public class SelectAddressActivity extends BaseActivity implements View.OnClickListener,AMap.OnMarkerClickListener, AMap.InfoWindowAdapter,AMapLocationListener,LocationSource,PoiSearch.OnPoiSearchListener {
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	private MapView mapview;
	private AMap mAMap;
	private PoiResult poiResult; // poi返回的结果
	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch poiSearch;
	private List<PoiItem> poiItems;// poi数据
	private ClearableEditText mSearchText;
	private String city = "";
	private double lat = 26.08;
	private double lon = 119.3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_address);
		setCustomTitle("选择地址");
		mapview = (MapView)findViewById(R.id.mapView);
		mapview.onCreate(savedInstanceState);
		initUI();
		initMap();
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
		findView(R.id.btn_search).setOnClickListener(this);
		mSearchText = findView(R.id.input_edittext);
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
		if (mAMap == null) {
			mAMap = mapview.getMap();
			mAMap.moveCamera(CameraUpdateFactory.zoomTo(16));
			mAMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
			mAMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
		}
	}

	/**
	 * 开始进行poi搜索
	 */
	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		AppHelper.hideSoftInputFromWindow(mSearchText);
		ProgressDialogHelper.showProgressDialog(SelectAddressActivity.this, "搜索中...");
		currentPage = 0;
		query = new PoiSearch.Query(mSearchText.getText().toString(), "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(10);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页
		query.setCityLimit(true);
		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.searchPOIAsyn();
	}

	private void doSearchNear(){
		AppHelper.hideSoftInputFromWindow(mSearchText);
		ProgressDialogHelper.showProgressDialog(SelectAddressActivity.this, "搜索中...");
		currentPage = 0;
		query = new PoiSearch.Query("", "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(10);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页
		query.setCityLimit(true);
		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat,
				lon), 6000));// 设置周边搜索的中心点以及区域
		poiSearch.searchPOIAsyn();
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
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



	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.btn_search){
			ProjectHelper.disableViewDoubleClick(v);
			if (Helper.isEmpty(mSearchText.getText().toString())){
				ToastHelper.showToast("请输入搜索关键字");
			}else{
				doSearchQuery();
			}
		}
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null) {
			if (amapLocation.getErrorCode() == 0) {
				city = Helper.isEmpty(amapLocation.getCity())?"":amapLocation.getCity();
				mAMap.addMarker(new MarkerOptions()
						.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
				mAMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
				lat = amapLocation.getLatitude();
				lon = amapLocation.getLongitude();
				doSearchNear();
				deactivate();
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
				Log.e("AmapErr", errText);
			}
		}
	}

	@Override
	public void onPoiSearched(PoiResult result, int rcode) {
		ProgressDialogHelper.dismissProgressDialog();
		if (rcode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					// 取得搜索到的poiitems有多少页
					List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

					if (poiItems != null && poiItems.size() > 0) {
						mAMap.clear();// 清理之前的图标
						PoiOverlay poiOverlay = new PoiOverlay(mAMap, poiItems);
						poiOverlay.removeFromMap();
						poiOverlay.addToMap();
						poiOverlay.zoomToSpan();
						SelectAddressPop selectAddressPop = new SelectAddressPop(SelectAddressActivity.this, poiItems, new SelectAddressPop.SelectListener() {
							@Override
							public void onSelected(PoiItem poiItem) {
								if (Helper.isNotEmpty(poiItem)){
									String address = poiItem.getTitle();
									if (Helper.isNotEmpty(address)){
										Intent intent = new Intent();
										intent.putExtra(ProjectConstants.BundleExtra.KEY_ADDRESS_NAME, address);
										intent.putExtra(ProjectConstants.BundleExtra.KEY_ADDRESS_LAT,poiItem.getLatLonPoint().getLatitude());
										intent.putExtra(ProjectConstants.BundleExtra.KEY_ADDRESS_LON,poiItem.getLatLonPoint().getLongitude());
										setResult(RESULT_OK, intent);
										finish();
									}
								}
							}
						});
						selectAddressPop.show(mapview);
					} else if (suggestionCities != null
							&& suggestionCities.size() > 0) {
						showSuggestCity(suggestionCities);
					} else {
						ToastHelper.showToast("对不起，没有搜索到相关数据！");
					}
				}
			} else {
				ToastHelper.showToast("对不起，没有搜索到相关数据！");
			}
		}
	}

	@Override
	public void onPoiItemSearched(PoiItem item, int rCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		marker.showInfoWindow();
		return false;
	}

	@Override
	public View getInfoContents(Marker marker) {
		return null;
	}

	@Override
	public View getInfoWindow(final Marker marker) {
		View view = getLayoutInflater().inflate(R.layout.map_custom_windowinfo, null);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(marker.getTitle());

		TextView snippet = (TextView) view.findViewById(R.id.snippet);
		snippet.setText(marker.getSnippet());
		ImageButton button = (ImageButton) view.findViewById(R.id.start_amap_app);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String address = marker.getTitle();
				if (Helper.isNotEmpty(address)){
					Intent intent = new Intent();
					intent.putExtra(ProjectConstants.BundleExtra.KEY_ADDRESS_NAME, address);
					intent.putExtra(ProjectConstants.BundleExtra.KEY_ADDRESS_LAT,marker.getPosition().latitude);
					intent.putExtra(ProjectConstants.BundleExtra.KEY_ADDRESS_LON,marker.getPosition().longitude);
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});
		return view;
	}

	/**
	 * poi没有搜索到数据，返回一些推荐城市的信息
	 */
	private void showSuggestCity(List<SuggestionCity> cities) {
		String infomation = "推荐城市\n";
		for (int i = 0; i < cities.size(); i++) {
			infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
					+ cities.get(i).getCityCode() + "城市编码:"
					+ cities.get(i).getAdCode() + "\n";
		}
		ToastHelper.showToast(infomation);
	}
}
