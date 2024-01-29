package org.example;

import model.Employee;
import model.Job;
import service.EmployeeService;
import service.JobService;
import service.impl.EmployeeServiceImpl;
import service.impl.JobServiceImpl;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        EmployeeService employeeService = new EmployeeServiceImpl();
        JobService jobService = new JobServiceImpl();
//        employeeService.createEmployee();
//        jobService.createJobTable();
//        employeeService.addEmployee(new Employee("Nur", "Toi", 25, "nur@gmail.com"));
//        jobService.addJob(new Job("Instructor", "IT", "backend", 5, 1));
//        employeeService.dropTable();
//        employeeService.cleanTable();
//        System.out.println(employeeService.getEmployeeById(2L));
//        System.out.println(employeeService.getAllEmployees());
//        System.out.println(jobService.getJobByEmployeeId(1L));
//        System.out.println(jobService.getJobById(1L));
//        System.out.println(employeeService.getAllEmployees());
//        System.out.println(employeeService.getEmployeeByPosition("mentor"));
//        System.out.println(jobService.getJobById(2L));
//        System.out.println(jobService.sortByExperience("asc"));
//        System.out.println(jobService.sortByExperience("desc"));

//        employeeService.updateEmployee(5L, new Employee("Zepa", "alapaeva", 27, "zepa@gmail.com"));

//        System.out.println(employeeService.getAllEmployees());

//        System.out.println(employeeService.findByEmail("nur@gmail.com"));

    }
}
