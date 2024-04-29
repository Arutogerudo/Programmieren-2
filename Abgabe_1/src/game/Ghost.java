package game;

import java.awt.*;

public class Ghost {
    private GameView gameView;
    private Position position;
    private double speedInPixel;
    private double rotation;
    private double size;
    private double width;
    private double height;

    public Ghost(GameView gameView) {
        this.gameView = gameView;
        this.size = 30;
        position = new Position(0, GameView.HEIGHT / 2);
        speedInPixel = 5;
        this.rotation = 0;
        this.width = 150;
        this.height = 33;
    }

    @Override
    public String toString() {
        return "Ghost: " + position;
    }

    public void updatePosition() {
        position.right(speedInPixel);
        rotation += 1;
    }

    public void addToCanvas() {
        gameView.addTextToCanvas("Objekt 1", position.getX(), position.getY(), size, true, Color.YELLOW, rotation);
    }
}
