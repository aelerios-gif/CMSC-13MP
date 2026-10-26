package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import app.GameFrame;

public class SplashPanel extends JPanel 
{

    private GameFrame frame;
    private Image background;

    public SplashPanel(GameFrame frame) 
    {

        this.frame = frame;

        background = new ImageIcon(
            SplashPanel.class.getResource("/images/SplashScreen.png")
        ).getImage();

        setLayout(new BorderLayout());

        MouseAdapter listener = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) 
            {

                frame.showScreen("HOME");
            }
        };

        addMouseListener(listener);
        //title.addMouseListener(listener);
        //startText.addMouseListener(listener);


        javax.swing.Timer timer = new javax.swing.Timer(5000, e -> {
            frame.showHome();
        });

        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if(background != null)
        {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}