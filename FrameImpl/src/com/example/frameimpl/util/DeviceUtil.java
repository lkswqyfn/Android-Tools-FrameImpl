package com.example.frameimpl.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class DeviceUtil {
	public final static String KEY_SITE="APP_KEY";
	
	public static String getWifiMac(Context context) {
		String address = "";
		try {
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			address = info.getMacAddress();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return address;
	}

	public static String getImei(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = "";
		if (tm != null) {
			imei = tm.getDeviceId();
		}
		return imei;
	}
	
	public static String getSite(Context context) {
		String site = "";
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			Bundle b = ai.metaData;
			Object object = b.get(KEY_SITE);
			if (object != null){
				site = String.valueOf(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return site;
	}
}
