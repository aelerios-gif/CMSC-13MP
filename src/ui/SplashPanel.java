package ui;

import app.GameFrame;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import audio.AudioLoader;

public class SplashPanel extends JPanel
{
    private GameFrame frame;

    private Image[] splashImages;
    private int currentImage = 0;

    private float opacity = 1.0f;

    private boolean transitioning = false;

    private Timer displayTimer;
    private Timer fadeTimer;

    public SplashPanel(GameFrame frame)
    {
        this.frame = frame;
        audio.AudioLoader.playMainGameBG();
        //load all 7 splash screen images
        splashImages = new Image[7];

        for(int i = 0; i < splashImages.length; i++)
        {
            String imagePath = "/images/SplashScreen/" + (i + 1) + ".png";

            java.net.URL imageURL = SplashPanel.class.getResource(imagePath);

            if(imageURL == null)
            {
                throw new IllegalStateException("Missing splash image: " + imagePath);
            }

            splashImages[i] = new ImageIcon(imageURL).getImage();
        }

        setLayout(new BorderLayout());

        setBackground(new Color(7, 19, 11));

        //click anywhere to proceed
        addMouseListener(new MouseAdapter() {
           
            @Override 
            public void mouseClicked(MouseEvent e)
            {
                startFadeOut();
            }
        });

        //automatically proceed after 5 seconds
        displayTimer = new Timer(
            5000,
            e -> startFadeOut()
        );

        displayTimer.setRepeats(false);
        displayTimer.start();
    }

    //fade out current image
    private void startFadeOut()
    {
        if(transitioning)
        {
            return;
        }

        transitioning = true;

        displayTimer.stop();

        fadeTimer = new Timer(16, e ->
        {
            opacity -= 0.05f;

            if(opacity <= 0.0f)
            {
                opacity = 0.0f;

                fadeTimer.stop();

                //check if this is the last image
                if(currentImage == splashImages.length - 1)
                {
                    frame.showScreen("HOME");
                    return;
                }


                //proceed to the next image
                currentImage++;

                startFadeIn();
            }

            repaint();
        });

        fadeTimer.start();
    }

    //fade in next image
    private void startFadeIn()
    {
        fadeTimer = new Timer(16, e ->
        {
            opacity += 0.05f;

            if(opacity >= 1.0f)
            {
                opacity = 1.0f;

                fadeTimer.stop();

                transitioning = false;

                //start another 5 second countdown
                displayTimer.restart();
            }

            repaint();
        });

        fadeTimer.start();
    }

    //draw splash image

    @Override 
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        //draw dark background
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //apply image opacity
        g2d.setComposite(
            AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                opacity
            )
        );

        //display current image
        g2d.drawImage(
            splashImages[currentImage],
            0,
            0,
            getWidth(),
            getHeight(),
            this
        );

        g2d.dispose();
    }
}