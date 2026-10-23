import java.awt.*;
import javax.swing.*;

public class FriendPanel extends JDialog
{
    private GamePanel gamePanel;

    public FriendPanel(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        setTitle("Call a Friend");
        setSize(350, 300);
        setLocationRelativeTo(gamePanel);
        setModal(true);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel titleLabel = new JLabel("Choose a friend to help you!", SwingConstants.CENTER);

        add(titleLabel);

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener(e -> dispose());
        add(closeButton);

        Friend[] friends = {
            new Friend("Double D", QuestionCategory.Procedural),
            new Friend("Jimmy Neutron", QuestionCategory.OOP),
            new Friend("BMO", QuestionCategory.Functional),
            new Friend("Dexter", QuestionCategory.ImperativeDeclarative),
            new Friend("Dr. Doofenshmirtz", QuestionCategory.ImperativeDeclarative),
            new Friend("Nigel Numbuh 1", QuestionCategory.ImperativeDeclarative),
            new Friend("Morty Smith", QuestionCategory.OOP),
        };

        for(Friend friend : friends)
        {
            JButton friendButton = new JButton(friend.getName() + " - " + friend.getSpecialty());

            friendButton.addActionListener(e -> {
                this.gamePanel.callFriend(friend);
                dispose();
            });

            add(friendButton);
        }
    }
}