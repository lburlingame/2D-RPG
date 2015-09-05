package com.dreamstreet.arpg.input;


import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Vector2;

/**
 * Created on 5/2/2015.
 */
public class AIInput extends InputComponent {


    public AIInput(Game game) {
        super(game);
    }

    public void tick() {
        character.setDx(0);
        character.setDy(0);
    }

}
