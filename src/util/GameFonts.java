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

    // ==========================
    // GREEN THEME COLORS
    // ==========================

    public static final Color BACKGROUND =
        new Color(7, 19, 11);

    public static final Color PANEL =
        new Color(18, 61, 32);

    public static final Color BUTTON =
        new Color(22, 59, 34);

    public static final Color NEON_GREEN =
        new Color(32, 199, 90);

    public static final Color MINT_GREEN =
        new Color(155, 255, 176);

    public static final Color TEXT =
        new Color(232, 245, 233);


    private GameFonts()
    {
        // Prevent object creation
    }


    // ==========================
    // LOAD FONTS
    // ==========================

    public static void loadFonts()
    {
        pixelFont = loadFont(
            "/fonts/PressStart2P-Regular.ttf",
            "resources/fonts/PressStart2P-Regular.ttf"
        );

        techFont = loadFont(
            "/fonts/ShareTechMono-Regular.ttf",
            "resources/fonts/ShareTechMono-Regular.ttf"
        );

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

        // Development fallback
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


    // ==========================
    // SWING DEFAULTS
    // ==========================

    private static void installSwingDefaults()
    {
        Font body = body();
        Font button = button();

        // Fonts
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


        // General components
        UIManager.put("Panel.background", BACKGROUND);
        UIManager.put("Label.foreground", TEXT);

        // Buttons
        UIManager.put("Button.background", BUTTON);
        UIManager.put("Button.foreground", MINT_GREEN);
        UIManager.put("Button.select", PANEL);
        UIManager.put("Button.focus", NEON_GREEN);

        // Text fields
        UIManager.put("TextField.background", PANEL);
        UIManager.put("TextField.foreground", TEXT);

        UIManager.put("TextArea.background", PANEL);
        UIManager.put("TextArea.foreground", TEXT);

        // Checkboxes and radio buttons
        UIManager.put("CheckBox.background", BACKGROUND);
        UIManager.put("CheckBox.foreground", TEXT);

        UIManager.put("RadioButton.background", BACKGROUND);
        UIManager.put("RadioButton.foreground", TEXT);

        // Dialogs
        UIManager.put("OptionPane.background", BACKGROUND);
        UIManager.put("OptionPane.messageForeground", TEXT);

        // Tooltips
        UIManager.put("ToolTip.background", PANEL);
        UIManager.put("ToolTip.foreground", MINT_GREEN);
    }


    // ==========================
    // FONT METHODS
    // ==========================

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
