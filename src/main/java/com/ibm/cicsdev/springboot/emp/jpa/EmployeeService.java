package com.ibm.cicsdev.springboot.emp.jpa;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Emp> selectAll() {
		System.out.println("selectAll: EmployeeService");
		List<Emp> employees = new ArrayList<>();  //define a new list
		employeeRepository.findAll()                   //iterate through each data item
		.forEach(employees::add);                      // and add to list
		System.out.println("returning from selectAll");
		return employees;

	}

	public Optional<Emp> selectWhereEmpno(String empNo) {
		/*
		 * Return all rows for a specific employee number
		 */
		
		return employeeRepository.findById(empNo);
	}


	public String addEmployee(String fName, String lName) {
		/*
		 *  Add a new employee.
		 *      Firstname and lastname are passed in 
		 *      
		 *      for demo purposes all the other fields are set by this method
		 *      
		 */

		//generate an empNo between 300000 and 999999
		int max = 999999;
		int min = 300000;
		String empno = String.valueOf((int) Math.round((Math.random()*((max-min)+1))+min));

		//for demo purposes hard code all the remaining fields (except first name and last name) 
		String midInit = "A";
		String workdept = "E21";
		String phoneNo = "1234";
		LocalDate today = LocalDate.now();
		String hireDate = today.toString();
				
		String job = "Engineer";
		int edLevel =3 ;
		String sex ="M";
		String birthDate = "1999-01-01" ;
		long salary = 20000;
		long bonus= 1000;
		long comm = 1000;
		
		Emp newEmployee = new Emp(empno,fName,midInit,lName,workdept,phoneNo,hireDate,job,edLevel,sex,birthDate,salary,bonus,comm);

		try {
			employeeRepository.save(newEmployee);
			
			return "employee " + empno + " added";
		} catch (Exception e) {
			return "employee insert failed try again";
		}

	}


	public String deleteEmployee(String empNo)	{
		/*
		 *  Delete an employee based on the empNo passed in
		 *  
		 */
		try {
			employeeRepository.deleteById(empNo);

			return "employee " + empNo + " deleted";
		} catch (Exception e) {

			return "employee delete failed try again";
		}
	}


	public String updateEmployee(String employeeToUpdate, long newSalary) {
		/*
		 * Update a specified employee's salary based on the empNo passed to the salary passed in.
		 * 
		 */
		
		Emp emp = null;
		if (employeeRepository.existsById(employeeToUpdate)) {
			emp = employeeRepository.getOne(employeeToUpdate);
			emp.setSalary(newSalary);
		} else
		{
			return "Employee " + employeeToUpdate + " does not exist";
		}
		
		try {
			employeeRepository.save(emp);
			return "Employee " + employeeToUpdate + " salary updated";
		} catch (Exception e) {
			System.out.println(e);
			return "Employee " + employeeToUpdate + " update failed - try again";
		}

	}		
		
		
	public Emp selectWhereEmpno1(String empNo) {
		return employeeRepository.findByEmpNo(empNo);
	}
}
