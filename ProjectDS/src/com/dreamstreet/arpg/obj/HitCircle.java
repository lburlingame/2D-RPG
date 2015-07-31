package com.dreamstreet.arpg.obj;


import com.dreamstreet.arpg.gfx.Vector3;

import java.awt.*;

/**
 * Created on 5/9/2015.
 */
public class HitCircle {

    private Vector3 center;
    private double radius;

    public HitCircle(Vector3 center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Vector3 getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
