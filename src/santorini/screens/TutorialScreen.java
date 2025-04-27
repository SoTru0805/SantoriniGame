package santorini.screens;

import santorini.Main;
import santorini.screens.ScreenManager;

import javax.swing.*;
import java.awt.*;

public class TutorialScreen implements Screen {

  @Override
  public JPanel getPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    JTextArea tutorialText = new JTextArea();
    tutorialText.setEditable(false);
    tutorialText.setText(""); // You can paste your tutorial content here later
    tutorialText.setLineWrap(true);
    tutorialText.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(tutorialText);
    scrollPane.setPreferredSize(new Dimension(600, 400));

    JPanel bottomPanel = new JPanel(new BorderLayout());

    JButton backButton = new JButton("←");
    backButton.addActionListener(e -> ScreenManager.showScreen("WELCOME"));

    JButton godCardButton = new JButton("God Cards Info →");
    godCardButton.addActionListener(e -> showGodCardInfo());

    bottomPanel.add(backButton, BorderLayout.WEST);
    bottomPanel.add(godCardButton, BorderLayout.EAST);

    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(bottomPanel, BorderLayout.SOUTH);

    return panel;
  }

  private void showGodCardInfo() {

  }
}
