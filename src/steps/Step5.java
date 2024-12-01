package steps;

import data.Student;
import data.StudentDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class Step5 {

    private JFrame frame;
    private JTextArea textArea;
    private JTextField lastNameField;
    private JTextField passedCoursesField;

    private StudentDAO studentDAO = new StudentDAO();

    public Step5(){
        frame =new JFrame("Αναζητηση βαση εξαμηνου");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel inputPanel =  new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(new EmptyBorder(10,10,10,10));

        inputPanel.add(new JLabel("Επώνυμο:"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);

        inputPanel.add(new JLabel("Νέος αριθμός περασμένων μαθημάτων:"));
        passedCoursesField = new JTextField();
        inputPanel.add(passedCoursesField);

        JButton updateButton = new JButton("Ενημέρωση");
        updateButton.addActionListener(e -> updatePassedCourses());
        inputPanel.add(updateButton);


        frame.add(inputPanel,BorderLayout.NORTH);
        frame.add(scrollPane,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void updatePassedCourses(){
        String lastName = lastNameField.getText().trim();
        String passedCoursesText = passedCoursesField.getText().trim();

        //validation
        if(lastName.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Παρακαλώ εισάγετε επώνυμο", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            int passedCourses = Integer.parseInt(passedCoursesText);
            if(passedCourses < 0){
                throw new NumberFormatException();
            }

            List<Student> students = studentDAO.searchStudentsByLastName(lastName);
            if(students.isEmpty()){
                textArea.setText("Δεν βρέθηκαν φοιτητές με επώνυμο " + lastName + ".");
                return;
            }

            //if multiple students found, ask user to select one
            Student selectedStudent = null;
            if(students.size() > 1){
                selectedStudent = selectStudent(students);
            }else{
                selectedStudent = students.get(0);
            }

            if(selectedStudent != null){
                //update the student's passed courses
                boolean success = studentDAO.updateStudentPassedCourses(selectedStudent.getId(), passedCourses);

                if(success){
                    textArea.setText("Επιτυχής ενημέρωση για τον φοιτητή:\n" +
                            selectedStudent.getFirstName() + " " +
                            selectedStudent.getLastName() + "\n" +
                            "Νέος αριθμός περασμένων μαθημάτων: " + passedCourses
                    );
                }else{
                    textArea.setText("Σφάλμα κατά την ενημέρωση.");
                }
            }
        }catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(frame, "Παρακαλώ εισάγετε έναν έγκυρο αριθμό μαθημάτων", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Student selectStudent(List<Student> students){
        String[] options = new String[students.size()];
        for(int i = 0; i < students.size(); i++){
            Student s = students.get(i);
            options[i] = String.format("%s %s (%s, Εξάμηνο: %d",
                        s.getFirstName(),
                        s.getLastName(),
                        s.getSchool(),
                        s.getSemester()
                    );
        }

        String selected = (String) JOptionPane.showInputDialog(frame,
                    "Βρέθηκαν πολλοί φοιτητές. Παρακαλώ επιλέξτε έναν:",
                    "Επιλογή Φοιτητή",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
                );

        if(selected != null){
            int index = -1;
            for(int i=0; i <options.length; i++){
                if(options[i].equals(selected)){
                    index = i;
                    break;
                }
            }
            return index != -1 ? students.get(index) : null;
        }

        return null;
    }


}
