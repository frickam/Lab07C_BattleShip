import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI {
    private JFrame frame;
    private JButton[][] boardButtons = new JButton[10][10];
    private JLabel missCounterLabel, strikeCounterLabel, totalMissLabel, totalHitLabel;
    private GameBoard gameBoard;
    private int missCounter = 0, strikeCounter = 0, totalMiss = 0, totalHit = 0;

    public void createAndShowGUI() {
        frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create Game Board
        JPanel boardPanel = new JPanel(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton();
                button.setBackground(Color.LIGHT_GRAY);
                button.addActionListener(new CellClickListener(i, j));
                boardButtons[i][j] = button;
                boardPanel.add(button);
            }
        }

        // Create Status Panel
        JPanel statusPanel = new JPanel();
        missCounterLabel = new JLabel("MISS Counter: 0");
        strikeCounterLabel = new JLabel("STRIKE Counter: 0");
        totalMissLabel = new JLabel("TOTAL MISS Counter: 0");
        totalHitLabel = new JLabel("TOTAL HIT Counter: 0");

        statusPanel.add(missCounterLabel);
        statusPanel.add(strikeCounterLabel);
        statusPanel.add(totalMissLabel);
        statusPanel.add(totalHitLabel);

        // Create Control Panel
        JPanel controlPanel = new JPanel();
        JButton playAgainButton = new JButton("Play Again");
        JButton quitButton = new JButton("Quit");

        playAgainButton.addActionListener(e -> resetGame());
        quitButton.addActionListener(e -> quitGame());

        controlPanel.add(playAgainButton);
        controlPanel.add(quitButton);

        // Add Panels to Frame
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Initialize Game Logic
        gameBoard = new GameBoard();
        gameBoard.initializeShips();

        frame.pack();
        frame.setVisible(true);
    }

    private void resetGame() {
        int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to start a new game?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose();
            createAndShowGUI();
        }
    }

    private void quitGame() {
        int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private class CellClickListener implements ActionListener {
        private int row, col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = boardButtons[row][col];
            if (gameBoard.isHit(row, col)) {
                button.setText("X");
                button.setBackground(Color.RED);
                totalHit++;
                missCounter = 0;
                totalHitLabel.setText("TOTAL HIT Counter: " + totalHit);
                if (gameBoard.isShipSunk(row, col)) {
                    JOptionPane.showMessageDialog(frame, "You sunk a ship!");
                }
                if (gameBoard.allShipsSunk()) {
                    JOptionPane.showMessageDialog(frame, "You won!");
                    resetGame();
                }
            } else {
                button.setText("M");
                button.setBackground(Color.YELLOW);
                missCounter++;
                totalMiss++;
                missCounterLabel.setText("MISS Counter: " + missCounter);
                totalMissLabel.setText("TOTAL MISS Counter: " + totalMiss);
                if (missCounter == 5) {
                    strikeCounter++;
                    missCounter = 0;
                    strikeCounterLabel.setText("STRIKE Counter: " + strikeCounter);
                    if (strikeCounter == 3) {
                        JOptionPane.showMessageDialog(frame, "You lost!");
                        resetGame();
                    }
                }
            }
            button.setEnabled(false);
        }
    }
}