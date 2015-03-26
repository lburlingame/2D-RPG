package com.dreamstreet.arpg.obj;

import java.awt.*;

/**
 * Created on 3/24/2015.
 */
public abstract class Entity {

    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    protected int spriteid;

    protected String name;

    protected double x;
    protected double y;

    protected double width;
    protected double height;

    protected double imgscale;

    public Entity(int spriteid, String name, double x, double y, double width, double height, double imgscale) {
        this.spriteid = spriteid;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imgscale = imgscale;
    }

    public abstract void draw(Graphics g);

}
