package ui;

import java.awt.*;
import javax.swing.*;

import app.GameFrame;

public class TutorialPanel extends JPanel {

    private GameFrame frame;

    public TutorialPanel(GameFrame frame) {

        this.frame = frame;

        setLayout(new BorderLayout());

        //JLabel title = new JLabel("HOW TO PLAY");

        //title.setFont(new Font("Arial", Font.BOLD, 35));

        //title.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea instructions = new JTextArea();

        instructions.setText(
            "Welcome to Are You Smarter Than a 5th Grader!\n\n" +

            "Answer 70 multiple-choice questions.\n\n" +

            "Each question has four possible answers.\n\n" +

            "Choose the answer you think is correct.\n\n" +

            "You also have a Call a Friend lifeline.\n\n" +

            "You can call one of six different friends " +
            "for help.\n\n" +

            "Try to get the highest score possible!"
        );

        instructions.setFont(
            new Font("Arial", Font.PLAIN, 20)
        );

        instructions.setEditable(false);

        instructions.setLineWrap(true);

        instructions.setWrapStyleWord(true);

        instructions.setMargin(
            new Insets(30, 50, 30, 50)
        );

        JButton backButton = new JButton("BACK");

        backButton.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        backButton.addActionListener(e -> {

            frame.showScreen("HOME");

        });

        //add(title, BorderLayout.NORTH);
        add(instructions, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}