package com.example.frameimpl.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

public class ShortCutUtil {
	/**
	 * 创建快捷方式
	 * 
	 * @param context
	 * @param shortcutName
	 * @param resourceId
	 * @param cls
	 */
	public static void creatShortCut(Context context, String shortcutName, int resourceId, Class<? extends Activity> cls) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");

		Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		shortcutintent.putExtra("duplicate", false);
		ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(context, resourceId);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		context.sendBroadcast(shortcutintent);
	}

	/**
	 * 删除
	 * 
	 * @param context
	 * @param shortcutName
	 * @param cls
	 */
	public static void deleteShortCut(Context context, String shortcutName, Class<? extends Activity> cls) {
		Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);

		Intent intent = new Intent();
		intent.setClass(context, cls);
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		context.sendBroadcast(shortcut);

		// String appClass = this.getPackageName() + "." +
		// this.getLocalClassName();
		// ComponentName comp = new ComponentName(this.getPackageName(),
		// appClass);
		// shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new
		// Intent(action).setComponent(comp));
	}

	/**
	 * 隐藏
	 * 
	 * @param context
	 * @param shortcutName
	 * @return
	 */
	public static boolean hasShortcut(Context context, String shortcutName) {
		String url = "";
		@SuppressWarnings("deprecation")
		int systemversion = Integer.parseInt(android.os.Build.VERSION.SDK);
		if (systemversion < 8) {
			url = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			url = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(url), null, "title=?", new String[] { shortcutName }, null);
		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}

	public static void hideDesktopIcon(Activity context) {
		PackageManager p = context.getPackageManager();
		p.setComponentEnabledSetting(context.getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
	}
	
	public static void ShowDesktopIcon(Activity context) {
		PackageManager p = context.getPackageManager();
		p.setComponentEnabledSetting(context.getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
	}
}
