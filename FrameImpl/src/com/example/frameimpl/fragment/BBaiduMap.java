package com.example.frameimpl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

public class BBaiduMap extends BaseFragment{
	BaiduMap mBaiduMap;
	private MapView mMapView;
	private Button button;
	private EditText et;
	private PoiSearch mPoiSearch;
	@Override
	public void onClick(View v) {
		String key = et.getText().toString();
		mPoiSearch = PoiSearch.newInstance();
		OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
			@Override
			public void onGetPoiResult(PoiResult result) {
				if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR){
					PoiOverlay overlay = new PoiOverlay(mBaiduMap);
					overlay.setData(result);
					overlay.addToMap();
					overlay.zoomToSpan();
				}
			}
			
			@Override
			public void onGetPoiDetailResult(PoiDetailResult arg0) {
			}
		};
		mPoiSearch.setOnGetPoiSearchResultListener(listener);
		PoiCitySearchOption option = new PoiCitySearchOption().city("北京").keyword(key).pageNum(10);
		mPoiSearch.searchInCity(option );
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout ll_item = new LinearLayout(getActivity());
		ll_item.setOrientation(LinearLayout.HORIZONTAL);
		et = new EditText(getActivity());
		LayoutParams params = new LayoutParams(0,LayoutParams.WRAP_CONTENT,1);
		ll_item.addView(et,params);
		
		button = new Button(getActivity());
		button.setText("确定");
		ll_item.addView(button);
		
		ll.addView(ll_item);
		
		mMapView = new MapView(getActivity());
		ll.addView(mMapView);
		
		return ll;
	}

	@Override
	public void initView() {
		mBaiduMap = mMapView.getMap();
	}

	@Override
	public void initData() {
//		LatLng lng = new LatLng(39.963175, 116.400244);
//		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.qq_blue);
//		MarkerOptions icon = new MarkerOptions().position(lng).icon(bitmap);
//		mBaiduMap.addOverlay(icon);
//		ImageView iv = new ImageView(getActivity());
//		iv.setImageResource(R.drawable.qq_blue);
//		LatLng lng = new LatLng(39.963175, 116.400244);
//		OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
//			@Override
//			public void onInfoWindowClick() {
//				Toast.makeText(getActivity(), "qq", 0).show();
//			}
//		};
//		InfoWindow window = new InfoWindow(iv, lng, listener);
//		mBaiduMap.showInfoWindow(window);
	}

	@Override
	public void initListener() {
		button.setOnClickListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}
}
