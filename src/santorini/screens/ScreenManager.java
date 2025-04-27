package santorini.screens;

import santorini.screens.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
  private static CardLayout cardLayout = new CardLayout();
  private static JPanel mainPanel = new JPanel(cardLayout);
  private static Map<String, Screen> screens = new HashMap<>();

  public static JPanel getMainPanel() {
    return mainPanel;
  }

  public static void registerScreen(String name, Screen screen) {
    screens.put(name, screen);
    mainPanel.add(screen.getPanel(), name);
  }

  public static Screen getScreen(String name) {
    return screens.get(name);
  }

  public static void showScreen(String name) {
    if (!screens.containsKey(name)) {
      throw new IllegalArgumentException("Screen " + name + " not found!");
    }
    cardLayout.show(mainPanel, name);
  }
}
