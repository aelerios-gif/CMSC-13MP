import java.awt.*;
import javax.swing.*;

public class FriendPanel extends JDialog 
{

    private GamePanel gamePanel;

    public FriendPanel(JFrame parent, GamePanel gamePanel) 
    {

        super(parent, "Call a Friend", true);

        this.gamePanel = gamePanel;

        setSize(600, 450);

        setLocationRelativeTo(parent);

        setLayout(new BorderLayout());

        JLabel title = new JLabel("CALL A FRIEND");

        title.setFont(new Font("Arial", Font.BOLD, 28));

        title.setHorizontalAlignment(SwingConstants.CENTER);

        add(title, BorderLayout.NORTH);

        JPanel friendPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        Friend[] friends = 
        {

            new Friend("Double D", "Procedural", 90),

            new Friend("Jimmy Neutron", "OOP", 85),

            new Friend("BMO", "Functional", 80),

            new Friend("Dexter", "Declarative", 75),

            new Friend("Dr. Doofenshmirtz", "Imperative", 65),

            new Friend("Nigel Numbuh 1", "Declarative and Imperative", 50),

            new Friend("Morty Smith", "OOP and Functional", 90)
        };

        for (Friend friend : friends) 
        {

            JButton button = new JButton(friend.getName() + "\n" + friend.getSpecialty());

            button.setFont(new Font("Arial", Font.BOLD, 16));

            button.addActionListener(e -> {

                gamePanel.callFriend(friend);

                dispose();

            });

            friendPanel.add(button);
        }

        add(friendPanel, BorderLayout.CENTER);

        JButton cancelButton = new JButton("CANCEL");

        cancelButton.addActionListener(e -> {

            dispose();

        });

        add(cancelButton, BorderLayout.SOUTH);
    }
}