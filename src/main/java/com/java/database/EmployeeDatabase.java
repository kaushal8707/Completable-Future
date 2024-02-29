package com.java.database;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.dto.Employee;

public class EmployeeDatabase {

	public static List<Employee> fetchEmployee(){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper
					.readValue(new File("employee.json"), new TypeReference<List<Employee>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null; 
	}

}
