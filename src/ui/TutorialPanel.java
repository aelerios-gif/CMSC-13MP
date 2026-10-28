package ui;

import java.awt.*;
import javax.swing.*;

import app.GameFrame;

public class TutorialPanel extends JPanel {

    private final GameFrame frame;
    private final Image tutorialImage;

    public TutorialPanel(GameFrame frame) {
        this.frame = frame;

        java.net.URL imageUrl = TutorialPanel.class.getResource("/images/TutorialScreen.png");
        if (imageUrl == null) {
            throw new IllegalStateException("Missing tutorial image resource: /images/TutorialScreen.png");
        }
        tutorialImage = new ImageIcon(imageUrl).getImage();

        setLayout(new BorderLayout());

        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> this.frame.showScreen("HOME"));
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(tutorialImage, 0, 0, getWidth(), getHeight(), this);
    }
}