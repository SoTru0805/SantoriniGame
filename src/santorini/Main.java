package santorini;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String player1 = JOptionPane.showInputDialog(null, "Enter Player 1's name:", "Santorini", JOptionPane.PLAIN_MESSAGE);
            if (player1 == null || player1.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Player 1 name is required. Exiting.");
                System.exit(0);
            }

            String player2 = JOptionPane.showInputDialog(null, "Enter Player 2's name:", "Santorini", JOptionPane.PLAIN_MESSAGE);
            if (player2 == null || player2.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Player 2 name is required. Exiting.");
                System.exit(0);
            }

            // 2 god cards selection for player 1:
            List<String> godCards = Arrays.asList("Artemis", "Demeter");

            String p1GodCard = (String) JOptionPane.showInputDialog(
                    null,
                    player1 + ", choose your God Card:",
                    "God Card Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    godCards.toArray(),
                    godCards.get(0)
            );

            if (p1GodCard == null) System.exit(0);

            // Removing first player's god card selection:
            String p2CardDefault = godCards.get(0).equals(p1GodCard) ? godCards.get(1) : godCards.get(0);

            // The final god card selection for player 2 (to be extended in next Sprint):
            String p2GodCard = (String) JOptionPane.showInputDialog(
                    null,
                    player2 + ", choose your God Card:",
                    "God Card Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new String[]{p2CardDefault},
                    p2CardDefault
            );

            if (p2GodCard == null) System.exit(0);

            // Main Game Window
            JFrame frame = new JFrame("Santorini - Sprint 2");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);

            // Board Panel
            JPanel boardPanel = new JPanel(); // <-- Changed: No 5x5 grid
            boardPanel.setPreferredSize(new Dimension(300, 300));
            boardPanel.setBackground(Color.LIGHT_GRAY);
            boardPanel.setBorder(BorderFactory.createTitledBorder(player1 + "’s Turn"));

            // Game History
            JTextArea gameLog = new JTextArea("Game’s Log:\n• Turn 1: ...");
            gameLog.setEditable(false);
            JScrollPane gameLogScroll = new JScrollPane(gameLog);
            gameLogScroll.setPreferredSize(new Dimension(300, 100));

            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.add(boardPanel, BorderLayout.CENTER);
            leftPanel.add(gameLogScroll, BorderLayout.SOUTH);

            // Player 1's God Card + Buttons
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel godCardTitle = new JLabel("Player 1’s Card");
            godCardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            godCardTitle.setFont(new Font("Arial", Font.BOLD, 16));

            JLabel godCardName = new JLabel(p1GodCard);
            godCardName.setAlignmentX(Component.CENTER_ALIGNMENT);
            godCardName.setFont(new Font("Arial", Font.PLAIN, 14));

            JTextArea godDescription = new JTextArea(getGodCardDescription(p1GodCard));
            godDescription.setLineWrap(true);
            godDescription.setWrapStyleWord(true);
            godDescription.setEditable(false);
            godDescription.setMaximumSize(new Dimension(200, 100));

            JButton undoButton = new JButton("Undo");
            undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton endTurnButton = new JButton("End Turn");
            endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            rightPanel.add(godCardTitle);
            rightPanel.add(Box.createVerticalStrut(10));
            rightPanel.add(godCardName);
            rightPanel.add(Box.createVerticalStrut(10));
            rightPanel.add(godDescription);
            rightPanel.add(Box.createVerticalStrut(20));
            rightPanel.add(undoButton);
            rightPanel.add(Box.createVerticalStrut(10));
            rightPanel.add(endTurnButton);

            // Frame Layout
            frame.setLayout(new BorderLayout());
            frame.add(leftPanel, BorderLayout.CENTER);
            frame.add(rightPanel, BorderLayout.EAST);

            frame.setVisible(true);
        });
    }

    // Private function to get the god card description
    private static String getGodCardDescription(String card) {
        return switch (card) {
            case "Artemis" -> "Power: Your Worker may move one additional time, but not back to its initial space.";
            case "Demeter" -> "Power: Your Worker may build one additional time, but not on the same space.";
            default -> "";
        };
    }
}
