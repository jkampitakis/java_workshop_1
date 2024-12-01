package steps;

import data.Student;
import data.StudentDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class Step3 {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField searchLastNameField; // New text field for search by last name

    private StudentDAO studentDAO = new StudentDAO();

    public Step3() {
        // Create a frame
        frame = new JFrame("Step3");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around the input panel

        inputPanel.add(new JLabel("Search by Last Name:"));
        searchLastNameField = new JTextField();
        inputPanel.add(searchLastNameField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchStudentsByLastName());
        inputPanel.add(searchButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void searchStudentsByLastName() {
        textArea.setText("");
        String lastName = searchLastNameField.getText().trim();

        if (lastName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Last Name field must not be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Student> students = studentDAO.searchStudentsByLastName(lastName);

        if(students.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No students found with that last name.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Student student : students) {
            textArea.append(student.getId() + "\t" +
                    student.getFirstName() + "\t" +
                    student.getLastName() + "\t" +
                    student.getSchool() + "\t" +
                    student.getSemester() + "\t" +
                    student.getPassedCourses() + "\n");
        }
    }

}