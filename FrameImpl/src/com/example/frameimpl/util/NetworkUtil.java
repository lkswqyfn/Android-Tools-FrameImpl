package com.example.frameimpl.util;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetworkUtil {
	/**
	 * 判断是否有可用的网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	/**
	 * 检查当前网络状态,判断是WiFi连接还是基站连接？
	 * 
	 * @param context
	 * @return
	 */
	public int checkNetwork(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null)
			return networkInfo.getType();

		return -1;
	}
/**
 * 开启wifi
 * @param paramContext
 * @param paramBoolean
 */
	public static void enableWIFI(Context paramContext, boolean paramBoolean) {
		if (paramContext != null) {
			((WifiManager) paramContext.getSystemService("wifi")).setWifiEnabled(paramBoolean);
		}
	}
/**
 * 开启手机网络
 * @param paramContext
 * @param paramBoolean
 */
	public static void enableMobileConnection(Context paramContext, boolean paramBoolean) {
		if (paramContext != null) {
			ConnectivityManager connMgr = (ConnectivityManager) paramContext.getSystemService("connectivity");
			try {
				Method method = connMgr.getClass().getDeclaredMethod("setMobileDataEnabled", boolean.class);
				method.setAccessible(true);
				method.invoke(connMgr, paramBoolean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
/**
 * 拿到域名IP
 * @param domian
 * @return
 */
	public static String getHostAddress(String domian){
		String hostName = null;
		try {
			InetAddress inetHost = InetAddress.getByName(domian);
			hostName = inetHost.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}  
		return hostName;
	}
}
