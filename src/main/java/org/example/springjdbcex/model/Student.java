package org.example.springjdbcex.model;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Student is a simple domain model (POJO) that represents a row in the `Student` table.
 *
 * Why a POJO here?
 * - This project uses Spring's JdbcTemplate (not JPA), so we keep the model as a plain Java object.
 * - The repository layer is responsible for mapping SQL ResultSet rows to instances of this class.
 * - Keeping the model simple improves testability and separation of concerns.
 */
@Component
@Scope("prototype")
public class Student {
    // The primary key / business identifier for a student.
    // Matches the `rollno` column in the database schema (see Schema.sql).
    private int rollno;

    // Student's name. Kept as String and mapped from a VARCHAR column in the DB.
    private String name;

    // Numeric marks for the student. Use an int when values are small and non-null.
    private int marks;

    // toString() is useful for quick debugging and logging. It should not be used as a serialization format.
    @Override
    public String toString() {
        return "Student{" +
                "rollno=" + rollno +
                ", name='" + name + '\'' +
                ", marks=" + marks +
                '}';
    }

    // --- Getters and setters ---
    // These are simple accessors used by the repository when constructing instances
    // and by other layers when reading or modifying the model.

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    /**
     * save() is intentionally left empty in this example.
     *
     * Why not put persistence logic here?
     * - We follow separation of concerns: the repository layer (`StudentRepo`) handles database access.
     * - Keeping persistence out of the model avoids tight coupling and makes unit testing easier.
     *
     * If you need convenience helpers for tests (for example, to create prefilled instances),
     * add factory or builder methods instead of embedding JDBC code here.
     */
    public void save() {

    }
}
