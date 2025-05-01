public class Cell {
    private Ship ship;
    private boolean hit;

    public boolean hasShip() {
        return ship != null;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean hit() {
        hit = true;
        if (ship != null) {
            ship.hit();
            return true;
        }
        return false;
    }

    public Ship getShip() {
        return ship;
    }
}