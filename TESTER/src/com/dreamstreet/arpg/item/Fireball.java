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

    private int duration;
    private boolean active;

    public Fireball(double x, double y, double dx, double dy, double radius) {
        if (FIREBALL == null) {
            FIREBALL = spellsheet.getSprite(0,0,32,32);
        }
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.radius = radius;
        this.duration = 180;
        active = false;
    }


    public void tick() {
        this.x += dx;
        this.y += dy;

        if (duration > 0 && active) {
            duration --;
        }
    }

    public void draw(Graphics g, Camera camera) {
        Vector2 offset = camera.getCartOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();


        g.drawImage(FIREBALL, (int)((x-xOffset - radius)*scale), (int)((y-yOffset- radius)*scale), (int)((radius *2)*scale), (int)((radius * 2)*scale), null);
    }

    public void shoot(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        this.active = true;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isActive() {
        return active;
    }

    public double getRadius() {
        return radius;
    }
}
