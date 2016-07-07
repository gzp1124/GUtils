package com.gzp1124.gutils.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationListener;
//import com.amap.api.location.LocationManagerProxy;
//import com.amap.api.location.LocationProviderProxy;


/**
 * 高德定位工具类
 * 
 * @author gzp 2015-6-24 上午11:46:07 Copyright (C) 2015年 北京爱信游信息技术有限公司. All
 *         rights reserved.
 * 
 * 
 *         调用方式： //开始定位 GDMapUtil.startLocation(this, new LocationSuccess() {
 * @Override public void wSuccess(String location) { //定位成功location表示定位后的位置 }
 *           });
 * 
 */
public class GGDMapUtil {
//	private static LocationManagerProxy aMapManager;
//	private static LocationSuccess lSuccess;
//
//	/**
//	 * 开始定位
//	 *
//	 * @param activity
//	 * @param mSuccess
//	 *            定位后的回调接口
//	 */
//	public static void startLocation(Context activity, LocationSuccess mSuccess) {
//		if (!isOPen(activity)) {
//			mSuccess.wSuccess("无法获取");
//			return;
//		}
//
//
//
//		aMapManager = LocationManagerProxy.getInstance(activity);
//		// 赋值到成员
//		lSuccess = mSuccess;
//		aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork,
//				-1, 10, mAMapLocationListener);
//
//	}
//
//	/**
//	 * 停止定位
//	 */
//	@SuppressWarnings("deprecation")
//	private static void stopLocation() {
//		if (aMapManager != null) {
//			aMapManager.removeUpdates(mAMapLocationListener);
//			aMapManager.destory();
//		}
//		aMapManager = null;
//	}
//
//	/**
//	 * 定位成功的回调接口
//	 *
//	 * @author gzp 2015-6-24 上午11:49:23 Copyright (C) 2015年 北京爱信游信息技术有限公司. All
//	 *         rights reserved.
//	 */
//	public interface LocationSuccess {
//		/**
//		 * 定位成功后调用的方法
//		 *
//		 * @param location
//		 */
//		public void wSuccess(String location);
//	}
//
//	private static AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
//
//		@Override
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//			provider = provider.trim();
//			String s = String.valueOf(provider.charAt(provider.length()-1));
//			if("市".equals(s)){
//				provider = provider.substring(0,provider.length()-1);
//			}
//			lSuccess.wSuccess(provider);
//		}
//
//		@Override
//		public void onProviderEnabled(String provider) {
//			provider = provider.trim();
//			String s = String.valueOf(provider.charAt(provider.length()-1));
//			if("市".equals(s)){
//				provider = provider.substring(0,provider.length()-1);
//			}
//			lSuccess.wSuccess(provider);
//		}
//
//		@Override
//		public void onProviderDisabled(String provider) {
//			provider = provider.trim();
//			String s = String.valueOf(provider.charAt(provider.length()-1));
//			if("市".equals(s)){
//				provider = provider.substring(0,provider.length()-1);
//			}
//			lSuccess.wSuccess(provider);
//		}
//
//		@Override
//		public void onLocationChanged(Location location) {
//
//		}
//
//		@Override
//		public void onLocationChanged(AMapLocation location) {
//			if (location != null) {
//				Double geoLat = location.getLatitude();
//				Double geoLng = location.getLongitude();
//				String cityCode = "";
//				String desc = "";
//				Bundle locBundle = location.getExtras();
//				if (locBundle != null) {
//					cityCode = locBundle.getString("citycode");
//					desc = locBundle.getString("desc");
//				}
//				// 城市名
//				String res = location.getCity();
//				res = res.trim();
//				String s = String.valueOf(res.charAt(res.length()-1));
//				if("市".equals(s)){
//					res = res.substring(0,res.length()-1);
//				}
//				// 成功后的回调
//				lSuccess.wSuccess(res);
//				// 只定位一次
//				stopLocation();
//			} else {
//				// 成功后的回调
//				lSuccess.wSuccess("无法获取");
//				stopLocation();
//			}
//		}
//	};
//
//	/**
//	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
//	 *
//	 * @param context
//	 * @return true 表示开启
//	 */
//	public static final boolean isOPen(final Context context) {
//		LocationManager locationManager = (LocationManager) context
//				.getSystemService(Context.LOCATION_SERVICE);
//		boolean gps = locationManager
//				.isProviderEnabled(LocationManager.GPS_PROVIDER);
//		boolean network = locationManager
//				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//		if (gps || network) {
//			return true;
//		}
//		return false;
//	}
}
