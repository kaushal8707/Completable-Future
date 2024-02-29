package com.java.async;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.java.database.EmployeeDatabase;
import com.java.dto.Employee;


// The purpose of using this supplyAsync() , after executing the thread I am expecting the result something which is
// List of Employee.  I am just getting this value from the database. if you want to sort that value or if u 
// want to do some data manipulation. That transformation logic you can perform using other methods provided in 
// CompletableFuture i.e thenApply(), thenAccept() and thenRun().

public class SupplyAsyncDemo {
	public List<Employee> getEmployees() throws InterruptedException, ExecutionException{
		Executor executor = Executors.newCachedThreadPool();
		CompletableFuture<List<Employee>> listCompletableFuture = CompletableFuture
				.supplyAsync(()->{
					System.out.println("Executed By : "+Thread.currentThread().getName());
		   return EmployeeDatabase.fetchEmployee();	
		}, executor);
		
		return listCompletableFuture.get(); 
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
		List<Employee> employees = supplyAsyncDemo.getEmployees();
		employees.stream().forEach(System.out::println);
	}
}
