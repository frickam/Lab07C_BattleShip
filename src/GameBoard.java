import java.util.Random;

public class GameBoard {
    private final Cell[][] cells = new Cell[10][10];
    private final Ship[] ships = { new Ship(5), new Ship(4), new Ship(3), new Ship(3), new Ship(2) };

    public GameBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void initializeShips() {
        for (Ship ship : ships) {
            placeShip(ship);
        }
    }

    private void placeShip(Ship ship) {
        Random random = new Random();
        boolean placed = false;

        while (!placed) {
            boolean horizontal = random.nextBoolean();
            int row = random.nextInt(10);
            int col = random.nextInt(10);

            if (canPlaceShip(ship, row, col, horizontal)) {
                for (int i = 0; i < ship.getSize(); i++) {
                    if (horizontal) {
                        cells[row][col + i].setShip(ship);
                    } else {
                        cells[row + i][col].setShip(ship);
                    }
                }
                placed = true;
            }
        }
    }

    private boolean canPlaceShip(Ship ship, int row, int col, boolean horizontal) {
        if (horizontal) {
            if (col + ship.getSize() > 10) return false;
            for (int i = 0; i < ship.getSize(); i++) {
                if (cells[row][col + i].hasShip()) return false;
            }
        } else {
            if (row + ship.getSize() > 10) return false;
            for (int i = 0; i < ship.getSize(); i++) {
                if (cells[row + i][col].hasShip()) return false;
            }
        }
        return true;
    }

    public boolean isHit(int row, int col) {
        return cells[row][col].hit();
    }

    public boolean isShipSunk(int row, int col) {
        return cells[row][col].getShip().isSunk();
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) return false;
        }
        return true;
    }
}