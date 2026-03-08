package org.example.springjdbcex.Repository;

import org.example.springjdbcex.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for all database interactions related to Student.
 *
 * Key responsibilities:
 * - Execute SQL queries and updates using JdbcTemplate.
 * - Map ResultSet rows to `Student` instances.
 * - Keep SQL close to the code that uses it.
 *
 * Note: This class intentionally keeps logic simple for educational purposes. In bigger
 * projects you might externalize SQL, use NamedParameterJdbcTemplate, or move mapping
 * to dedicated mappers.
 */
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

    /**
     * Retrieve all students from the database.
     *
     * Current implementation returns an empty list; replace with jdbc.query and a RowMapper
     * when you want real data. Example (commented):
     *
     * // String sql = "select rollno, name, marks from Student";
     * // List<Student> students = jdbc.query(sql, (rs, rowNum) -> {
     * //     Student s = new Student();
     * //     s.setRollno(rs.getInt("rollno"));
     * //     s.setName(rs.getString("name"));
     * //     s.setMarks(rs.getInt("marks"));
     * //     return s;
     * // });
     *
     * Using a RowMapper lambda keeps mapping concise and readable.
     */
    public List<Student> findAll() {
        List<Student> s=new ArrayList<Student>();
        return s;
    }

    /**
     * Save a student to the database.
     *
     * This method demonstrates using JdbcTemplate.update() for INSERT statements.
     * - Use `update` for INSERT/UPDATE/DELETE statements.
     * - For INSERTs that generate keys, use a KeyHolder with jdbc.update(PreparedStatementCreator, KeyHolder).
     *
     * Note: The SQL uses `?` placeholders to avoid SQL injection - JdbcTemplate will bind the parameters safely.
     */
    public  void save(Student student) {
       //inserting updating deleting kind of query we use executeUpdate() method
        // for select query we use executeQuery() method
        String sql="insert into student(rollno , name , marks) values(?,?,?)";
         int rows=jdbc.update(sql,student.getRollno(),student.getName(),student.getMarks());
        System.out.println("the affected rows" + rows);
    }
}
