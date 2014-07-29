/**
 * 自定义ListView适配器
 */
package com.example.frameimpl.control;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.frameimpl.layout.BaseLayout;

/**
 * Adapter基类
 * @author wqYuan
 *
 * @param <T>
 */
public abstract class BaseModelAdapter<T> extends BaseAdapter {

	protected Context mContext;

	protected List<T> mData;

	protected BaseLayout mLayout;

	/**
	 * 更换数据
	 * @param data
	 */
	public void setData(List<T> data) {
		this.mData = data;
	}

	public BaseModelAdapter(Context context, List<T> data) {
		this.mContext = context;
		this.mData = data;
	}

	public BaseModelAdapter(Context context, List<T> data, BaseLayout layout) {
		this.mContext = context;
		this.mData = data;
		this.mLayout = layout;
	}

	/**
	 * 添加数据
	 * @param listData
	 */
	public void addData(List<T> listData) {
		if (mData != null) {
			mData.addAll(listData);
		}
	}
	
	public void addData(T object){
		if (mData != null) {
			mData.add(object);
		}
	}
	
	public void addData(int location,T object){
		if (mData != null) {
			mData.add(location, object);
		}
	}
	
	public void deleteData(int location){
		if (mData != null){
			mData.remove(location);
		}
	}
	
	public void deleteData(T object){
		if (mData != null){
			mData.remove(object);
		}
	}

	@Override
	public int getCount() {
		// TODO
		if (mData == null)
			return 0;
		
		return mData.size();
	}

	/**
	 * 拿到mData存储的对象
	 */
	@Override
	public T getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
}
