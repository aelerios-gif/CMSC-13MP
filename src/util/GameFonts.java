package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.UIManager;

public final class GameFonts 
{
    private static Font pixelFont;
    private static Font techFont;

    private GameFonts()
    {
        //prevent object creation;
    }

    public static void loadFonts()
    {
        pixelFont = loadFont("/fonts/PressStart2P-Regular.ttf", "resources/fonts/PressStart2P-Regular.ttf");

        techFont = loadFont("/fonts/ShareTechMono-Regular.ttf", "resources/fonts/ShareTechMono-Regular.ttf");

        //use the readable font as the default Swing font
        installSwingDefaults();
    }

    private static Font loadFont(String resourcePath, String filePath)
    {
        try(InputStream stream = GameFonts.class.getResourceAsStream(resourcePath))
        {
            if(stream != null)
            {
                return Font.createFont(Font.TRUETYPE_FONT, stream);
            }
        }
        catch(FontFormatException | IOException exception)
        {
            System.err.println("Could not load bundled font: " + resourcePath);
        }

        //development fallback: load from the project folder
        try
        {
            File file = new File(filePath);

            if(file.isFile())
            {
                return Font.createFont(Font.TRUETYPE_FONT, file);
            }
        }
        catch(FontFormatException | IOException exception)
        {
            System.err.println("Could not load font file: " + filePath);
        }

        System.err.println("Using system font as fallback for: " + resourcePath);

        return new Font(Font.MONOSPACED, Font.PLAIN, 14);
    }

    private static void installSwingDefaults()
    {
        Font body = body();
        Font button = button();

        UIManager.put("Label.font", body);
        UIManager.put("Button.font", button);
        UIManager.put("CheckBox.font", body);
        UIManager.put("RadioButton.font", body);
        UIManager.put("TextField.font", body);
        UIManager.put("PasswordField.font", body);
        UIManager.put("TextArea.font", body);
        UIManager.put("TextPane.font", body);
        UIManager.put("EditorPane.font", body);
        UIManager.put("ComboBox.font", body);
        UIManager.put("List.font", body);
        UIManager.put("Table.font", body);
        UIManager.put("TableHeader.font", button);
        UIManager.put("Menu.font", body);
        UIManager.put("MenuItem.font", body);
        UIManager.put("OptionPane.messageFont", body);
        UIManager.put("OptionPane.buttonFont", button);
        UIManager.put("TitledBorder.font", button);
        UIManager.put("ToolTip.font", body);

        Color background = new Color(11, 16, 32);
        Color foreground = new Color(230, 241, 255);
        Color cyan = new Color(0, 245, 255);
        Color purple = new Color(192, 132, 252);

        UIManager.put("Panel.background", background);
        UIManager.put("OptionPane.background", background);
        UIManager.put("OptionPane.messageForeground", foreground);

        UIManager.put("Label.foreground", foreground);
        UIManager.put("Button.background", new Color(20, 30, 50));
        UIManager.put("Button.foreground", cyan);

        UIManager.put("TextField.background", new Color(17, 24, 39));
        UIManager.put("TextField.foreground", foreground);
        UIManager.put("TextArea.background", new Color(17, 24, 39));
        UIManager.put("TextArea.foreground", foreground);

        UIManager.put("CheckBox.background", background);
        UIManager.put("CheckBox.foreground", foreground);
        UIManager.put("RadioButton.background", background);
        UIManager.put("RadioButton.foreground", foreground);

        UIManager.put("ComboBox.background", new Color(20, 30, 50));
        UIManager.put("ComboBox.foreground", foreground);
        UIManager.put("ToolTip.background", new Color(20, 30, 50));
        UIManager.put("ToolTip.foreground", cyan);
    }

    public static Font title()
    {
        return pixelFont.deriveFont(Font.PLAIN, 24f);
    }

    public static Font heading()
    {
        return pixelFont.deriveFont(Font.PLAIN, 16f);
    }

    public static Font smallPixel()
    {
        return pixelFont.deriveFont(Font.PLAIN, 8f);
    }

    public static Font question()
    {
        return techFont.deriveFont(Font.PLAIN, 22f);
    }

    public static Font button()
    {
        return techFont.deriveFont(Font.BOLD, 18f);
    }

    public static Font body()
    {
        return techFont.deriveFont(Font.PLAIN, 16f);
    }

    public static Font small()
    {
        return techFont.deriveFont(Font.PLAIN, 13f);
    }
}
