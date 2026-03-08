package org.example.springjdbcex.service;


import org.example.springjdbcex.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.springjdbcex.model.Student;

import java.util.List;


@Service
public class StudentService {
      private StudentRepo studentRepo;


    public StudentRepo getStudentRepo() {
        return studentRepo;
    }

    @Autowired
    public void setStudentRepo(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    /**
     * Service method to add a student.
     *
     * Why keep a service layer?
     * - Centralizes business rules (validation, transformations) away from controllers and repositories.
     * - Makes it easy to apply transactional boundaries using @Transactional when multiple repository calls
     *   must succeed or fail together.
     * - Simplifies testing: unit test this service by mocking the StudentRepo.
     */
    public void addStudent(Student student) {
      studentRepo.save(student);
  }

    /**
     * Retrieve all students.
     *
     * As the app grows you might add caching, pagination, or filtering here. Keep repository methods focused
     * on data access (SQL); keep service methods focused on business rules.
     */
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
}
