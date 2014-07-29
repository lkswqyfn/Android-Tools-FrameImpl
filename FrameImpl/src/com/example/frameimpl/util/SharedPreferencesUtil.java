package com.example.frameimpl.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * SharedPreferences
 * @author wqYuan
 *
 */
public class SharedPreferencesUtil {
	public static void setSharedPreferencesValue(Context context, String name, String[] keys, Object[] values) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		Object value;
		for (int i = 0; i < keys.length; i++) {
			value = values[i];
			if (value instanceof String){
				edit.putString(keys[i], (String) value);
			}else if (value instanceof Integer){
				edit.putInt(keys[i], (Integer) value);
			}else if (value instanceof Boolean){
				edit.putBoolean(keys[i], (Boolean) value);
			}else if (value instanceof Long){
				edit.putLong(keys[i], (Long) value);
			}
		}
		edit.commit();
	}
	
	public static void clearSharedPreferencesValue(Context context,String name) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().clear().commit();
	}
	
	public static Object getSharedPreferencesValue(Context context,String name, String key) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getAll().get(key);
	}

	public static boolean containsSPKey(Context context,String name, String key) {
		return context.getSharedPreferences(name, Context.MODE_PRIVATE).contains(key);
	}
}
