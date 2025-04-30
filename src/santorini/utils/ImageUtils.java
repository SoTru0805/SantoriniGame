package santorini.utils;

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
  public static Image setScaledGodCardIcon(GodCard godCard, JLabel label, int width, int height) {
    if (godCard == null || label == null || godCard.getImagePath() == null){
      return null;
    }

    ImageIcon icon = new ImageIcon(godCard.getImagePath());
    Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    label.setIcon(new ImageIcon(scaledImage));

    return scaledImage;
  }
}
