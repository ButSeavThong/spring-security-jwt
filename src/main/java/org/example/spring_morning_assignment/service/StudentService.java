package org.example.spring_morning_assignment.service;

import org.example.spring_morning_assignment.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final List<Student> students;

    @Autowired
    public StudentService(List<Student> students) {
        this.students = students;
    }

    public void insertStudent(Student student) {
        students.add(student);
    }

    public List<Student> selectAllStudents() {
        return students;
    }

    public Optional<Student> selectStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst();
    }

    public boolean updateStudentById(int id, Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                students.set(i, updatedStudent);
                return true;
            }
        }
        return false;
    }

    public boolean deleteStudentById(int id) {
        return students.removeIf(student -> student.getId() == id);
    }
}