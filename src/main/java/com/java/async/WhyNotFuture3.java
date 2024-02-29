package com.java.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class WhyNotFuture3 {

	//No Exception Handling mechanism provided in Future Object
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(10);
		Future<List<Integer>> future = service.submit(()->{
			//you are doing API call
			System.out.println("Thread : "+Thread.currentThread().getName()); 
			System.out.println(10/0); 
			return Arrays.asList(1,2,3,4,5);
		});
		
		List<Integer> integers = future.get();
		System.out.println(integers); 
	}

}
