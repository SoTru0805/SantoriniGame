package santorini.elements;

import santorini.elements.ArtemisGod;
import santorini.elements.GodCard;
import santorini.engine.Player;

import javax.swing.*;
import java.awt.*;

public class GodCardFrame extends JFrame {
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel godCardImage;

    // Constructor that takes a GodCard object and displays its information
    public GodCardFrame(GodCard godCard) {
        // Initialize the frame
        setTitle("GodCard: " + godCard.getName());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout for the panel
        setLayout(new BorderLayout());

        // Name label (displays the name of the GodCard)
        nameLabel = new JLabel("Name: " + godCard.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Description label (displays the description of the GodCard)
        descriptionLabel = new JLabel("Description: " + godCard.getDescription(), SwingConstants.CENTER);

        // Image label (displays the GodCard image)
        godCardImage = new JLabel();
        godCardImage.setHorizontalAlignment(SwingConstants.CENTER);

        // Fetch the image filename from the map using the GodCard name
        String imageFilename = getGodCardImage(godCard.getName());
        if (imageFilename != null) {
            // Load the image using the filename (assuming images are in "path_to_images" folder)
            ImageIcon icon = new ImageIcon("path_to_images/" + imageFilename);
            godCardImage.setIcon(icon);
        } else {
            // Fallback if image is not found in the map
            godCardImage.setText("Image not found");
        }

        // Add the components to the frame
        add(nameLabel, BorderLayout.NORTH);
        add(descriptionLabel, BorderLayout.CENTER);
        add(godCardImage, BorderLayout.SOUTH);
    }

    // A simple method to fetch the image filename based on the GodCard's name
    private String getGodCardImage(String cardName) {
        switch (cardName) {
            case "Artemis":
                return "artemis.png";
            case "Apollo":
                return "apollo.png";
            // Add other cases for different GodCards as needed
            default:
                return null;
        }
    }

    // Main method to test the frame with a sample GodCard (like Artemis)
    public static void main(String[] args) {
        // Example of creating a GodCard for Artemis
        //Player player = new Player("Player 1"); // Dummy player, replace as needed
        GodCard artemisCard = new ArtemisGod(); // ArtemisGodCard

        // Display the GodCard in the frame
        GodCardFrame frame = new GodCardFrame(artemisCard);
        frame.setVisible(true);
    }
}

