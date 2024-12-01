package steps;

import data.Student;
import data.StudentDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class Step4 {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField semesterField;
    private StudentDAO studentDAO = new StudentDAO();

    public Step4(){
        frame =new JFrame("Αναζητηση βαση εξαμηνου");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel inputPanel =  new JPanel(new GridLayout(2, 2));
        inputPanel.setBorder(new EmptyBorder(10,10,10,10));

        inputPanel.add(new JLabel("Εξάμηνο:"));
        semesterField = new JTextField();
        inputPanel.add(semesterField);

        JButton searchButton = new JButton("Αναζήτηση");
        searchButton.addActionListener(e -> searchBySemester());
        inputPanel.add(searchButton);

        frame.add(inputPanel,BorderLayout.NORTH);
        frame.add(scrollPane,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void searchBySemester(){
        textArea.setText("");
        String semesterText = semesterField.getText().trim();

        if(semesterText.isEmpty()){
            JOptionPane.showMessageDialog(frame, "το πεδίο του εξαμήνου δεν μπορεί να είναι κενό", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            int semester = Integer.parseInt(semesterText);
            List<Student> students = studentDAO.searchStudentsBySemester(semester);

            if(students.isEmpty()){
                textArea.setText("Δεν βρέθηκαν φοιτητές στο " + semester + "o εξάμηνο.");
                return;
            }

            for(Student student : students){
                textArea.append(student.getId() + "\t" +
                        student.getFirstName() + "\t" +
                        student.getLastName() + "\t" +
                        student.getSchool() + "\t" +
                        student.getSemester() + "\t" +
                        student.getPassedCourses() + "\n");
            }
        }catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(frame, "παρακαλώ εισάγετε έναν έγκυρο αριθμό εξαμήνου", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }
}
