package santorini; // Declare the package name (like a folder)

import santorini.godcards.ArtemisGod;
import santorini.godcards.DemeterGod;
import santorini.godcards.GodCard;

import javax.swing.*; // Import Java Swing components (GUI elements)
import java.awt.*;    // Import AWT for layout and graphics
import java.util.Arrays; // Import utility class for array operations
import java.util.List;   // Import List interface

public class Main {
    // CardLayout lets us switch between different screens (welcome, tutorial, game)
    private static CardLayout cardLayout = new CardLayout();

    // The main panel that will hold all the other panels (screens)
    private static JPanel mainPanel = new JPanel(cardLayout);

    // The main application window
    private static JFrame frame;

    public static void main(String[] args) {
        // Run all GUI creation on the Event Dispatch Thread (good Swing practice)
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Santorini"); // Create the main game window with a title
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app when window is closed
            frame.setSize(900, 600); // Set window dimensions
            frame.setLocationRelativeTo(null); // Center the window on screen

            // Create both panels and assign them screen names
            JPanel welcomePanel = createWelcomePanel();
            JPanel tutorialPanel = createTutorialPanel();

            // Add both panels to the mainPanel with identifiers for switching
            mainPanel.add(welcomePanel, "WELCOME");
            mainPanel.add(tutorialPanel, "TUTORIAL");

            // Show the welcome screen first
            frame.setContentPane(mainPanel);
            frame.setVisible(true);
        });
    }

    // Create the welcome screen panel
    private static JPanel createWelcomePanel() {
        JPanel panel = new JPanel(); // Base panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100)); // Add padding

        JLabel title = new JLabel("Welcome to Santorini"); // Title label
        title.setFont(new Font("Arial", Font.BOLD, 26)); // Set font and size
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally

        // Button to start the game
        JButton startButton = new JButton("Start game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
        startButton.setMaximumSize(new Dimension(200, 40)); // Set button size
        startButton.addActionListener(e -> startGame()); // When clicked, start the game

        // Button to open the tutorial
        JButton tutorialButton = new JButton("How to play");
        tutorialButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutorialButton.setMaximumSize(new Dimension(200, 40));
        tutorialButton.addActionListener(e -> cardLayout.show(mainPanel, "TUTORIAL")); // Switch to tutorial

        // Add components to the panel with spacing
        panel.add(title);
        panel.add(Box.createVerticalStrut(40)); // Add vertical space
        panel.add(startButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(tutorialButton);

        return panel;
    }

    // Create the tutorial screen
    private static JPanel createTutorialPanel() {
        JPanel panel = new JPanel(new BorderLayout()); // Use border layout for structure

        JTextArea tutorialText = new JTextArea(); // Create a text area for tutorial
        tutorialText.setEditable(false); // Make it read-only
        tutorialText.setText(""); // Placeholder, you can paste your content here
        tutorialText.setLineWrap(true); // Wrap long lines
        tutorialText.setWrapStyleWord(true); // Wrap at word boundaries

        JScrollPane scrollPane = new JScrollPane(tutorialText); // Add scroll
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JPanel bottomPanel = new JPanel(); // Bottom bar for buttons

        // Back button to return to the welcome screen
        JButton backButton = new JButton("←");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "WELCOME"));

        // Button to show god card info
        JButton godCardButton = new JButton("God Cards Information →");
        godCardButton.addActionListener(e -> showGodCardInfo());

        // Use border layout to place back button left, god card right
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(godCardButton, BorderLayout.EAST);

        panel.add(scrollPane, BorderLayout.CENTER); // Tutorial text in center
        panel.add(bottomPanel, BorderLayout.SOUTH); // Buttons at the bottom

        return panel;
    }

    // Show a popup with god card descriptions
    private static void showGodCardInfo() {
        JOptionPane.showMessageDialog(frame,
                "Artemis:\nYour Worker may move one additional time, but not back to its initial space.\n\n" +
                        "Demeter:\nYour Worker may build one additional time, but not on the same space.",
                "God Cards Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Run when the user clicks “Start game”
    private static void startGame() {
        // Ask for player 1's name
        String player1 = JOptionPane.showInputDialog(null, "Enter Player 1's name:", "Santorini", JOptionPane.PLAIN_MESSAGE);
        if (player1 == null || player1.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Player 1 name is required. Exiting.");
            System.exit(0);
        }

        // Ask for player 2's name
        String player2 = JOptionPane.showInputDialog(null, "Enter Player 2's name:", "Santorini", JOptionPane.PLAIN_MESSAGE);
        if (player2 == null || player2.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Player 2 name is required. Exiting.");
            System.exit(0);
        }

        // List of available god cards
        List<GodCard> godCards = Arrays.asList(new ArtemisGod(), new DemeterGod());

        // Player 1 picks their god card
        String p1GodCardName = (String) JOptionPane.showInputDialog(
                null,
                player1 + ", choose your God Card:",
                "God Card Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                godCards.stream().map(GodCard::getName).toArray(),
                godCards.get(0).getName()
        );

        if (p1GodCardName == null) System.exit(0); // Exit if cancel

        // Automatically assign the other card to player 2
        String p2CardDefault = godCards.stream().filter(card -> !card.getName().equals(p1GodCardName))
                .findFirst().map(GodCard::getName).orElse("");

        String p2GodCardName = (String) JOptionPane.showInputDialog(
                null,
                player2 + ", choose your God Card:",
                "God Card Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{p2CardDefault},
                p2CardDefault
        );

        if (p2GodCardName == null) System.exit(0);

        System.out.println("Player 1 God Card: " + p1GodCardName); // Debugging line

        // Create instances of the selected God cards
        GodCard p1SelectedCard = createGodCardInstance(p1GodCardName);
        System.out.println("Created God Card: " + p1SelectedCard); // Debugging line

        GodCard p2SelectedCard = createGodCardInstance(p2GodCardName);

        // Create board area (placeholder)
        JPanel boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(300, 300));
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(BorderFactory.createTitledBorder(player1 + "’s Turn")); // Show turn title

        // Game log area
        JTextArea gameLog = new JTextArea("Game’s Log:\n• Turn 1: ...");
        gameLog.setEditable(false);
        JScrollPane gameLogScroll = new JScrollPane(gameLog);
        gameLogScroll.setPreferredSize(new Dimension(300, 100));

        // Left side with board and log
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(boardPanel, BorderLayout.CENTER);
        leftPanel.add(gameLogScroll, BorderLayout.SOUTH);

        // Right side for god card info and buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Use the dynamic God card name and description
        JLabel godCardTitle = new JLabel(player1 + "’s Card");
        godCardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        godCardTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel godCardName = new JLabel(p1SelectedCard.getName()); // Use dynamically fetched name
        godCardName.setAlignmentX(Component.CENTER_ALIGNMENT);
        godCardName.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextArea godDescription = new JTextArea(p1SelectedCard.getDescription()); // Use dynamically fetched description
        godDescription.setLineWrap(true);
        godDescription.setWrapStyleWord(true);
        godDescription.setEditable(false);
        godDescription.setMaximumSize(new Dimension(200, 100));

        JButton undoButton = new JButton("Undo");
        undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add all items to right panel
        rightPanel.add(godCardTitle);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(godCardName);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(godDescription);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(undoButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(endTurnButton);

        // Final layout: left = board & log, right = god info & buttons
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(leftPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.revalidate();
        frame.repaint();
    }

    // Utility method to create GodCard instances based on the selected card name
    private static GodCard createGodCardInstance(String godCardName) {
        switch (godCardName) {
            case "Artemis":
                return new ArtemisGod(); // Return the ArtemisGod instance
            case "Demeter":
                return new DemeterGod(); // Return the DemeterGod instance (example, you would need to create this class)
            default:
                return null; // Default, should handle unknown cards
        }
    }
}
