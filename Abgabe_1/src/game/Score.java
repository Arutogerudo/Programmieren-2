package game;
import java.awt.*;

public class Score {
    private GameView gameView;
    private Position position;
    private double speedInPixel;
    private double rotation;
    private double size;
    private double width;
    private double height;

    public Score(GameView gameView) {
        this.gameView = gameView;
        this.size = 30;
        this.width = 150;
        this.height = 33;
        position = new Position(GameView.WIDTH - width, 0);
        this.rotation = 0;
    }

    @Override
    public String toString(){
        return "Score: " + position;
    }

    /*void updatePosition(){
        position.right();
        rotation += 1;
    }*/

    public void addToCanvas() {
        gameView.addTextToCanvas("Objekt 3", position.getX(), position.getY(), size, true, Color.YELLOW, rotation);
    }
}
