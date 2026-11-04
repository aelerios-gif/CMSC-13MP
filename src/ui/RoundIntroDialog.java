
package ui;

import app.GameFrame;
import java.awt.*;
import javax.swing.*;
import model.Friend;
import util.GameFonts;

public class RoundIntroDialog extends JDialog
{
    public RoundIntroDialog(
        GameFrame frame,
        Friend proctor,
        String message
    )
    {
        super(frame, true);

        setUndecorated(true);

        //get the current dimensions of the game window
        int margin = 10;
        int dialogHeight = 250;

        int dialogWidth = frame.getWidth() - (margin * 2);

        // make the dialogue stretch across the bottom
        setSize(dialogWidth, dialogHeight);

        int x = frame.getX() + margin;
        int y = frame.getY() + frame.getHeight() - dialogHeight - margin;

        setLocation(x, y);

        //setLayout(new BorderLayout());

        setResizable(false);
        setLayout(new BorderLayout());

        // Main dialogue panel
        JPanel dialoguePanel = new JPanel(new BorderLayout(15, 10))
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                // White dialogue box
                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRoundRect(
                    2, 2,
                    getWidth() - 4,
                    getHeight() - 4,
                    45, 45
                );

                // Blue outline
                g2d.setColor(new Color(120, 190, 225));
                g2d.setStroke(new BasicStroke(5));

                g2d.drawRoundRect(
                    3, 3,
                    getWidth() - 7,
                    getHeight() - 7,
                    45, 45
                );

                g2d.dispose();
            }
        };

        dialoguePanel.setOpaque(false);
        dialoguePanel.setBorder(
            BorderFactory.createEmptyBorder(25, 35, 20, 30)
        );

        // Dialogue text
        JTextArea messageArea = new JTextArea(message);

        messageArea.setFont(GameFonts.question());
        messageArea.setForeground(new Color(45, 75, 190));
        messageArea.setOpaque(false);
        messageArea.setEditable(false);
        messageArea.setFocusable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        JScrollPane messageScrollPane = new JScrollPane(messageArea);

        messageScrollPane.setBorder(null);
        messageScrollPane.setOpaque(false);
        messageScrollPane.getViewport().setOpaque(false);

        messageScrollPane.setHorizontalScrollBarPolicy(
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        messageScrollPane.setVerticalScrollBarPolicy(
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        dialoguePanel.add(messageScrollPane, BorderLayout.CENTER);

        // Proctor portrait
        JLabel proctorImage = new JLabel();

        ImageIcon originalIcon = new ImageIcon(
            proctor.getImagePath()
        );

        if(originalIcon.getIconWidth() > 0)
        {
            Image scaledImage = originalIcon.getImage()
                .getScaledInstance(
                    150,
                    170,
                    Image.SCALE_SMOOTH
                );

            proctorImage.setIcon(new ImageIcon(scaledImage));
        }

        proctorImage.setHorizontalAlignment(SwingConstants.CENTER);
        proctorImage.setVerticalAlignment(SwingConstants.CENTER);

        dialoguePanel.add(proctorImage, BorderLayout.EAST);

        // Continue button
        JButton continueButton = new JButton("CONTINUE");

        continueButton.setFont(GameFonts.button());
        continueButton.setFocusPainted(false);
        continueButton.setBackground(GameFonts.BUTTON);
        continueButton.setForeground(GameFonts.MINT_GREEN);

        continueButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(continueButton);

        dialoguePanel.add(buttonPanel, BorderLayout.SOUTH);

        add(dialoguePanel, BorderLayout.CENTER);

        // Allow Enter to continue
        getRootPane().setDefaultButton(continueButton);
    }
}
