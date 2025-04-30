package santorini.screens;

import santorini.engine.Player;
import santorini.godcards.GodCard;
import santorini.screens.ScreenManager;
import santorini.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class ResultScreen implements Screen {

  private Player winner;
  private int totalTurns;

  public ResultScreen(Player winner, int totalTurns) {
    this.winner = winner;
    this.totalTurns = totalTurns;
  }

  @Override
  public JPanel getPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

    JLabel title = new JLabel("Winner: " + winner.getName());
    title.setFont(new Font("Arial", Font.BOLD, 26));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel turns = new JLabel("Total Turns: " + totalTurns);
    turns.setFont(new Font("Arial", Font.PLAIN, 18));
    turns.setAlignmentX(Component.CENTER_ALIGNMENT);

    GodCard godCard = winner.getGodCard();
    JLabel godName = new JLabel("God Card: " + godCard.getName());
    godName.setFont(new Font("Arial", Font.BOLD, 20));
    godName.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel godCardDesc = new JLabel(godCard.getDescription());
    godName.setFont(new Font("Arial", Font.PLAIN, 14));
    godCardDesc.setHorizontalAlignment(SwingConstants.CENTER);
    godCardDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel godCardLabel = ImageUtils.setUpGodCardLabel();
    ImageIcon scaledImage = ImageUtils.setScaledGodCardIcon(godCard, godCardLabel, 200, 300);
    JLabel godImage = new JLabel(scaledImage);
    godImage.setAlignmentX(Component.CENTER_ALIGNMENT);

    panel.add(title);
    panel.add(Box.createVerticalStrut(10));
    panel.add(turns);
    panel.add(Box.createVerticalStrut(20));
    panel.add(godImage);
    panel.add(Box.createVerticalStrut(10));
    panel.add(godName);
    panel.add(Box.createVerticalStrut(10));
    panel.add(godCardDesc);

    return panel;
  }

  @Override
  public JScrollPane getScrollPanel() {
    return null;
  }
}
