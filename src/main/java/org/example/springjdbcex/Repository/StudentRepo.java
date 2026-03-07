package org.example.springjdbcex.Repository;

import org.example.springjdbcex.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepo {

    public List<Student> findAll() {
        List<Student> s=new ArrayList<Student>();
        return s;
    }

    public  void save(Student student) {
        System.out.println("added now");
    }
}
