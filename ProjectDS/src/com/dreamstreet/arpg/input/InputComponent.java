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

}
