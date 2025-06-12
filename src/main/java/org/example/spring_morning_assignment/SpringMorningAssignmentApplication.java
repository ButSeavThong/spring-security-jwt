package org.example.spring_morning_assignment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Optional;

@SpringBootApplication
public class SpringMorningAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMorningAssignmentApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(StudentService studentService) {
        return (args) -> {
            System.out.println("=== Testing StudentService ===");

            // Test select all
            System.out.println("\nAll Students:");
            studentService.selectAllStudents().forEach(System.out::println);

            // Test select by ID
            System.out.println("\nStudent with ID 3:");
            Optional<Student> student = studentService.selectStudentById(3);
            student.ifPresent(System.out::println);

            // Test insert
            System.out.println("\nInserting new student...");
            Student newStudent = new Student(11, "New Student", "Male", 88.8);
            studentService.insertStudent(newStudent);
            System.out.println("After insertion:");
            studentService.selectAllStudents().forEach(System.out::println);

            // Test update
            System.out.println("\nUpdating student with ID 5...");
            Student updatedStudent = new Student(5, "Updated Name", "Female", 99.9);
            boolean updateResult = studentService.updateStudentById(5, updatedStudent);
            System.out.println("Update successful? " + updateResult);
            System.out.println("After update:");
            studentService.selectAllStudents().forEach(System.out::println);

            // Test delete
            System.out.println("\nDeleting student with ID 2...");
            boolean deleteResult = studentService.deleteStudentById(2);
            System.out.println("Delete successful? " + deleteResult);
            System.out.println("After deletion:");
            studentService.selectAllStudents().forEach(System.out::println);
        };
    }
}