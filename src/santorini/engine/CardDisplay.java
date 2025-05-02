package santorini.engine;

import santorini.utils.ImageUtils;

import javax.swing.*;

public class CardDisplay {
  private JLabel cardTitle, cardName, cardDescription, godCardImage;
  public CardDisplay(JLabel cardTitle, JLabel cardName, JLabel cardDescription, JLabel cardImage){
    this.cardTitle = cardTitle;
    this.cardName = cardName;
    this.cardDescription = cardDescription;
    this.godCardImage = cardImage;
  }

  public void updateCardPanel(Player player){
    cardTitle.setText(player.getName() + "’s Card");
    cardName.setText(player.getGodCard().getName());
    cardDescription.setText("<html><div style='text-align:center;'>" +
            player.getGodCard().getDescription() + "</div></html>");
    ImageUtils.setScaledGodCardIcon(player.getGodCard(), godCardImage, 200, 300);
  }
}
