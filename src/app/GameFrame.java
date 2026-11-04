package app;

import java.awt.*;
import javax.swing.*;
import ui.GamePanel;
import ui.HomePanel;
import ui.SplashPanel;
import ui.TutorialPanel;

public class GameFrame extends JFrame 
{

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private GamePanel gamePanel;

    private boolean fullscreen = false;

    private Rectangle previousBounds;


    public GameFrame() 
    {

        setUndecorated(true);
        setResizable(true);

        setTitle("Debugged");
        setSize(900, 650);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);

        // Create the screens
        SplashPanel splashPanel = new SplashPanel(this);
        HomePanel homePanel = new HomePanel(this);
        TutorialPanel tutorialPanel = new TutorialPanel(this);

        gamePanel = new GamePanel(this);

        // Add screens
        mainPanel.add(splashPanel, "SPLASH");
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(tutorialPanel, "TUTORIAL");
        mainPanel.add(gamePanel, "GAME");

        add(mainPanel);

        showScreen("SPLASH");
    }

    public void showScreen(String screenName) 
    {

        cardLayout.show(mainPanel, screenName);
    }

    public void startGame() 
    {

        gamePanel.resetGame();

        showScreen("GAME");

        gamePanel.showFirstProctorIntro();
    }

    public void returnToHome() 
    {
        showScreen("HOME");
    }

    public void showHome()
    {
        cardLayout.show(mainPanel, "HOME");
    }

    public boolean isFullscreen()
    {
        return fullscreen;
    }

    public void setFullscreen(boolean enabled)
    {
        if(enabled == fullscreen)
        {
            return;
        }

        if(enabled)
        {
            //save the current window size and position
            previousBounds = getBounds();

            //get the screen dimensions
            Rectangle screenBounds = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();

            //remove window decorations
            dispose();
            setUndecorated(true);

            //fill the screen without exclusive fullscreen
            setBounds(
                GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .getBounds()
            );

            setVisible(true);

            fullscreen = true;
        }
        else
        {
            //restore the previos window size and position
            dispose();

            setUndecorated(true);

            setBounds(previousBounds);

            setVisible(true);

            fullscreen = false;
        }
    }
}