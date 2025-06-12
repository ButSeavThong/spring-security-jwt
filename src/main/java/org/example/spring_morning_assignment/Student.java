package org.example.spring_morning_assignment;

public class Student {
    private int id;
    private String fullName;
    private String gender;
    private double score;

    public Student(int id, String fullName, String gender, double score) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.score = score;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", score=" + score +
                '}';
    }
}