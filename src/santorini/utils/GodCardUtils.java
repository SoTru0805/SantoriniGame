package santorini.utils;

import santorini.engine.Player;
import santorini.godcards.GodCard;
import santorini.godcards.GodCardDeck;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GodCardUtils {

  public static void assignGodCardsToPlayers(List<Player> players, GodCardDeck deck) {
    if (deck == null || players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Deck and player list must not be null or empty.");
    }

    deck.shuffle();

    for (Player player : players) {
      GodCard card = deck.draw();
      player.setGodCard(card);
      showCardAssignment(player);
    }
  }

  public static void showCardAssignment(Player player) {
    JLabel godCardImage = ImageUtils.setUpGodCardLabel();

    GodCard godCard = player.getGodCard();
    ImageIcon scaledIcon = ImageUtils.setScaledGodCardIcon(godCard, godCardImage, 200, 300);

    JLabel imageLabel = new JLabel(scaledIcon);
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel nameLabel = new JLabel("<html><div style='text-align: center;'><b>" +
            player.getName() + "</b>, you have been assigned the God Card:<br><br>" +
            "<b>" + godCard.getName() + "</b><br><br>" +
            "<i>Power:</i><br>" + godCard.getDescription() + "</div></html>");
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(nameLabel);
    panel.add(Box.createVerticalStrut(15)); // spacing
    panel.add(imageLabel);

    JOptionPane.showMessageDialog(
            null,
            panel,
            "God Card Assignment",
            JOptionPane.INFORMATION_MESSAGE
    );
  }
}

