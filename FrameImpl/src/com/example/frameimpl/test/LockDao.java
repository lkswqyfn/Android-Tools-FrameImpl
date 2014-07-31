package com.example.frameimpl.test;

import android.content.Context;

import com.example.frameimpl.dao.BaseDao;

public class LockDao extends BaseDao {
	private static LockDao instance;

	private LockDao(Context context) {
		super(context);
	}

	public static LockDao getInstance(Context context) {
		if (instance == null) {
			instance = new LockDao(context);
		}
		return instance;
	}

	public void read(int x) {
		readLock.lock();
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println("read" + x + ":" + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}finally {
			readLock.unlock();
		}
	}

	public void write() {
		writeLock.lock();
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println("write:" + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}finally {
			writeLock.unlock();
		}
	}
}
