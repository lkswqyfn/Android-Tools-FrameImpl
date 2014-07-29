package com.example.frameimpl.bean;

import java.lang.reflect.Field;

import com.example.frameimpl.annotation.Fieldfilter;

import android.content.ContentValues;

/**
 * Bean的基类
 * @author wqYuan
 *
 */
public abstract class BaseBean {
	/**
	 * 获取字段的ContentValues
	 * @return
	 */
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		Field[] fields = getClass().getFields();
		for (Field item : fields) {
			if (item.getAnnotation(Fieldfilter.class) != null)
				continue;
			
			try {
				Object obj = item.get(this);
				if (obj != null) {
					if (obj instanceof String)
						values.put(item.getName(), (String) obj);
					if (obj instanceof Long)
						values.put(item.getName(), (Long) obj);
					if (obj instanceof Integer)
						values.put(item.getName(), (Integer) obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return values;
	}
}
