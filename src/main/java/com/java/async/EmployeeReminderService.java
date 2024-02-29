package com.java.async;
//	In this part we will understand how to chain multiple future together. I mean after getting result from thread  
//	execution how we can transform that value or how we can pass that value to another thread for execution.
//	There is few methods provided by CompletableFuture :

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import com.java.database.EmployeeDatabase;
import com.java.dto.Employee;

// 	thenApply(Function) -> takes the argument as Function which means it will take the input from you or after thread execution it will get the input and it will do data manipulation then it will return something.
// 	thenAccept(Consumer) -> takes the argument as Consumer, and as you know Consumer is a Functional Interface. It will always take input, but it won’t return anything. 
// 	thenRun(Runnable) -> if you see thenAccept() and thenRun() both are a same kind of methods. But the argument is only different. In case of thenRun() it will take input as a runnable. In both the methods it won’t return anything.


public class EmployeeReminderService 
{
    public  Void sendReminderToEmployee() throws InterruptedException, ExecutionException {
    	Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("fetchEmployee : " + Thread.currentThread().getName());
            return EmployeeDatabase.fetchEmployee();
        },executor).thenApplyAsync((employees) -> {
            System.out.println("filter new joiner employee  : " + Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "TRUE".equals(employee.getNewJoiner()))
                    .collect(Collectors.toList());
        },executor).thenApplyAsync((employees) -> {
            System.out.println("filter training not complete employee  : " + Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "TRUE".equals(employee.getLearningPending()))
                    .collect(Collectors.toList());
        },executor).thenApplyAsync((employees) -> {
            System.out.println("get emails  : " + Thread.currentThread().getName());
            return employees.stream().map(Employee::getEmail).collect(Collectors.toList());
        },executor).thenAcceptAsync((emails) -> {
            System.out.println("send email  : " + Thread.currentThread().getName());
            emails.forEach(EmployeeReminderService::sendEmail);
        },executor);
        return completableFuture.get();
    }


    public static void sendEmail(String email) {
        System.out.println("sending training reminder email to : " + email);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EmployeeReminderService service=new EmployeeReminderService();
        service.sendReminderToEmployee();
    }
}
