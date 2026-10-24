import java.awt.*;
import javax.swing.*;

public class SettingsDialog extends JDialog
{
    private static int soundEffectsVolume = 80;
    private static int gameMusicVolume = 60;

    private JSlider soundEffectsSlider;
    private JSlider gameMusicSlider;
    private JCheckBox fullscreenCheckBox;

    public SettingsDialog(GameFrame frame)
    {
        super(frame, "Settings", true);

        setUndecorated(true);
        setSize(450,350);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout(15, 15));

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("SETTINGS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(25));

        //Sound effects volume
        JLabel effectsLabel = new JLabel("SoundEffectsVolume");
        soundEffectsSlider = new JSlider(0, 100, soundEffectsVolume);
        soundEffectsSlider.setMajorTickSpacing(25);
        soundEffectsSlider.setPaintTicks(true);
        soundEffectsSlider.setPaintLabels(true);

        contentPanel.add(effectsLabel);
        contentPanel.add(soundEffectsSlider);
        contentPanel.add(Box.createVerticalStrut(15));

        //Background music volume
        JLabel musicLabel = new JLabel("Game Music Volume");
        gameMusicSlider = new JSlider(0, 100, gameMusicVolume);
        gameMusicSlider.setMajorTickSpacing(25);
        gameMusicSlider.setPaintTicks(true);
        gameMusicSlider.setPaintLabels(true);

        contentPanel.add(musicLabel);
        contentPanel.add(gameMusicSlider);
        contentPanel.add(Box.createVerticalStrut(20));

        //Fullscreen setting
        fullscreenCheckBox = new JCheckBox("Fullscreen", frame.isFullscreen());

        fullscreenCheckBox.addActionListener(e -> {
            frame.setFullscreen(fullscreenCheckBox.isSelected());
        });

        contentPanel.add(fullscreenCheckBox);


        //close button
        JButton closeButton = new JButton("CLOSE");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        closeButton.addActionListener(e -> dispose());

        add(contentPanel, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        //Save the selected volume levels
        soundEffectsSlider.addChangeListener(e -> {
            soundEffectsVolume = soundEffectsSlider.getValue();
        });

        gameMusicSlider.addChangeListener(e -> {
            gameMusicVolume = gameMusicSlider.getValue();
        });
    }

    

    public static int getSoundEffectsVolume()
    {
        return soundEffectsVolume;
    }

    public static int getGameMusicVolume()
    {
        return gameMusicVolume;
    }
}