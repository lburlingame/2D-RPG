package com.dreamstreet.arpg.obj;

import com.dreamstreet.arpg.gfx.Vector2;

/**
 * Created on 5/9/2015.
 */
public class HitCircle {

    private Vector2 center;
    private double radius;

    public HitCircle(Vector2 center, double radius) {
        this.center = center;
        this.radius = radius;
        System.out.println(radius);
    }

    public Vector2 getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}
