package com.dreamstreet.arpg.item;

import com.dreamstreet.arpg.gfx.*;
import com.dreamstreet.arpg.obj.HitCircle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created on 4/18/2015.
 */
public class Fireball {

    private static BufferedImage FIREBALL;
    private static SpriteSheet spellsheet = new SpriteSheet("/effects/fireball.png");

    private Vector3 pos;

    private double dx;
    private double dy;

    private double radius;

    private int duration;
    private boolean active;

    private HitCircle hit;
    private ArrayList<Integer> hitids;

    public Fireball(Vector3 pos, double dx, double dy, double radius) {
        if (FIREBALL == null) {
            FIREBALL = spellsheet.getSprite(0,0,32,32);
        }
        this.pos = new Vector3(pos.x,pos.y,pos.z);
        this.dx = dx;
        this.dy = dy;
        this.radius = radius;
        this.duration = 180;
        active = false;

        hit = new HitCircle(new Vector3(0,0,0),radius / 2);
        hitids = new ArrayList<>(); // add casters hit id to this;
    }


    public void tick() {
        pos.x += dx;
        pos.y += dy;

        if (duration > 0 && active) {
            duration --;
        }
    }

    public void draw(Graphics g, Camera camera) {
        Vector3 offset = camera.getOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();


        g.drawImage(FIREBALL, (int)((pos.x-xOffset - radius)*scale), (int)((pos.y-yOffset- radius)*scale), (int)((radius *2)*scale), (int)((radius * 2)*scale), null);

        g.setColor(Color.green);

        g.drawOval((int)((pos.x + hit.getCenter().x - hit.getRadius() * 7 / 5 - xOffset) * scale), (int)((pos.y + hit.getCenter().y  - hit.getRadius() * 7 / 10 - yOffset) * scale), (int)(hit.getRadius() * 14 / 5 * scale), (int)(hit.getRadius() * 7 / 5 * scale));

    }

    public void shoot(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        this.active = true;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        hit.setRadius(radius / 2);
    }

    public int getDuration() {
        return duration;
    }

    public boolean isActive() {
        return active;
    }

    public HitCircle getHit() {
        return hit;
    }

    public double getX() {
        return pos.x;
    }

    public double getY() {
        return pos.y;
    }
}
