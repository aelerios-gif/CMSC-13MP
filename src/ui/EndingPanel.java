package ui;

import app.GameFrame;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import util.GameFonts;

public class EndingPanel extends JPanel
{
    private final GameFrame frame;

    private Image endingImage;

    private float opacity = 0.0f;

    private boolean readyToContinue = false;

    private Timer delayTimer;
    private Timer fadeTimer;

    public EndingPanel(GameFrame frame)
    {
        this.frame = frame;

        setBackground(Color.BLACK);

        // Clicking anywhere returns to the home screen.
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(readyToContinue)
                {
                    frame.returnToHome();
                }
            }
        });
    }

    public void showEnding(boolean won)
    {
        // Stop previous animations, if any.
        if(delayTimer != null)
        {
            delayTimer.stop();
        }

        if(fadeTimer != null)
        {
            fadeTimer.stop();
        }

        String imagePath = won
            ? "/images/Win.png"
            : "/images/Lose.png";

        java.net.URL imageURL =
            EndingPanel.class.getResource(imagePath);

        if(imageURL == null)
        {
            throw new IllegalStateException(
                "Missing ending image: " + imagePath
            );
        }

        endingImage = new ImageIcon(imageURL).getImage();

        // Start with a completely dark screen.
        opacity = 0.0f;
        readyToContinue = false;

        repaint();

        // Wait 3 seconds before fading in the image.
        delayTimer = new Timer(3000, e ->
        {
            startFadeIn();
        });

        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    private void startFadeIn()
    {
        fadeTimer = new Timer(16, e ->
        {
            opacity += 0.025f;

            if(opacity >= 1.0f)
            {
                opacity = 1.0f;

                fadeTimer.stop();

                readyToContinue = true;
            }

            repaint();
        });

        fadeTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Dark background.
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the ending image with transparency.
        if(endingImage != null)
        {
            g2d.setComposite(
                AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER,
                    opacity
                )
            );

            g2d.drawImage(
                endingImage,
                0,
                0,
                getWidth(),
                getHeight(),
                this
            );
        }

        // Show the instruction only after the fade finishes.
        if(readyToContinue)
        {
            g2d.setComposite(AlphaComposite.SrcOver);

            String message = "CLICK ANYWHERE TO CONTINUE";

            g2d.setFont(GameFonts.button());
            g2d.setColor(GameFonts.MINT_GREEN);

            FontMetrics metrics = g2d.getFontMetrics();

            int x = (getWidth() - metrics.stringWidth(message)) / 2;
            int y = getHeight() - 35;

            g2d.drawString(message, x, y);
        }

        g2d.dispose();
    }
}
