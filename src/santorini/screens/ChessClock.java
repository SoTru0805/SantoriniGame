package santorini.screens;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessClock {
    private Timer timer;
    private int player1TimeLeft;
    private int player2TimeLeft;
    private boolean isPlayer1Turn;
    private JLabel player1Label;
    private JLabel player2Label;
    private Runnable onTimeOut;

    public ChessClock(JLabel player1Label, JLabel player2Label, int minutes, Runnable onTimeOut) {
        this.player1Label = player1Label;
        this.player2Label = player2Label;
        this.player1TimeLeft = minutes * 60;
        this.player2TimeLeft = minutes * 60;
        this.onTimeOut = onTimeOut;
        isPlayer1Turn = true;

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isPlayer1Turn) {
                    player1TimeLeft--;
                    if (player1TimeLeft <= 0) {
                        player1TimeLeft = 0;
                        timer.stop();
                        onTimeOut.run();
                    }
                } else {
                    player2TimeLeft--;
                    if (player2TimeLeft <= 0) {
                        player2TimeLeft = 0;
                        timer.stop();
                        onTimeOut.run();
                    }
                }
                updateLabels();
            }
        });
        updateLabels();
    }

    public void start() { timer.start(); }
    public void pause() { timer.stop(); }
    public void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        updateLabels();
    }
    public void reset(int minutes) {
        timer.stop();
        player1TimeLeft = player2TimeLeft = minutes * 60;
        isPlayer1Turn = true;
        updateLabels();
    }
    private void updateLabels() {
        player1Label.setText("Player 1: " + formatTime(player1TimeLeft) + (isPlayer1Turn ? " ⏳" : ""));
        player2Label.setText("Player 2: " + formatTime(player2TimeLeft) + (!isPlayer1Turn ? " ⏳" : ""));
    }
    private String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}
