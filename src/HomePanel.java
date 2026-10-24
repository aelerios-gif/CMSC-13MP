import java.awt.*;
import javax.swing.*;

public class HomePanel extends JPanel 
{

    private GameFrame frame;

    public HomePanel(GameFrame frame) 
    {

        this.frame = frame;

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;


        // Play
        JButton playButton = new JButton("PLAY");

        playButton.setPreferredSize(new Dimension(250, 55));

        playButton.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridy = 1;

        add(playButton, gbc);


        // Tutorial
        JButton tutorialButton = new JButton("TUTORIAL");

        tutorialButton.setPreferredSize(new Dimension(250, 55));

        tutorialButton.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridy = 2;

        add(tutorialButton, gbc);



        //Settings
        JButton settingsButton = new JButton("SETTINGS");

        settingsButton.setPreferredSize(new Dimension(250, 55));

        settingsButton.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridy = 3;

        add(settingsButton, gbc);



        // Exit
        JButton exitButton = new JButton("EXIT");

        exitButton.setPreferredSize(new Dimension(250, 55));

        exitButton.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridy = 4;

        add(exitButton, gbc);

        // Button actions
        //playButton
        playButton.addActionListener(e -> {

            frame.startGame();

        });

        //tutorialButton
        tutorialButton.addActionListener(e -> {

            frame.showScreen("TUTORIAL");

        });

        //settingsButton
        settingsButton.addActionListener(e -> {
            SettingsDialog settings = new SettingsDialog(frame);
            settings.setVisible(true);
        });

        //exitButton
        exitButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit the game?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if(choice == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });
    }
}