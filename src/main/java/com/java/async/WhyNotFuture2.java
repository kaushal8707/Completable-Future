package com.java.async;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class WhyNotFuture2 {
	
	//We cannot combine multiple futures together

	
	public static void main(String[] args) throws InterruptedException, ExecutionException  {
		ExecutorService service = Executors.newFixedThreadPool(10);
		Future<List<Integer>> future1 = service.submit(()->{
			
			//you are doing API call
			System.out.println("Thread : "+Thread.currentThread().getName()); 
			return Arrays.asList(1,2,3,4,5);
		});
		
		Future<List<Integer>> future2 = service.submit(()->{
			
			//you are doing API call
			System.out.println("Thread : "+Thread.currentThread().getName()); 
			return Arrays.asList(1,2,3,4,5);
		});
		future1.get();
		future2.get();
	}
}
