package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Vector2;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 3/26/2015.
 */
public class ActionBar {

    private ArrayList<ActionButton> buttons = new ArrayList<>();

    public ActionBar() {
      /*  buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2-48*5.6, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2-48*4.6, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2-48*3.6, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2-48*2.6, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2+48*1.4, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2+48*2.4, Game.dimension.getHeight()-49)));*/

        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2-48*3, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2-48*2, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2-48*1, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2+48*1, Game.dimension.getHeight()-49)));
        buttons.add(new ActionButton(new Vector2(Game.dimension.getWidth()/2+48*2, Game.dimension.getHeight()-49)));

    }


    public void tick() {

    }

    public void draw(Graphics g) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(g);
        }
    }
}
