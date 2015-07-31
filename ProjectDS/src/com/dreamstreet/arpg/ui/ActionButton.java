package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 5/5/2015.
 */
public class ActionButton {

    private static BufferedImage EMPTY_BUTTON;
    private static SpriteSheet emptysheet = new SpriteSheet("/gui/action_button.png");

    public ActionButton() {
        if (EMPTY_BUTTON == null) {
            EMPTY_BUTTON = emptysheet.getSprite(0,0,48,48);
        }
    }

    public void tick() {

    }

    public void draw(Graphics g) {

    }
}
