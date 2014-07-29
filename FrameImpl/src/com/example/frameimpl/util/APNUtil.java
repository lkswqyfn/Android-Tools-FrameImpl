package com.example.frameimpl.util;

import org.apache.http.HttpHost;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

@SuppressWarnings("all")
public class APNUtil {
	public static final String A = "port";
	public static final byte B = 0;
	public static final byte C = 1;
	public static final byte D = 2;
	public static final byte E = 3;
	public static final byte F = 4;
	public static final byte G = 5;
	public static final byte H = 6;
	public static final byte I = 7;
	public static final byte J = 8;
	public static final byte K = 9;
	private static Uri PROXYPRE = Uri.parse("content://telephony/carriers/preferapn");
	public static final int a = 1;
	public static final int b = 2;
	public static final int c = 4;
	public static final int d = 8;
	public static final int e = 16;
	public static final int f = 32;
	public static final int g = 64;
	public static final int h = 128;
	public static final int i = 256;
	public static final int j = 512;
	public static final int k = 1024;
	public static final int l = 2048;
	public static final String m = "wifi";
	public static final String n = "cmwap";
	public static final String o = "cmnet";
	public static final String p = "uniwap";
	public static final String q = "uninet";
	public static final String r = "wap";
	public static final String s = "net";
	public static final String t = "ctwap";
	public static final String u = "ctnet";
	public static final String v = "3gwap";
	public static final String w = "3gnet";
	public static final String x = "none";
	public static final String y = "apn";
	public static final String z = "proxy";

	public static String getAPNName(Context paramContext) {
		int i1 = getAPNType(paramContext);
		String str;
		if (i1 == 2) {
			str = "wifi";
		}
		if (i1 == 1) {
			return "cmwap";
		}
		if (i1 == 4) {
			return "cmnet";
		}
		if (i1 == 16) {
			return "uniwap";
		}
		if (i1 == 8) {
			return "uninet";
		}
		if (i1 == 64) {
			return "wap";
		}
		if (i1 == 32) {
			return "net";
		}
		if (i1 == 512) {
			return "ctwap";
		}
		if (i1 == 256) {
			return "ctnet";
		}
		if (i1 == 2048) {
			return "3gnet";
		}
		if (i1 == 1024) {
			return "3gwap";
		}
		str = getAPNNameByAPI(paramContext);
		if ((str != null) && (str.length() > 0))
			return str;
		return "none";
	}

	public static String getAPNNameByAPI(Context paramContext) {
		int i1 = Build.VERSION.SDK_INT;
		String str = null;
		Cursor localCursor;
		if (i1 <= 16) {
			localCursor = paramContext.getContentResolver().query(PROXYPRE, null, null, null, null);
			if (localCursor != null) {
				localCursor.moveToFirst();
				boolean bool = localCursor.isAfterLast();
				if (!bool) {
					str = localCursor.getString(localCursor.getColumnIndex("apn"));
				}
				localCursor.close();
			}
		}
		return str;
	}

	public static String getProxyHost(Context paramContext) {
		if (Build.VERSION.SDK_INT > 16) {
			return Proxy.getDefaultHost();
		}
		String str = null;
		Cursor localCursor = paramContext.getContentResolver().query(PROXYPRE, null, null, null, null);
		if (localCursor != null) {
			localCursor.moveToFirst();
			boolean bool = localCursor.isAfterLast();
			if (!bool) {
				str = localCursor.getString(localCursor.getColumnIndex("proxy"));
			}
			localCursor.close();
		}
		return str;
	}

	public static String getProxyPort(Context paramContext) {
		if (Build.VERSION.SDK_INT > 16) {
			int i1 = Proxy.getDefaultPort();
			return String.valueOf(i1);
		}

		Cursor localCursor;
		String str = null;
		localCursor = paramContext.getContentResolver().query(PROXYPRE, null, null, null, null);
		if (localCursor != null) {
			localCursor.moveToFirst();
			boolean bool = localCursor.isAfterLast();
			if (!bool) {
				str = localCursor.getString(localCursor.getColumnIndex("port"));
			}
			localCursor.close();
		}
		return str;
	}

	public static int getProxyPort1(Context paramContext) {
		if (Build.VERSION.SDK_INT > 16) {
			return Proxy.getDefaultPort();
		}
		int i1 = -1;
		Cursor localCursor = paramContext.getContentResolver().query(PROXYPRE, null, null, null, null);
		if (localCursor == null) {
			localCursor.moveToFirst();
			if (!localCursor.isAfterLast()) {
				i1 = localCursor.getInt(localCursor.getColumnIndex("port"));
			}
			localCursor.close();
		}
		return i1;
	}

	public static boolean checkProxy(Context paramContext) {
		if (getAPNType(paramContext) == 2) {
			return false;
		}
		return checkProxyHost(paramContext);
	}

	public static boolean checkProxyHost(Context paramContext) {
		String str = getProxyHost(paramContext);
		if ((str != null) && (str.length() > 0))
			return true;
		return false;
	}

	public static int getAPNType(Context content) {
		try {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) content.getSystemService("connectivity")).getActiveNetworkInfo();
			if (localNetworkInfo == null) {
				return 128;
			}
			if (localNetworkInfo.getTypeName().toUpperCase().equals("WIFI")) {
				return 2;
			}
			String str1 = localNetworkInfo.getExtraInfo();
			if (str1 == null) {
				return 128;
			}
			String str2 = str1.toLowerCase();
			Log.d("APNUtil", "extraInfo:" + str2);
			if (str2.startsWith("cmwap")) {
				return 1;
			}
			if ((!str2.startsWith("cmnet")) && (!str2.startsWith("epc.tmobile.com"))) {
				if (str2.startsWith("uniwap")) {
					return 16;
				}
				if (str2.startsWith("uninet")) {
					return 8;
				}
				if (str2.startsWith("3gwap")) {
					return 1024;
				}
				if (str2.startsWith("3gnet")) {
					return 2048;
				}
				if (str2.startsWith("ctwap")) {
					return 512;
				}
				if (str2.startsWith("ctnet")) {
					return 256;
				}
				if (str2.startsWith("wap")) {
					return 64;
				}
				if (str2.startsWith("internet")) {
					return 2;
				}
				if (str2.startsWith("net")) {
					return 32;
				}
				if (str2.startsWith("#777")) {
					String str3 = getProxyHost(content);
					if (str3 != null) {
						int i1 = str3.length();
						if (i1 > 0) {
							return 512;
						}
					}
					return 256;
				}
			}
		} catch (Exception localException) {
			if (checkProxyHost(content)) {
				return 64;
			}
			return 32;
		}
		return 4;
	}

	public static String i(Context paramContext) {
		NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
		if (localNetworkInfo != null) {
			return localNetworkInfo.getTypeName();
		}
		return "MOBILE";
	}

	public static boolean j(Context paramContext) {
		NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
		return (localNetworkInfo != null) && (localNetworkInfo.getState() == NetworkInfo.State.CONNECTED);
	}

	public static boolean k(Context paramContext) {
		NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
		if (localNetworkInfo != null) {
			return localNetworkInfo.isAvailable();
		}
		return false;
	}

	public static HttpHost l(Context paramContext) {
		HttpHost localHttpHost = null;
		if (checkProxy(paramContext)) {
			String str = getProxyHost(paramContext);
			int i1 = getProxyPort1(paramContext);
			if (str != null) {
				localHttpHost = new HttpHost(str, i1);
			}
		}
		return localHttpHost;
	}
}