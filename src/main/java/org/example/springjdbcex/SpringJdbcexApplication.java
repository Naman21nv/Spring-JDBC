package org.example.springjdbcex;

import java.util.*;
import org.example.springjdbcex.model.Student;
import org.example.springjdbcex.service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringJdbcexApplication {

    public static void main(String[] args) {
       ApplicationContext context= SpringApplication.run(SpringJdbcexApplication.class, args);
       Student st=context.getBean(Student.class);
       st.setMarks(98);
       st.setName("Karna");
       st.setRollno(1);

        StudentService studentService=context.getBean(StudentService.class);
        studentService.addStudent(st);

        List<Student> students=studentService.getAllStudents();
        System.out.println(students);
    }

}
