package com.example.frameimpl.test;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.test.AndroidTestCase;

public class FrameImplTest extends AndroidTestCase {
		public void testAnnotation(){
			BeanTest test = new BeanTest();
			ContentValues values = test.getContentValues();
			System.out.println(values);
		}
		
		public void testBaseAdapter(){
			List<String> list = new ArrayList<String>();
			System.out.println(list.getClass().getName() + '@' + Integer.toHexString(list.hashCode()));
			AdapterTest test = new AdapterTest(getContext(), list);
			test.info();
			list.add("cdcd");
			test.info();
			list.add("cdcddd");
			test.info();
			list.add("cdcdfv");
			test.info();
		}
}
