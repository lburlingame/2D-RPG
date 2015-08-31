package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.gfx.SpriteSheet;
import com.dreamstreet.arpg.gfx.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 8/28/2015.
 */
public class MenuButton {

    private Vector2 pos;
    private Vector2 dim;
    private BufferedImage image;
    //boolean popped

    public MenuButton(Vector2 pos, Vector2 dim, String path) {
        this.pos = pos;
        this.dim = dim;
        image = new SpriteSheet(path).getSprite(0,0,300,75);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.drawImage(image, (int)pos.x, (int)pos.y, (int)dim.x, (int)dim.y, null);
    }

    public Vector2 getPosition() {
        return new Vector2(pos.x, pos.y);
    }

    public Vector2 getDimensions() {
        return new Vector2(dim.x, dim.y);
    }

    public boolean clicked(Point point) {
        if ((point.getX() >= pos.x && point.getX() <= pos.x + dim.x)
            && (point.getY() >= pos.y && point.getY() <= pos.y + dim.y)) {
            return true;
        }
        return false;
    }


    public void pop() {
        dim.x += 50;
        dim.y += 20;
        pos.x -= 25;
        pos.y -= 10;
    }
}
