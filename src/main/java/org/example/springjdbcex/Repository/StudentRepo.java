package org.example.springjdbcex.Repository;

import org.example.springjdbcex.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepo {


   // JdbcTemplate is a class provided by Spring Framework that simplifies the process of working with relational databases using JDBC (Java Database Connectivity).
   // It provides a higher-level abstraction over the standard JDBC API, making it easier to execute SQL queries, manage database connections, and handle exceptions.
    // we no need to create this class manually we can autowire it in our class and use it to perform database operations.
     private JdbcTemplate jdbc;


    public JdbcTemplate getJdbcTemplate() {
        return jdbc;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public List<Student> findAll() {
        List<Student> s=new ArrayList<Student>();
        return s;
    }

    public  void save(Student student) {
       //inserting updating deleting kind of query we use executeUpdate() method
        // for select query we use executeQuery() method
        String sql="insert into student(rollno , name , marks) values(?,?,?)";
         int rows=jdbc.update(sql,student.getRollno(),student.getName(),student.getMarks());
        System.out.println("the affected rows" + rows);
    }
}
