package santorini.screens;

import santorini.godcards.GodCard;
import santorini.godcards.GodCardDeck;
import santorini.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class GodCardInfoScreen implements Screen {
  private GodCardDeck deck;
  private JPanel panel;
  private int GOD_CARD_COUNTER = 0;

  public GodCardInfoScreen(GodCardDeck deck) {
    this.deck = deck;
    this.panel = null;
  }

  @Override
  public JPanel getPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    for (String name : deck.getAvailableGodCardNames()) {
      GOD_CARD_COUNTER++;

      GodCard card = deck.peekGodCard(name);

      JPanel cardPanel = new JPanel(new BorderLayout(20, 20));
      cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

      // Left side of page: Name & Description
      JTextArea text = new JTextArea(GOD_CARD_COUNTER + ". " + card.getName() + "\n" + card.getDescription());
      text.setEditable(false);
      text.setLineWrap(true);
      text.setWrapStyleWord(true);
      text.setFont(new Font("Arial", Font.PLAIN, 20));
      text.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      cardPanel.add(text, BorderLayout.CENTER);

      JLabel godCardImage = new JLabel();
      godCardImage.setHorizontalAlignment(SwingConstants.CENTER);
      godCardImage.setAlignmentX(Component.CENTER_ALIGNMENT);
      godCardImage.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

      // Right side of page: Image
      ImageIcon scaledImage = ImageUtils.setScaledGodCardIcon(card, godCardImage, 200, 300);
      JLabel imageLabel = new JLabel(scaledImage);
      cardPanel.add(imageLabel, BorderLayout.EAST);

      panel.add(cardPanel);
      panel.add(new JSeparator());
    }

    // Back Button
    JButton backButton = new JButton("← Back to Tutorial");
    backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    backButton.addActionListener(e -> ScreenManager.showScreen("TUTORIAL"));
    panel.add(Box.createVerticalStrut(20));
    panel.add(backButton);

    this.panel = panel;
    return panel;
  }

  @Override
  public JScrollPane getScrollPanel() {
    JScrollPane scrollPane = new JScrollPane(panel);
    return scrollPane;
  }
}
