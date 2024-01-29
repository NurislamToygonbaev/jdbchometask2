package dao.impl;

import configuration.JdbcConfig;
import dao.JobDao;
import model.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    private final Connection connection = JdbcConfig.getConnection();
    @Override
    public void createJobTable() {
        try {
            String query = """
                    create table if not exists jobs(
                    job_id serial primary key,
                    position_job varchar,
                    profession_job varchar,
                    description_job varchar,
                    experience int,
                    employee_id int references employees(id)
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
    public void addJob(Job job) {
        try {
            String query = """
                    insert into jobs(position_job, profession_job, description_job, experience, employee_id)
                    values (?, ?, ?, ?, ?);
                    """;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, job.getPosition());
                preparedStatement.setString(2, job.getProfession());
                preparedStatement.setString(3, job.getDescription());
                preparedStatement.setInt(4, job.getExperience());
                preparedStatement.setInt(5, job.getEmployeeId());
                int i = preparedStatement.executeUpdate();
                if(i > 0) System.out.println("successfully saved");
                else System.out.println("error");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Job getJobById(Long jobId) {
        try {
            String query = "select * from jobs where job_id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setLong(1, jobId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    Job job = new Job();
                    job.setId(resultSet.getLong("job_id"));
                    job.setPosition(resultSet.getString("position_job"));
                    job.setProfession(resultSet.getString("profession_job"));
                    job.setDescription(resultSet.getString("description_job"));
                    job.setExperience(resultSet.getInt("experience"));
                    job.setEmployeeId(resultSet.getInt("employee_id"));
                    return job;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();
        if (ascOrDesc.equalsIgnoreCase("asc")) {
            try {
                String queryAsc = "select * from jobs order by experience";
                try (PreparedStatement preparedStatement = connection.prepareStatement(queryAsc)) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Job job = new Job();
                        job.setId(resultSet.getLong("job_id"));
                        job.setPosition(resultSet.getString("position_job"));
                        job.setProfession(resultSet.getString("profession_job"));
                        job.setDescription(resultSet.getString("description_job"));
                        job.setExperience(resultSet.getInt("experience"));
                        job.setEmployeeId(resultSet.getInt("employee_id"));
                        jobs.add(job);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            try {
                String queryDesc = "select * from jobs order by experience desc";
                try (PreparedStatement preparedStatement = connection.prepareStatement(queryDesc)) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Job job = new Job();
                        job.setId(resultSet.getLong("job_id"));
                        job.setPosition(resultSet.getString("position_job"));
                        job.setProfession(resultSet.getString("profession_job"));
                        job.setDescription(resultSet.getString("description_job"));
                        job.setExperience(resultSet.getInt("experience"));
                        job.setEmployeeId(resultSet.getInt("employee_id"));
                        jobs.add(job);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else System.out.println("incorrect choice");
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        Job job = new Job();
        try {
            String query = "select * from jobs where employee_id in (select id from employees" +
                    " where id = ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setLong(1, employeeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        job.setId(resultSet.getLong("job_id"));
                        job.setPosition(resultSet.getString("position_job"));
                        job.setProfession(resultSet.getString("profession_job"));
                        job.setDescription(resultSet.getString("description_job"));
                        job.setExperience(resultSet.getInt("experience"));
                        job.setEmployeeId(resultSet.getInt("employee_id"));
                    }
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return job;
    }

    @Override
    public void deleteDescriptionColumn() {
        try {
            String query = "alter table jobs drop column description_job";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                int i = preparedStatement.executeUpdate();
                if (i > 0) System.out.println("success dropped column");
                else System.out.println("error");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
