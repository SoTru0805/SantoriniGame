package santorini;

import javax.swing.*;


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

            JOptionPane.showMessageDialog(null,
                    "Welcome " + player1 + " and " + player2 + "!\nGet ready to play Santorini.",
                    "Santorini",
                    JOptionPane.INFORMATION_MESSAGE
            );

            JFrame frame = new JFrame("Santorini - Sample Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);

            JLabel label = new JLabel("Game UI will go here", SwingConstants.CENTER);
            frame.add(label);

            frame.setVisible(true);
        });
    }
}

