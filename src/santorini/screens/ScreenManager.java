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
    JPanel screenPanel = screen.getPanel();

    JScrollPane scrollPane = new JScrollPane(screenPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    mainPanel.add(scrollPane, name);
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
