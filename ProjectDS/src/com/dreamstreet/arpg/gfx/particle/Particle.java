package com.dreamstreet.arpg.gfx.particle;

import com.dreamstreet.arpg.gfx.Camera;
import com.dreamstreet.arpg.gfx.Vector3;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 5/5/2015.
 */
public abstract class Particle {

    protected BufferedImage img;

    protected Vector3 pos;
    protected Vector3 v;
    protected int duration;

    public Particle(Vector3 pos, Vector3 v) {
        this.pos = pos;
        this.v = v;
    }

    public abstract void tick();

    public abstract void draw(Graphics g, Camera camera);

    public int getDuration() {
        return duration;
    }

}
