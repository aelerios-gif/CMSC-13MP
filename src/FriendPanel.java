import java.awt.*;
import javax.swing.*;

public class FriendPanel extends JDialog
{
    private GamePanel gamePanel;

    public FriendPanel(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        setTitle("Call a Friend");
        setSize(750, 300);
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
            new Friend("Dexter", QuestionCategory.Declarative),
            new Friend("Dr. Doofenshmirtz", QuestionCategory.Imperative),
            new Friend("Nigel Numbuh 1", QuestionCategory.Imperative, QuestionCategory.Declarative),
            new Friend("Morty Smith", QuestionCategory.OOP, QuestionCategory.Functional),
        };

        for(Friend friend : friends)
        {
            JButton friendButton = new JButton(friend.getName() + " - " + java.util.Arrays.toString(friend.getSpecialties()));

            friendButton.addActionListener(e -> {
                this.gamePanel.callFriend(friend);
                dispose();
            });

            add(friendButton);
        }
    }
}