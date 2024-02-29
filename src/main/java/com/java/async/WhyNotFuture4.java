package com.java.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class WhyNotFuture4 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//Now once we call the get() method again it will block the main thread until and unless this completable future 
		//does not succeed. It will still block my thread to avoid that there is a feature called complete() method. 
		//If thread is taking a long time, then forcefully you can complete it and return some dummy data.
				
		CompletableFuture<String> completableFuture=new CompletableFuture<>(); 
		TimeUnit.SECONDS.sleep(10);
		System.out.println(completableFuture.get());
		System.out.println(completableFuture.complete("return some dummy data !!"));
	}

}
