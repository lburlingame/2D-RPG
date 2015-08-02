package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Vector2;

/**
 * Created on 5/2/2015.
 */
public class NullInput extends InputComponent{

    public NullInput(Game game) {
        super(game);
    }

    @Override
    public void tick() {

    }

    @Override
    public Vector2 getScreenLoc() {
        return new Vector2(0,0);
    }
}
