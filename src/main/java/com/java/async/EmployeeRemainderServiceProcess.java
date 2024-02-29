package com.java.async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.java.database.EmployeeDatabase;
import com.java.dto.Employee;

public class EmployeeRemainderServiceProcess {

	public Void sendRemainder() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		 CompletableFuture<Void> completableFeature = CompletableFuture.supplyAsync(()->
		  {
				System.out.println("Fetch Employee : "+Thread.currentThread().getName()); 
				return EmployeeDatabase.fetchEmployee();
		  },executor).thenApplyAsync(employee->{
				System.out.println("filter new joiner employee  : : "+Thread.currentThread().getName()); 
				return employee.stream().filter(emp->emp.getNewJoiner().equals("TRUE"))
								.collect(Collectors.toList());
		  },executor).thenApplyAsync(employee->{
				System.out.println("filter training not complete employee : "+Thread.currentThread().getName()); 
				return employee.stream().filter(emp->emp.getLearningPending().equals("TRUE"))
								.collect(Collectors.toList());
			  
		  },executor).thenApplyAsync(employee->{
				System.out.println("get emails  : "+Thread.currentThread().getName()); 
				return employee.stream().map(Employee::getEmail).collect(Collectors.toList());
			  
		  },executor).thenAcceptAsync((emails)->{
				System.out.println("send emails  : "+Thread.currentThread().getName()); 
		  		emails.forEach(System.out::println);
		  },executor); 
		return completableFeature.get();  
	}
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		EmployeeRemainderServiceProcess process = new EmployeeRemainderServiceProcess();
		process.sendRemainder();
	}

}
