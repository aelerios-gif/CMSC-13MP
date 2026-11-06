package ui;

import app.GameFrame;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import util.GameFonts;
import audio.AudioLoader;

public class HomePanel extends JPanel 
{

    private GameFrame frame;

    private Image backgroundImage;

    public HomePanel(GameFrame frame) 
    {

        this.frame = frame;
        audio.AudioLoader.playMainGameBG();
        backgroundImage = new ImageIcon("resources/images/HomeScreen.png").getImage();

        setLayout(new BorderLayout());

        //Buttons panel
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        //Make the button panel transparent
        buttonPanel.setOpaque(false);

        //add space around the buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 20));

        //PLAY button
        GameButton playButton = new GameButton("PLAY");

        //TUTORIAL button
        GameButton tutorialButton = new GameButton("TUTORIAL");

        //SETTINGS button
        GameButton settingsButton = new GameButton("SETTINGS");

        //EXIT BUtton
        GameButton exitButton = new GameButton("EXIT");

        //Arrange all four buttons
        GameButton[] buttons = {
            playButton,
            tutorialButton,
            settingsButton,
            exitButton
        };

        for (GameButton button : buttons) 
        {
            //button dimensions
            button.setPreferredSize(new Dimension(250, 55));
            button.setMinimumSize(new Dimension(250, 55));
            button.setMaximumSize(new Dimension(250, 55));

            button.setAlignmentX(Component.LEFT_ALIGNMENT);

            //Add button to panel
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(15));
        }

        //Place the buttons on the center left side
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1.0;

        leftPanel.add(buttonPanel, gbc);

        add(leftPanel, BorderLayout.WEST);

        //button actions
        playButton.addActionListener(e -> {
            frame.startGame();
        });

        tutorialButton.addActionListener(e -> {
            frame.showScreen("TUTORIAL");
        });

        settingsButton.addActionListener(e -> {
            SettingsDialog settings = new SettingsDialog(frame);
            settings.setVisible(true);
        });

        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit the game?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(
                    backgroundImage,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );
        }
    }
}
