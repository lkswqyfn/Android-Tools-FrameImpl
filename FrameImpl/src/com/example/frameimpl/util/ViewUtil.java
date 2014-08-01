package com.example.frameimpl.util;

import java.lang.reflect.Field;

import android.view.View;

import com.example.frameimpl.annotation.ViewAnnotation;

public class ViewUtil {
	public static void initViewByAnnotation(View view, Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field item : fields) {
			ViewAnnotation annotation = item.getAnnotation(ViewAnnotation.class);
			if (annotation != null) {
				View viewById = view.findViewById(annotation.value());
				if (viewById != null) {
					try {
						item.setAccessible(true);
						item.set(obj, viewById);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
