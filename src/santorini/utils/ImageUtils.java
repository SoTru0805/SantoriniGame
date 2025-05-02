package santorini.utils;

import santorini.engine.Player;
import santorini.godcards.GodCard;

import javax.swing.*;
import java.awt.*;

public class ImageUtils {

  /**
   * Loads and sets a scaled icon from a GodCard's image path into a JLabel.
   * @param godCard the GodCard whose image path will be used
   * @param label the JLabel to apply the icon to
   * @param width desired width
   * @param height desired height
   */
  public static ImageIcon setScaledGodCardIcon(GodCard godCard, JLabel label, int width, int height) {
    if (godCard == null || label == null || godCard.getImagePath() == null){
      return null;
    }

    ImageIcon icon = new ImageIcon(godCard.getImagePath());
    Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    ImageIcon imageIcon = new ImageIcon(scaledImage);
    label.setIcon(imageIcon);

    return imageIcon;
  }

  public static JLabel setUpGodCardLabel(){
    JLabel godCardImage = new JLabel();
    godCardImage.setHorizontalAlignment(SwingConstants.CENTER);
    godCardImage.setAlignmentX(Component.CENTER_ALIGNMENT);
    godCardImage.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

    return godCardImage;
  }
}
