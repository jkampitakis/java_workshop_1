package steps;

import data.Student;
import data.StudentDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class Step2 {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField firstNameField, lastNameField, schoolField, semesterField, passedCoursesField;

    private StudentDAO studentDAO = new StudentDAO();

    public Step2() {
        // Create a frame
        frame = new JFrame("Step2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around the input panel
        inputPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        inputPanel.add(firstNameField);

        inputPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);

        inputPanel.add(new JLabel("School:"));
        schoolField = new JTextField();
        inputPanel.add(schoolField);

        inputPanel.add(new JLabel("Semester:"));
        semesterField = new JTextField();
        inputPanel.add(semesterField);

        inputPanel.add(new JLabel("Passed Courses:"));
        passedCoursesField = new JTextField();
        inputPanel.add(passedCoursesField);

        JButton fetchButton = new JButton("Fetch All Students");
        fetchButton.addActionListener(e -> fetchAllStudents());
        inputPanel.add(fetchButton);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> addStudent());
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void fetchAllStudents() {
        textArea.setText("");
        List<Student> students = studentDAO.fetchAllStudents();
        for (Student student : students) {
            textArea.append(student.getId() + "\t" +
                    student.getFirstName() + "\t" +
                    student.getLastName() + "\t" +
                    student.getSchool() + "\t" +
                    student.getSemester() + "\t" +
                    student.getPassedCourses() + "\n");
        }
    }

    private void addStudent() {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String school = schoolField.getText().trim();
            String semesterText = semesterField.getText().trim();
            String passedCoursesText = passedCoursesField.getText().trim();

            // Validation to ensure fields are not empty and within length constraints
            if (firstName.isEmpty() || firstName.length() > 50) {
                JOptionPane.showMessageDialog(frame, "First Name must not be empty and must be less than 50 characters.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (lastName.isEmpty() || lastName.length() > 100) {
                JOptionPane.showMessageDialog(frame, "Last Name must not be empty and must be less than 100 characters.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (school.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "School must not be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (school.length() > 100) {
                JOptionPane.showMessageDialog(frame, "School must be less than 100 characters.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (semesterText.isEmpty() || passedCoursesText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Semester and Passed Courses fields must not be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int semester = Integer.parseInt(semesterText);
            int passedCourses = Integer.parseInt(passedCoursesText);

            studentDAO.addStudent(
                    firstName,
                    lastName,
                    school,
                    semester,
                    passedCourses
            );

            fetchAllStudents();
            JOptionPane.showMessageDialog(frame, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            //clear all fields
            firstNameField.setText("");
            lastNameField.setText("");
            schoolField.setText("");
            semesterField.setText("");
            passedCoursesField.setText("");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numbers for Semester and Passed Courses.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}