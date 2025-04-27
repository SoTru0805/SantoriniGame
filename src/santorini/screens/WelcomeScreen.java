package santorini.screens;

import santorini.Main;

import javax.swing.*;
import java.awt.*;

import santorini.screens.ScreenManager;

public class WelcomeScreen implements Screen {

  @Override
  public JPanel getPanel() {
    JPanel panel = new JPanel();

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

    JLabel title = new JLabel("Welcome to Santorini");
    title.setFont(new Font("Arial", Font.BOLD, 26));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton startButton = new JButton("Start Game");
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    startButton.addActionListener(e -> {
    GameScreen gameScreen = (GameScreen) ScreenManager.getScreen("GAME");
    if (gameScreen != null) {
      gameScreen.startGameSetup();
    }
    ScreenManager.showScreen("GAME");
    });

    JButton tutorialButton = new JButton("How to Play");
    tutorialButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    tutorialButton.addActionListener(e -> ScreenManager.showScreen("TUTORIAL"));

    panel.add(title);
    panel.add(Box.createVerticalStrut(40));
    panel.add(startButton);
    panel.add(Box.createVerticalStrut(20));
    panel.add(tutorialButton);

    return panel;
  }
}
