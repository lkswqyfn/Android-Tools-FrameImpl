package com.example.frameimpl.test;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.frameimpl.control.BaseModelAdapter;

public class AdapterTest extends BaseModelAdapter<String> {

	public AdapterTest(Context context, List<String> data) {
		super(context, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return null;
	}

	public void info(){
		System.out.println(mData.getClass().getName() + '@' + Integer.toHexString(mData.hashCode()));
		System.out.println("count:"+mData.size());
	}
	
}
