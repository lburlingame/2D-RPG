package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.gfx.Vector2;

import java.awt.*;

/**
 * Created on 8/28/2015.
 */
public class MenuButton {

    private Vector2 pos;
    private Vector2 dim;

    public MenuButton(Vector2 pos, Vector2 dim) {
        this.pos = pos;
        this.dim = dim;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRoundRect((int)pos.x, (int)pos.y, (int)dim.x, (int)dim.y, 3, 3);
    }

    public Vector2 getPosition() {
        return new Vector2(pos.x, pos.y);
    }

    public Vector2 getDimensions() {
        return new Vector2(dim.x, dim.y);
    }

    public boolean collision(Point point) {
        if ((point.getX() >= pos.x && point.getX() <= pos.x + dim.x)
            && (point.getY() >= pos.y && point.getY() <= pos.y + dim.y)) {
            return true;
        }
        return false;
    }



}
