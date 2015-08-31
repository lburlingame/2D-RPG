package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Camera;
import com.dreamstreet.arpg.screen.GameScreen;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 8/24/2015.
 */

/*

    Refactor into only one Input class that has listeners, and this class will instead toggle Key class booleans true/false, along with previous Key class
    these Key classes will then be read in playerinput class, so that this way there will only need to be one class implementing KeyListeners
 */
public class WindowInput {

    private GameScreen screen;
    private Game game;
    private InputHandler input;
    private Camera camera;

    public WindowInput(Game game, GameScreen screen, Camera camera) {
        this.game = game;
        this.screen = screen;
        this.camera = camera;
        this.input = game.getInput();
    }


    public void tick() {
        if (input.ESC.isPressed()) {
            game.openMenu();
        }

        if (!input.I.isPressed() && input.I.getPrevious()) {
            Game.debug = !Game.debug;
        }

        if (input.P.isPressed() && !input.P.getPrevious()) {
            screen.pause = !screen.pause;
        }

        if (!input.L.isPressed() && input.L.getPrevious()) {
            screen.showhealth = !screen.showhealth;
        }

        int notches = input.wheelnotches;
        if (notches < 0) {
            camera.zoomIn();
        }else if (notches > 0){
            camera.zoomOut();
        }
        camera.centerCamera();
    }



        /*switch(e.getKeyCode())
        {
            case KeyEvent.VK_M:
                if (screen.audioPlay) {
                    screen.music.stop();
                }else{
                    screen.music.start();
                }
                screen.audioPlay = !screen.audioPlay;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                break;
            case KeyEvent.VK_P:
                Game.pause = !Game.pause;
                break;
            case KeyEvent.VK_I:
                Game.debug = !Game.debug;
                break;
            case KeyEvent.VK_L:
                screen.showhealth = !screen.showhealth;
                break;
        }
    }*/

    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            camera.zoomIn();
        } else {
            camera.zoomOut();
        }
        camera.centerCamera();
    }


}
