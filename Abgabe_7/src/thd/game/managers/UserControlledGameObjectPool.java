package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

import java.awt.event.KeyEvent;

class UserControlledGameObjectPool {
    private boolean wasdKey;
    protected final GameView gameView;
    Tank tank;
    Scoreboard scoreboard;
    UserControlledGameObjectPool(GameView gameView){
        this.gameView = gameView;
    }
    protected void gameLoopUpdate() {
        Integer[] pressedKeys = gameView.keyCodesOfCurrentlyPressedKeys();
        wasdKey = false;
        for (int keyCode:pressedKeys){
            processKeyCode(keyCode);
        }
    }
    private void processKeyCode(int keyCode) {
        if (keyCode == KeyEvent.VK_A && !wasdKey) {
            wasdKey = true;
            tank.left();
        } else if (keyCode == KeyEvent.VK_D && !wasdKey) {
            wasdKey = true;
            tank.right();
        } else if (keyCode == KeyEvent.VK_W && !wasdKey) {
            wasdKey = true;
            tank.up();
        } else if (keyCode == KeyEvent.VK_S && !wasdKey) {
            wasdKey = true;
            tank.down();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            tank.shoot();
        }
    }
}
