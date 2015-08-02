package com.dreamstreet.arpg.input;


import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Vector2;

/**
 * Created on 5/2/2015.
 */
public class NPCInput extends InputComponent {


    public NPCInput(Game game) {
        super(game);
    }

    public void tick() {
        //character.move(0,0);
    }

    @Override
    public Vector2 getScreenLoc() {
        return new Vector2(0,0);
    }
}
