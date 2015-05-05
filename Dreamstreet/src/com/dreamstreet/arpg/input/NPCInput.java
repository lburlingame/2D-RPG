package com.dreamstreet.arpg.input;


import com.dreamstreet.arpg.Game;

/**
 * Created on 5/2/2015.
 */
public class NPCInput extends InputComponent {


    public NPCInput(Game game) {
        super(game);
    }

    public void tick() {
        character.move(0,0);
    }
}
