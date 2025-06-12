package org.example.spring_morning_assignment.dataContainer;

import org.example.spring_morning_assignment.model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.List;

@Configuration
public class InMemoryDatabase {

    @Bean
    public List<Student> studentList() {
        return Arrays.asList(
                new Student(1, "John Doe", "Male", 85.5),
                new Student(2, "Jane Smith", "Female", 92.0),
                new Student(3, "Michael Johnson", "Male", 78.3),
                new Student(4, "Emily Davis", "Female", 88.9),
                new Student(5, "Robert Wilson", "Male", 76.2),
                new Student(6, "Sarah Brown", "Female", 95.1),
                new Student(7, "David Taylor", "Male", 82.7),
                new Student(8, "Jessica Miller", "Female", 90.4),
                new Student(9, "William Anderson", "Male", 79.8),
                new Student(10, "Olivia Thomas", "Female", 87.6)
        );
    }
}