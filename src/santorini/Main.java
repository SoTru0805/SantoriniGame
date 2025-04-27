package santorini;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import santorini.board.Board;
import santorini.board.BoardGUI;
import java.util.Collections;


public class Main {
    private static CardLayout cardLayout = new CardLayout();
    private static JPanel mainPanel = new JPanel(cardLayout);
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Santorini");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);

            JPanel welcomePanel = createWelcomePanel();
            JPanel tutorialPanel = createTutorialPanel();

            mainPanel.add(welcomePanel, "WELCOME");
            mainPanel.add(tutorialPanel, "TUTORIAL");

            frame.setContentPane(mainPanel);
            frame.setVisible(true);
        });
    }

    private static JPanel createWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JLabel title = new JLabel("Welcome to Santorini");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 40));
        startButton.addActionListener(e -> startGame());

        JButton tutorialButton = new JButton("How to play");
        tutorialButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutorialButton.setMaximumSize(new Dimension(200, 40));
        tutorialButton.addActionListener(e -> cardLayout.show(mainPanel, "TUTORIAL"));

        panel.add(title);
        panel.add(Box.createVerticalStrut(40));
        panel.add(startButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(tutorialButton);

        return panel;
    }

    private static JPanel createTutorialPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea tutorialText = new JTextArea();
        tutorialText.setEditable(false);
        tutorialText.setText("");
        tutorialText.setLineWrap(true);
        tutorialText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(tutorialText);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JButton backButton = new JButton("←");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "WELCOME"));

        JButton godCardButton = new JButton("God Cards Information →");
        godCardButton.addActionListener(e -> showGodCardInfo());

        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(godCardButton, BorderLayout.EAST);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static void showGodCardInfo() {
        JOptionPane.showMessageDialog(frame,
                "Artemis:\nYour Worker may move one additional time, but not back to its initial space.\n\n" +
                        "Demeter:\nYour Worker may build one additional time, but not on the same space.",
                "God Cards Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void startGame() {
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


        List<String> godCards = Arrays.asList("Artemis", "Demeter");

        Collections.shuffle(godCards); //This is to shuffle the card

        String p1GodCard = godCards.get(0); //These two for automatically assign
        String p2GodCard = godCards.get(1);

        JOptionPane.showMessageDialog(null, player1 + ", you have received the God Card: " + p1GodCard, "God Card Assignment", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, player2 + ", you have received the God Card: " + p2GodCard, "God Card Assignment", JOptionPane.INFORMATION_MESSAGE);


        // Create the real Board and BoardGUI
        Board board = new Board(); // <- your logic (must exist)
        BoardGUI boardGUI = new BoardGUI(board); // <- your GUI wrapper

        JPanel boardPanel = boardGUI.getBoardPanel(); // <- get the actual panel to add

        // Game log area
        JTextArea gameLog = new JTextArea("Game’s Log:\n• Turn 1: ...");
        gameLog.setEditable(false);
        JScrollPane gameLogScroll = new JScrollPane(gameLog);
        gameLogScroll.setPreferredSize(new Dimension(300, 100));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(boardPanel, BorderLayout.CENTER);
        leftPanel.add(gameLogScroll, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel godCardTitle = new JLabel(player1 + "’s Card");
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

        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(leftPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.revalidate();
        frame.repaint();
    }

    private static String getGodCardDescription(String card) {
        return switch (card) {
            case "Artemis" -> "Power: Your Worker may move one additional time, but not back to its initial space.";
            case "Demeter" -> "Power: Your Worker may build one additional time, but not on the same space.";
            default -> "";
        };
    }
}
