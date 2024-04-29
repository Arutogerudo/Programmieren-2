package game;

public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position() {
        this(0, 0);
    }

    public Position(Position otherPosition) {
        this(otherPosition.getX(), otherPosition.getY());
    }

    public void left() {
        x -= 1;
    }

    public void left(double pixel) {
        x -= pixel;
    }

    public void right() {
        x += 1;
    }

    public void right(double pixel) {
        x += pixel;
    }

    public void up() {
        y -= 1;
    }

    public void up(double pixel) {
        y -= pixel;
    }

    public void down() {
        y += 1;
    }

    public void down(double pixel) {
        y += pixel;
    }

    @Override
    public String toString() {
        return "Position (" + ((int) Math.round(x)) + ", " + ((int) Math.round(y)) + ")";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void updateCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void updateCoordinates(Position otherPosition) {
        this.x = otherPosition.getX();
        this.y = otherPosition.getY();
    }
}
