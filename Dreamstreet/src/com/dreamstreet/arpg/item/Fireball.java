package com.dreamstreet.arpg.item;

import com.dreamstreet.arpg.gfx.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 4/18/2015.
 */
public class Fireball {

    private static BufferedImage FIREBALL;
    private static SpriteSheet spellsheet = new SpriteSheet("/effects/fireball.png");

    private double x;
    private double y;

    private double dx;
    private double dy;

    private double radius;

    public Fireball(double x, double y, double dx, double dy, double radius) {
        if (FIREBALL == null) {
            FIREBALL = spellsheet.getSprite(0,0,32,32);
        }
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.radius = radius;
    }


    public void tick() {
        this.x += dx;
        this.y += dy;
    }

    public void draw(Graphics g, Camera camera) {
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        Vector2 iso = IsoCalculator.twoDToIso(new Vector3(x, y, 0));


        g.drawImage(FIREBALL, (int)((iso.x-xOffset - radius)*scale), (int)((iso.y-yOffset- radius)*scale), (int)((radius *2)*scale), (int)((radius * 2)*scale), null);
    }


    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
