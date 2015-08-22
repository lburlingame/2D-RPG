package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Vector2;
import com.dreamstreet.arpg.obj.Entity;


/**
 * Created on 3/26/2015.
 */
public abstract class InputComponent {

    protected Game game;
    protected Entity character;

    public InputComponent(Game game) {
        this.game = game;
    }

    public abstract void tick();

    public void setCharacter(Entity character) {
        this.character = character;
    }

    public abstract Vector2 getScreenLoc();
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
