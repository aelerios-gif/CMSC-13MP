package app;

import javax.swing.SwingUtilities;
import util.GameFonts;

public class Main 
{
    public static void main(String[] args) 
    {
        GameFonts.loadFonts();

        SwingUtilities.invokeLater(() -> {

            GameFrame frame = new GameFrame();

            frame.setVisible(true);

        });
    }
}