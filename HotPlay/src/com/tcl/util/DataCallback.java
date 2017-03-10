package com.tcl.util;

public interface DataCallback<ResultType>{
	public void onSuccess(ResultType t);
    public void onFailure(String str);
}
