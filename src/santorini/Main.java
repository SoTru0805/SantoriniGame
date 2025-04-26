package santorini;

import santorini.engine.Player;
import santorini.godcards.GodCard;
import santorini.godcards.ArtemisGod;
import santorini.godcards.DemeterGod;
import santorini.board.Board;
import santorini.board.Cell;
import santorini.board.CellButton;


import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static JFrame frame;
    private static CardLayout cardLayout = new CardLayout();
    private static JPanel mainPanel = new JPanel(cardLayout);
    private static Board board; // Add this to store the board

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
        JButton backButton = new JButton("←");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "WELCOME"));

        JButton godCardButton = new JButton("God Cards Information →");
        godCardButton.addActionListener(e -> showGodCardInfo());

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(godCardButton, BorderLayout.EAST);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static void showGodCardInfo() {
        GodCard artemisCard = new ArtemisGod();
        GodCard demeterCard = new DemeterGod();

        String message = artemisCard.getName() + ":\n" + artemisCard.getDescription()
                + "\n\n"
                + demeterCard.getName() + ":\n" + demeterCard.getDescription();

        JOptionPane.showMessageDialog(frame,
                message,
                "God Cards Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void startGame() {
        String name1 = JOptionPane.showInputDialog(null, "Enter Player 1's name:", "Santorini", JOptionPane.PLAIN_MESSAGE);
        if (name1 == null || name1.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Player 1 name is required. Exiting.");
            System.exit(0);
        }

        String name2 = JOptionPane.showInputDialog(null, "Enter Player 2's name:", "Santorini", JOptionPane.PLAIN_MESSAGE);
        if (name2 == null || name2.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Player 2 name is required. Exiting.");
            System.exit(0);
        }

        Player player1 = new Player(name1);
        Player player2 = new Player(name2);

        GodCard artemisCard = new ArtemisGod();
        GodCard demeterCard = new DemeterGod();
        List<GodCard> godChoices = Arrays.asList(artemisCard, demeterCard);

        String[] options = godChoices.stream().map(GodCard::getName).toArray(String[]::new);

        String p1Choice = (String) JOptionPane.showInputDialog(
                null,
                name1 + ", choose your God Card:",
                "God Card Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );


        if (p1Choice == null) System.exit(0);

        GodCard godCard1 = createGodCard(p1Choice);
        player1.setGodCard(godCard1);

        String p2Choice = godChoices.get(0).equals(p1Choice) ? godChoices.get(1) : godChoices.get(0);
        GodCard godCard2 = createGodCard(p2Choice);
        player2.setGodCard(godCard2);

        // Create the real board
        board = new Board();

        // Create the board panel
        JPanel boardPanel = new JPanel(new GridLayout(5, 5, 2, 2));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boardPanel.setBackground(Color.DARK_GRAY);

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Cell cell = board.getCell(x, y);
                CellButton button = new CellButton(cell);
                boardPanel.add(button);
            }
        }

        // Create game log
        JTextArea gameLog = new JTextArea("Game’s Log:\n• Turn 1: ...");
        gameLog.setEditable(false);
        JScrollPane gameLogScroll = new JScrollPane(gameLog);
        gameLogScroll.setPreferredSize(new Dimension(300, 100));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(boardPanel, BorderLayout.CENTER);
        leftPanel.add(gameLogScroll, BorderLayout.SOUTH);

        // Right side with God Card Info
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel godCardTitle = new JLabel(player1.getName() + "’s Card");
        godCardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        godCardTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel godCardName = new JLabel(player1.getGodCard().getName());
        godCardName.setAlignmentX(Component.CENTER_ALIGNMENT);
        godCardName.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextArea godDescription = new JTextArea(player1.getGodCard().getDescription());
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

        // Replace the frame contents
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(leftPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.revalidate();
        frame.repaint();
    }

    private static GodCard createGodCard(String name) {
        return switch (name) {
            case "Artemis" -> new ArtemisGod();
            case "Demeter" -> new DemeterGod();
            default -> null;
        };
    }
}
