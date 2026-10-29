package ui;

import app.GameFrame;
import java.awt.*;
import javax.swing.*;
import util.GameFonts;

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
        backButton.setFont(GameFonts.button());
        backButton.addActionListener(e -> this.frame.showScreen("HOME"));
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(tutorialImage, 0, 0, getWidth(), getHeight(), this);
    }
}
