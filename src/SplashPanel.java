import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

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

        /* JLabel title = new JLabel(
            "<html><center>" +
            "Debugged<br>" +
            "</center></html>"
        );*/

        //title.setFont(new Font("Arial", Font.BOLD, 45));

        //title.setHorizontalAlignment(
        //    SwingConstants.CENTER
        //);

        //JLabel startText = new JLabel("Touch anywhere to hello");

        //startText.setFont(new Font("Arial", Font.PLAIN, 20));

        //startText.setHorizontalAlignment(SwingConstants.CENTER);

        //add(title, BorderLayout.CENTER);
        //add(startText, BorderLayout.SOUTH);

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
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}