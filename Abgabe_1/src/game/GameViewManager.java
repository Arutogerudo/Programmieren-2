package game;

import java.awt.*;

public class GameViewManager extends GameView {
    Ghost ghost;
    Spion spion;
    Score score;
    @Override
    public void initialize() {
        ghost = new Ghost(this);
        spion = new Spion(this);
        score = new Score(this);
    }
    @Override
    public void gameLoop() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        ghost.updatePosition();
        ghost.addToCanvas();
        spion.updatePosition();
        spion.addToCanvas();
        score.addToCanvas();
    }
}
