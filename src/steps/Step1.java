package steps;

import net.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Step1 {
    private JFrame frame;
    private JLabel connLbl;
    private JButton connectButton;

    public Step1() {
        // Create a frame
        frame = new JFrame("Step1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Create a label
        connLbl = new JLabel("Status", SwingConstants.CENTER);
        frame.add(connLbl, BorderLayout.NORTH);

        //add a button that calls connect
        connectButton = new JButton("Connect!");
        connectButton.addActionListener(e -> connect());
        frame.add(connectButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    //connect to database
    private void connect() {
        System.out.println("Connecting to database...");
        try {
            DatabaseConnection.getConnection();
            System.out.println("Connected to database!");
            connLbl.setText("Connected!");
            connectButton.setEnabled(false);
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            connLbl.setText("Failed...");
            e.printStackTrace();
        }
    }
}
