package ui;

import java.awt.*;
import javax.swing.*;

import app.GameFrame;

public class TutorialPanel extends JPanel {

    private GameFrame frame;
    private Image tutorialImage; 
        public TutorialPanel(GameFrame frame){
            this.frame=frame;
            tutorialImage=new ImageIcon("resources/images/TutorialScreen.png").getImage();
            setLayout(new BorderLayout());
        }


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