package util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel
{
    private Image backgroundImage;

    public BackgroundPanel(String imagePath)
    {
        backgroundImage = new ImageIcon(imagePath).getImage();
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        g2d.drawImage(
            backgroundImage,
            0,
            0,
            getWidth(),
            getHeight(),
            this
        );

        g2d.dispose();
    }
}