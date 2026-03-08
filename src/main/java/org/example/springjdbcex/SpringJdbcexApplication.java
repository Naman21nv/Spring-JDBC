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
       // Obtain a Student bean from the context. The Student is marked @Scope("prototype")
       // so each request for the bean returns a new instance. This is convenient for examples
       // where you want to quickly create domain objects from the container.
       Student st=context.getBean(Student.class);
       st.setMarks(98);
       st.setName("Karna");
       st.setRollno(1);

        // Retrieve the service bean and use it to persist the student.
        // In a real app a controller or REST endpoint would call the service; the main method here
        // demonstrates the flow in a simple, runnable way.
        StudentService studentService=context.getBean(StudentService.class);
        studentService.addStudent(st);

        // Fetch all students and print them to stdout. This is just a quick smoke-check to show
        // that the repository/service flow works. For robust checks, write unit/integration tests instead.
        List<Student> students=studentService.getAllStudents();
        System.out.println(students);
    }


}
