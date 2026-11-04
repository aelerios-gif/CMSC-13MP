package ui;

import app.GameFrame;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class SplashPanel extends JPanel
{
    private GameFrame frame;
    private Image background;

    private float opacity = 1.0f;
    private boolean fadingOut = false;

    private Timer splashTimer;
    private Timer fadeTimer;

    public SplashPanel(GameFrame frame)
    {
        this.frame = frame;

        background = new ImageIcon(
            SplashPanel.class.getResource("/images/SplashScreen.png")
        ).getImage();

        setLayout(new BorderLayout());

        // Background color visible as the image fades
        setBackground(new Color(7, 19, 11));

        // Click anywhere to start the fade-out
        MouseAdapter listener = new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                startFadeOut();
            }
        };

        addMouseListener(listener);

        // Automatically start fading after 5 seconds
        splashTimer = new Timer(5000, e -> startFadeOut());

        splashTimer.setRepeats(false);
        splashTimer.start();
    }

    private void startFadeOut()
    {
        // Prevent multiple fade animations
        if (fadingOut)
        {
            return;
        }

        fadingOut = true;

        // Stop the automatic timer if the player clicked
        splashTimer.stop();

        // Gradually decrease image opacity
        fadeTimer = new Timer(16, e ->
        {
            opacity -= 0.02f;

            if (opacity <= 0.0f)
            {
                opacity = 0.0f;

                fadeTimer.stop();

                // Show the Home Screen after fading
                frame.showScreen("HOME");
            }

            repaint();
        });

        fadeTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (background != null)
        {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setComposite(
                AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER,
                    opacity
                )
            );

            g2d.drawImage(
                background,
                0, 0,
                getWidth(),
                getHeight(),
                this
            );

            g2d.dispose();
        }
    }
}