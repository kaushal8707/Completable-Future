package com.java.async;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.dto.Employee;
// We need to use this runAsync() method if u are not expecting any return type after your thread execution. 
// Let’s say I have some file and I want to extract that file and I want to process that object to my database, 
// and after that I can directly verify the data into the database rather than giving some return statement from thread.
// in that case you can go for runAsync() method().

public class RunAsyncDemo {
	
	//anonymous implementation of Runnable
	public Void saveEmployees(File jsonFile) throws InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(new Runnable() {
			
			@Override
			public void run() {
				try {
					List<Employee> employees = mapper
							.readValue(jsonFile, new TypeReference<List<Employee>>() {});
					//write logic to save list of employee to database
					//repository.saveAll(employees)		
					System.out.println("Thread : "+Thread.currentThread().getName());
					//employees.stream().forEach(System.out::println);
					System.out.println("Total Employees : "+employees.size()); 
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		});
		return runAsyncFuture.get();
	}
	
	//converted to lambda
	public Void saveEmployeesWithCustomExecutor(File jsonFile) throws InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		Executor executor = Executors.newFixedThreadPool(5);
		CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(
				()-> {
				try {
					List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {});
					//write logic to save list of employee to database
					//repository.saveAll(employees)		
					System.out.println("Thread : "+Thread.currentThread().getName());
					System.out.println("Total Employees : "+employees.size()); 
				} catch (IOException e) {
					e.printStackTrace();
				} 
		}, executor);
		
		runAsyncFuture.toCompletableFuture();
		return runAsyncFuture.get();

	}
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		RunAsyncDemo asyncDemo = new RunAsyncDemo();
		asyncDemo.saveEmployees(new File("employee.json"));
		System.out.println("====================");
		asyncDemo.saveEmployeesWithCustomExecutor(new File("employee.json"));

	}
}
