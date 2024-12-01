package data;

import net.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void addStudent(String firstName, String lastName, String school, int semester, int passedCourses) {
        String sql = "INSERT INTO students (first_name, last_name, school, semester, passed_courses) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, school);
            pstmt.setInt(4, semester);
            pstmt.setInt(5, passedCourses);
            pstmt.executeUpdate();
            System.out.println("data.Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> fetchAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("school"),
                        rs.getInt("semester"),
                        rs.getInt("passed_courses")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void updatePassedCourses(String lastName, int passedCourses) {
        String sql = "UPDATE students SET passed_courses = ? WHERE last_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, passedCourses);
            pstmt.setString(2, lastName);
            pstmt.executeUpdate();
            System.out.println("data.Student updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(String lastName) {
        String sql = "DELETE FROM students WHERE last_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lastName);
            pstmt.executeUpdate();
            System.out.println("data.Student deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllStudents() {
        String sql = "DELETE FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All students deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> searchStudentsByLastName(String lastName) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE last_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lastName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("school"),
                            rs.getInt("semester"),
                            rs.getInt("passed_courses")
                    );
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}