package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

import java.awt.*;
import java.awt.event.KeyEvent;

class UserControlledGameObjectPool {
    private boolean diagonal;
    protected final GameView gameView;
    protected Ghost ghost;
    protected Spy spy;
    protected RadioactivePack radioactivePack;
    protected Tank tank;
    protected Accordion accordion;
    protected Jet jet;
    protected Rocket rocket;
    protected StartRamp startRamp;
    protected Projectile projectile;
    UserControlledGameObjectPool(GameView gameView){
        this.gameView = gameView;
        diagonal = false;
    }
    protected void gameLoopUpdate() {
        Integer[] pressedKeys = gameView.keyCodesOfCurrentlyPressedKeys();
        if (diagonal){
            for (int keyCode:pressedKeys){
                processKeyCode(keyCode);
            }
        } else if (pressedKeys.length != 0) {
            processKeyCode(pressedKeys[0]);
        }
    }
    private void processKeyCode(int keyCode) {
        if (keyCode == KeyEvent.VK_A) {
            tank.left();
        } else if (keyCode == KeyEvent.VK_D) {
            tank.right();
        } else if (keyCode == KeyEvent.VK_W) {
            tank.up();
        } else if (keyCode == KeyEvent.VK_S) {
            tank.down();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            tank.shoot();
        }
    }
}
