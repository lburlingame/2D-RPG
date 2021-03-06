package com.dreamstreet.arpg.item;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.*;
import com.dreamstreet.arpg.obj.Collidable;
import com.dreamstreet.arpg.obj.HitCircle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created on 4/18/2015.
 */
public class Fireball extends Collidable {

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

        hit = new HitCircle(new Vector3(0,0,0), radius * .6);
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
        double scale = camera.getScale();


        g.drawImage(FIREBALL, (int) ((pos.x - offset.x - radius) * scale), (int) ((pos.y - offset.y - radius + pos.z) * scale), (int) ((radius * 2) * scale), (int) ((radius * 2) * scale), null);

        if (Game.debug) {
            g.setColor(Color.green);
            g.drawOval((int)((pos.x + hit.getCenter().x - hit.getRadius() - offset.x) * scale), (int)((pos.y + hit.getCenter().y  - hit.getRadius()- offset.y) * scale), (int)(hit.getRadius() * scale * 2), (int)(hit.getRadius() * scale * 2));
        }


    }

    public void shoot(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        this.active = true;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        hit.setRadius(radius * .6);
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


    public Vector3 getPosition() {
        return new Vector3(pos.x,pos.y,pos.z);
    }


    public double getX() {
        return pos.x;
    }

    public double getY() {
        return pos.y;
    }

    public double getRadius() {
        return radius;
    }

    public boolean collidesWith(Collidable other) {
        return false;
    }
}
