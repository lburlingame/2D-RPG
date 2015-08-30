package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Camera;
import com.dreamstreet.arpg.gfx.Vector2;
import com.dreamstreet.arpg.gfx.Vector3;
import com.dreamstreet.arpg.obj.Entity;
import com.dreamstreet.arpg.screen.GameScreen;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 3/26/2015.
 */
public class PlayerInput2 extends InputComponent {

    private Entity character;
    private InputHandler input;

    private boolean clicked = false;
    private boolean stopped = false;

    public PlayerInput2(Game game) {
        super(game);

        this.input = game.getInput();
    }

    public void tick() {

        // TODO MAKE IT SO THE MOST RECENTLY ACTIVATED KEY GETS PRIORITY
        if (input.W.isPressed()) {
            character.setDy(-3);
        }else{
            if (input.S.isPressed()) {
                character.setDy(3);
            }else if (!input.S.isPressed()) {
                character.setDy(0);
            }
        }

        if (input.A.isPressed()) {
            character.setDx(-3);
        }else if (!input.A.isPressed() ) {
            if (input.D.isPressed()) {
                character.setDx(3);
            }else if (!input.D.isPressed()) {
                character.setDx(0);
            }
        }

        if (input.SPACE.isPressed() && !input.SPACE.getPrevious()) {
            character.jump();
        }

        if (clicked && !stopped) {

        }
    }


    public void setCharacter(Entity character) {
        this.character = character;
    }

}



    /*public class Key {
        private boolean pressed = false;
        protected void toggle(boolean isPressed) {
            pressed = isPressed;
        }

        public boolean isPressed() {
            return pressed;
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key s = new Key();

    public boolean leftClicked = false;
    public boolean rightClicked = false;
    public int wheelnotches = 0;

    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_S) {
            s.toggle(isPressed);
        }
    }*/
