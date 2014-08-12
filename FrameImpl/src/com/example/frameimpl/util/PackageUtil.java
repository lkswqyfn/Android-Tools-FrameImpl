package com.example.frameimpl.util;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;

/**
 * 应用管理
 * 
 * @author wqYuan
 * 
 */
public class PackageUtil {
	/**
	 * 根据路径开启应用
	 * 
	 * @param context
	 * @param appPath
	 */
	public static void startApk(Context context, String appPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(appPath, PackageManager.GET_ACTIVITIES);
		if (info != null) {
			String packageName = info.applicationInfo.packageName; // 得到安装包名称
			Intent intent = new Intent(Intent.ACTION_MAIN, null);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setPackage(packageName);
			List<ResolveInfo> activities = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);
			if (activities != null) {
				ResolveInfo resolveInfo = activities.iterator().next();
				if (resolveInfo != null) {
					String mainClass = resolveInfo.activityInfo.name;
					intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_LAUNCHER);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					ComponentName cn = new ComponentName(packageName, mainClass);
					intent.setComponent(cn);
					context.startActivity(intent);
				}
			}
		}
	}

	/**
	 * 根据apk路径查看该pak是否已经安装
	 * 
	 * @param context
	 * @param appPath
	 * @return
	 */
	public static boolean isInstall(Context context, String appPath) {
		PackageInfo info = getPackageInfo(context, appPath);
		if (info != null) {
			String packageName = info.applicationInfo.packageName;
			return isInstallByPackageName(context, packageName);
		}
		return false;
	}

	public static boolean isInstallByPackageName(Context context, String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 安装应用
	 * 
	 * @param context
	 * @param appPath
	 */
	public static void install(Context context, String appPath) {
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + appPath), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 卸载应用
	 * 
	 * @param context
	 * @param appPath
	 */
	public static boolean unInstall(Context context, String appPath) {
		PackageInfo info = getPackageInfo(context, appPath);
		if (info != null) {
			unInstallByPackageName(context, info.applicationInfo.packageName);
			return true;
		}
		return false;
	}

	public static PackageInfo getPackageInfo(Context context, String appPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(appPath, PackageManager.GET_ACTIVITIES);
		return info;
	}

	public static boolean unInstallByPackageName(Context context, String packageName) {
		try {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_DELETE);
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setData(Uri.parse("package:" + packageName));
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
