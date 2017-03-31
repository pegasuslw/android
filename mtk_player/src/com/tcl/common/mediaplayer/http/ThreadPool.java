package com.tcl.common.mediaplayer.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
//	public ExecutorService executor = Executors.newFixedThreadPool(3);
	public ExecutorService executor = Executors.newSingleThreadExecutor();
}
