package com.dreamstreet.arpg.exclude;

import com.dreamstreet.arpg.gfx.Direction;
import com.dreamstreet.arpg.obj.Entity;

/**
 * Created on 3/26/2015.
 */
public abstract class Mob extends Entity {

    protected double dest_x;
    protected double dest_y;

    protected double dx;
    protected double dy;

    protected double velocity;

    public Mob(int spriteid, String name, double x, double y, double width, double height, double imgscale, double velocity) {
        super(spriteid, name, x, y, width, height, imgscale);
        this.velocity = velocity;
        this.dest_x = x;
        this.dest_y = y;
        this.dx = 0;
        this.dy = 0;
    }

    public void tick() {
        x += dx;
        y += dy;
    }


    public void move(double dest_x, double dest_y)
    {
        this.dest_x = dest_x - (width - 17) * imgscale;
        this.dest_y = dest_y - (height - 4) * imgscale;

        Direction dir = findSlope();

        dx = findDX(dir.slope) * dir.xdir;
        dy = dir.slope * dx;


        if (dir.slope == 2000 || dir.slope == -2000)
        {
            dy = velocity * dir.slope / Math.abs(dir.slope);
        }
    }

    private Direction findSlope()
    {
        double rise = dest_y - y;
        double run = dest_x - x;

        byte xdir = 0;
        double slope = 0;
        if (run == 0 && rise > 0) {
            slope = 2000;
            xdir = 0;
        }else if (run == 0 && rise < 0) {
            slope = -2000;
            xdir = 0;
        }else if (run != 0) {
            slope = rise / run;
            if (run > 0) {
                xdir = 1;
            }else{
                xdir = -1;
            }
        }

        return new Direction(xdir, slope);
    }

    public double findDX(double slope) {
        return Math.sqrt(Math.pow(velocity, 2) / (1 + Math.pow(slope, 2)));
    }
}
