import javax.swing.*;

public class BattleshipGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameUI gameUI = new GameUI();
            gameUI.createAndShowGUI();
        });
    }
}