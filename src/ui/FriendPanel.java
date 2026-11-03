package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Friend;
import model.FriendRoster;

public class FriendPanel extends JDialog
{
    private GamePanel gamePanel;

    public FriendPanel(GamePanel gamePanel, Friend currentProctor)
    {
        this.gamePanel = gamePanel;

        setTitle("Call a Friend");
        setUndecorated(true);
        setModal(true);
        setSize(850, 480);
        setLocationRelativeTo(gamePanel);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(
            "CHOOSE A FRIEND TO HELP YOU!",
            SwingConstants.CENTER
        );

        titleLabel.setFont(util.GameFonts.heading());
        titleLabel.setForeground(util.GameFonts.MINT_GREEN);

        add(titleLabel, BorderLayout.NORTH);

        JPanel friendsPanel = new JPanel(new GridLayout(2, 4, 12, 12));
        friendsPanel.setOpaque(false);

        for(Friend friend : FriendRoster.getFriends())
        {
            GameButton friendButton = new GameButton();

            friendButton.setLayout(new BorderLayout(5, 5));
            friendButton.setPreferredSize(new Dimension(190, 170));

            ImageIcon originalIcon = new ImageIcon(friend.getImagePath());

            if(originalIcon.getIconWidth() > 0)
            {
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                    85, 95, Image.SCALE_SMOOTH
                );

                friendButton.setIcon(new ImageIcon(scaledImage));
            }

            String specialties = Arrays.toString(friend.getSpecialties());

            friendButton.setText(
                "<html><center>"
                + friend.getName()
                + "<br>"
                + specialties
                + "</center></html>"
            );

            friendButton.setHorizontalTextPosition(SwingConstants.CENTER);
            friendButton.setVerticalTextPosition(SwingConstants.BOTTOM);

            boolean isProctor = friend.getName().equals(
                currentProctor.getName()
            );

            if(isProctor)
            {
                friendButton.setEnabled(false);
                friendButton.setToolTipText("This friend is the current proctor.");
            }
            else
            {
                friendButton.addActionListener(e -> {
                    this.gamePanel.callFriend(friend);
                    dispose();
                });
            }

            friendsPanel.add(friendButton);
        }

        GameButton closeButton = new GameButton("CANCEL");
        closeButton.setPreferredSize(new Dimension(150, 45));
        closeButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(closeButton);

        add(friendsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}