package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import util.GameFonts;
import audio.AudioLoader;

public class GameButton extends JButton
{
    private int cornerRadius = 15;

    public GameButton(String text)
    {
        super(text);

        setFont(GameFonts.button());
        setForeground(GameFonts.MINT_GREEN);

        setPreferredSize(new Dimension(250, 55));
        setMinimumSize(new Dimension(250, 55));

        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);

        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Automatically play click sound whenever any GameButton is successfully clicked
        addActionListener(e -> {
            AudioLoader.playClick();
        });
    }

    public GameButton()
    {
        this("");
    }

    public void setCornerRadius(int radius)
    {
        cornerRadius = Math.max(0, radius);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        boolean isHovered = getModel().isRollover();
        boolean isPressed = getModel().isPressed();

        Color background;

        if (!isEnabled())
        {
            background = new Color(45, 60, 48);
        }
        else if (isPressed)
        {
            background = new Color(12, 95, 42);
        }
        else if (isHovered)
        {
            background = GameFonts.NEON_GREEN;
        }
        else
        {
            background = GameFonts.PANEL;
        }

        g2.setColor(background);

        g2.fill(new RoundRectangle2D.Float(
            0, 0,
            getWidth() - 1,
            getHeight() - 1,
            cornerRadius,
            cornerRadius
        ));

        g2.dispose();

        if (isHovered && !isPressed && isEnabled())
        {
            setForeground(GameFonts.BACKGROUND);
        }
        else if (isEnabled())
        {
            setForeground(GameFonts.MINT_GREEN);
        }
        else
        {
            setForeground(Color.GRAY);
        }

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        boolean isHovered = getModel().isRollover();

        if (isHovered && isEnabled())
        {
            g2.setColor(GameFonts.MINT_GREEN);
        }
        else
        {
            g2.setColor(GameFonts.NEON_GREEN);
        }

        g2.draw(new RoundRectangle2D.Float(
            1, 1,
            getWidth() - 3,
            getHeight() - 3,
            cornerRadius,
            cornerRadius
        ));

        g2.dispose();
    }
}