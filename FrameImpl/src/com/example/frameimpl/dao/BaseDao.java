package com.example.frameimpl.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseDao extends SQLiteOpenHelper {
	public static final String DB_NAME = "applite.db";
	public static final int VERSION = 1;

	public BaseDao(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public BaseDao(Context context) {
		this(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		onUpgrade(db, -1, VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
