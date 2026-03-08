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

    public void addStudent(Student student) {
      studentRepo.save(student);
  }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
}
