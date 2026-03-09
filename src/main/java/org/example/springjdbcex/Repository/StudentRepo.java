package org.example.springjdbcex.Repository;

import org.example.springjdbcex.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository for Student entities using Spring's JdbcTemplate.
 *
 * Purpose
 * - Encapsulates persistence operations for the `student` table.
 * - Uses Spring's JdbcTemplate to execute SQL and map database rows to
 *   the `Student` domain object.
 *
 * High-level execution flow (short)
 * 1. Application code calls repository methods (e.g., findAll()).
 * 2. The repository constructs an SQL string and passes it to JdbcTemplate.
 * 3. JdbcTemplate obtains a Connection from the configured DataSource.
 * 4. JdbcTemplate prepares and executes the SQL (PreparedStatement.executeQuery()).
 * 5. The JDBC driver receives rows from the database server and wraps them
 *    in a java.sql.ResultSet object. That ResultSet object is created by the
 *    JDBC driver (you never instantiate it).
 * 6. JdbcTemplate iterates the ResultSet and for each row calls the provided
 *    RowMapper.mapRow(rs, rowNum). The same driver-created ResultSet reference
 *    is passed to your RowMapper for every row; calling rs.getXXX(...) reads
 *    values from the currently positioned row.
 * 7. JdbcTemplate collects mapped objects into a List and, after iteration,
 *    closes ResultSet, Statement, and Connection for you.
 *
 * Why you don't create ResultSet yourself
 * - ResultSet instances are produced by the JDBC driver during executeQuery().
 * - Your code provides SQL and mapping logic; the driver + JdbcTemplate handle
 *   resource management (create/advance/close ResultSet) and call your mapper.
 *
 * Design notes / best practices included below in method-level docs.
 */
@Repository
public class StudentRepo {

    // JdbcTemplate is the central helper - it executes SQL and handles resource
    // management (opening/closing connections/statements/resultsets).
    private JdbcTemplate jdbc;

    /**
     * Exposes the configured JdbcTemplate. Kept for compatibility but may be
     * unused in the codebase; you can remove this accessor if not needed.
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbc;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    /**
     * Retrieve all Student rows from the database.
     *
     * Detailed behavior and flow:
     * - The SQL string below determines which table and columns the database
     *   will return in the ResultSet. Here we use an explicit column list
     *   (SELECT * kept for backward compatibility in this file; prefer explicit
     *   columns in production to avoid ambiguity and map mismatches).
     * - JdbcTemplate.query(sql, mapper) is synchronous: it executes the SQL,
     *   the JDBC driver creates a ResultSet, and JdbcTemplate iterates over the
     *   ResultSet. For each row it invokes mapper.mapRow(rs, rowNum) with the
     *   driver-created ResultSet. The mapper reads column values from the
     *   currently positioned row (rs.getInt("rollno"), etc.). Only after
     *   JdbcTemplate has finished mapping and closed resources does query(...)
     *   return and the surrounding method return to the caller.
     *
     * Important: rs is not created by you; it is created by the JDBC driver
     * when the SQL is executed. Your RowMapper receives that reference and
     * reads values from it. If you need to inspect column labels at runtime
     * for debugging, use rs.getMetaData() inside mapRow.
     *
     * Error and resource handling:
     * - If mapping throws SQLException, JdbcTemplate converts/propagates it as
     *   a DataAccessException (runtime) by default. The JDBC resources are
     *   still properly closed by JdbcTemplate.
     *
     * Mapping contract (what we expect):
     * - SQL must include columns named rollno, name, marks (or alias them to
     *   those labels). If a label doesn't exist a SQLException will be raised
     *   at runtime.
     * - Use rs.wasNull() or getObject(...) if you need to distinguish SQL NULLs
     *   from Java primitive defaults (e.g., getInt returns 0 for NULL).
     *
     * Example debugging snippet (temporary; remove in production):
     *   // List<Student> list = jdbc.query(sql, mapper);
     *   // System.out.println("query returned size=" + list.size());
     *   // return list;
     *
     * Returns: List<Student> mapped from rows of the student table.
     */
    public List<Student> findAll() {
        // SQL controls which table/columns the database returns. Prefer explicit
        // column lists (left here as SELECT * in case your schema has more cols).
        String sql = "SELECT * FROM student";

        // RowMapper implementation as an anonymous inner-class (no lambda).
        // JdbcTemplate will call the mapRow method for each row in the
        // driver-created ResultSet and pass that ResultSet instance as `rs`.
        RowMapper<Student> mapper = new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                // rs is created and managed by the JDBC driver; here we only
                // read values from the CURRENT row (cursor already positioned
                // by JdbcTemplate via rs.next()).
                Student s = new Student();

                // Read by column label. Column labels come from the SELECT
                // clause (or their aliases). If you renamed columns in SQL
                // use matching labels here.
                s.setRollno(rs.getInt("rollno"));
                s.setName(rs.getString("name"));
                s.setMarks(rs.getInt("marks"));

                // If you need to detect SQL NULL for a primitive getter use
                // rs.wasNull() after calling rs.getInt(...).
                // Example:
                // int m = rs.getInt("marks");
                // if (rs.wasNull()) { s.setMarks(null); } // if marks were Integer

                return s;
            }
        };

        // The jdbc.query call executes synchronously. It will not return until
        // the SQL has executed, all rows have been mapped by the mapper, and
        // resources have been closed. The returned List is the final result.
        return jdbc.query(sql, mapper);
    }

    /**
     * Insert a Student row into the database.
     *
     * Notes:
     * - Uses parameter placeholders (?) to avoid SQL injection and to allow
     *   JdbcTemplate/PreparedStatement to bind typed parameters.
     * - jdbc.update executes an update and returns the number of affected rows.
     * - No ResultSet is involved for update operations (executeUpdate returns
     *   an int update count instead of a ResultSet).
     */
    public void save(Student student) {
        // Use PostgreSQL upsert to insert or update on primary-key conflict.
        String sql = "insert into student(rollno, name, marks) values(?,?,?) " +
                     "on conflict (rollno) do update set name = excluded.name, marks = excluded.marks";
        int rows = jdbc.update(sql, student.getRollno(), student.getName(), student.getMarks());
        System.out.println("the affected rows" + rows);
    }
}
