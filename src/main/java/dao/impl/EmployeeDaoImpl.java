package dao.impl;

import configuration.JdbcConfig;
import dao.EmployeeDao;
import model.Employee;
import model.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public void createEmployee() {
        try {
            String query = """
                    create table if not exists employees(
                    id serial primary key,
                    first_name varchar,
                    last_name varchar,
                    age int,
                    email varchar
                    );
                    """;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addEmployee(Employee employee) {
        try {
            String query = """
                    insert into employees(first_name, last_name, age, email)
                    values (?, ?, ?, ?);
                    """;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setInt(3, employee.getAge());
                preparedStatement.setString(4, employee.getEmail());
                int i = preparedStatement.executeUpdate();
                if(i > 0) System.out.println("successfully saved");
                else System.out.println("error");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        try {
            String query = "drop table employees";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                int i = preparedStatement.executeUpdate();
                if(i > 0) System.out.println("successfully dropped");
                else System.out.println("error");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void cleanTable() {
        try {
            String query = "truncate table employees";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        try {
            String query = "update employees " +
                    "set first_name = ?, " +
                    "last_name = ?, " +
                    "age = ?, " +
                    "email = ? " +
                    " where id = ? ";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setInt(3, employee.getAge());
                preparedStatement.setString(4, employee.getEmail());
                preparedStatement.setLong(5, id);
                int i = preparedStatement.executeUpdate();
                if(i > 0) System.out.println("successfully updated");
                else System.out.println("error");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            String query = "select * from employees";
            try( PreparedStatement preparedStatement = connection.prepareStatement(query)){
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    Employee employee = new Employee();
                    employee.setId(resultSet.getLong("id"));
                    employee.setFirstName(resultSet.getString("first_name"));
                    employee.setLastName(resultSet.getString("last_name"));
                    employee.setAge(resultSet.getInt("age"));
                    employee.setEmail(resultSet.getString("email"));
                    employees.add(employee);
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }
    @Override
    public Employee findByEmail(String email) {
        try {
            String query = "select * from employees where email ilike (?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    Employee employee = new Employee();
                    employee.setId(resultSet.getLong("id"));
                    employee.setFirstName(resultSet.getString("first_name"));
                    employee.setLastName(resultSet.getString("last_name"));
                    employee.setAge(resultSet.getInt("age"));
                    employee.setEmail(resultSet.getString("email"));
                    return employee;
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> map = new HashMap<>();
        try {
            String query = "select * from employees join jobs on employees.id = jobs.employee_id " +
                    " where employees.id = ?; ";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setLong(1, employeeId);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
                        Job job = new Job();
                        job.setId(resultSet.getLong("job_id"));
                        job.setPosition(resultSet.getString("position_job"));
                        job.setProfession(resultSet.getString("profession_job"));
                        job.setDescription(resultSet.getString("description_job"));
                        job.setExperience(resultSet.getInt("experience"));
                        job.setEmployeeId(resultSet.getInt("employee_id"));
                        Employee employee = new Employee();
                        employee.setId(resultSet.getLong("id"));
                        employee.setFirstName(resultSet.getString("first_name"));
                        employee.setLastName(resultSet.getString("last_name"));
                        employee.setAge(resultSet.getInt("age"));
                        employee.setEmail(resultSet.getString("email"));

                        map.put(employee, job);
                    }
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return map;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employees = new ArrayList<>();
        try {
            String query = "select * from employees where id in (select employee_id from jobs" +
                    " where position_job = ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, position);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    Employee employee = new Employee();
                    employee.setId(resultSet.getLong("id"));
                    employee.setFirstName(resultSet.getString("first_name"));
                    employee.setLastName(resultSet.getString("last_name"));
                    employee.setAge(resultSet.getInt("age"));
                    employee.setEmail(resultSet.getString("email"));
                    employees.add(employee);
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }
}
