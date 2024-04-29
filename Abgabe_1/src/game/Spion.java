package game;
import java.awt.*;

public class Spion {
    private GameView gameView;
    private Position position;
    private double speedInPixel;
    private double rotation;
    private double size;
    private double width;
    private double height;

    public Spion(GameView gameView) {
        this.gameView = gameView;
        this.size = 30;
        position = new Position(1100, 650);
        speedInPixel = 2;
        this.rotation = 0;
        this.width = 150;
        this.height = 33;
    }

    @Override
    public String toString(){
        return "Spion: " + position;
    }

    public void updatePosition(){
        position.left(speedInPixel);
    }

    public void addToCanvas() {
        gameView.addRectangleToCanvas(position.getX(), position.getY(), 150, 40, 0, true, Color.GREEN);
        gameView.addRectangleToCanvas(position.getX(), position.getY(), 150, 40, 5, false, Color.WHITE);
        gameView.addTextToCanvas("Objekt 2", position.getX()+3, position.getY(), size, true, Color.BLUE, rotation);
    }

}
