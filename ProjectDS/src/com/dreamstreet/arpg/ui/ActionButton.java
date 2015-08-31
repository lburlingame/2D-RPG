package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.gfx.SpriteSheet;
import com.dreamstreet.arpg.gfx.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 5/5/2015.
 */
public class ActionButton {

    private static BufferedImage EMPTY_BUTTON;
    // flip light angle
    private static SpriteSheet emptysheet = new SpriteSheet("/gui/action_button.png");

    public Vector2 pos;

    public ActionButton(Vector2 pos) {
        if (EMPTY_BUTTON == null) {
            EMPTY_BUTTON = emptysheet.getSprite(0,0,48,48);
        }

        this.pos = new Vector2(pos.x, pos.y);
    }

    public void tick() {

    }

    public void draw(Graphics g) {
        g.drawImage(EMPTY_BUTTON, (int)pos.x, (int)pos.y, 48,48, null);
    }
}
