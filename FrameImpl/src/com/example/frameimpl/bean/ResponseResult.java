package com.example.frameimpl.bean;

public class ResponseResult<T> {
	private boolean hadError = false;
	private boolean isSuccess = false;
	private T resultObj;
	private int allCount = 0;
	private Object obj;

	public boolean isHadError() {
		return hadError;
	}

	public void setHadError(boolean hadError) {
		this.hadError = hadError;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public T getResultObj() {
		return resultObj;
	}

	public void setResultObj(T resultObj) {
		this.resultObj = resultObj;
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

}
