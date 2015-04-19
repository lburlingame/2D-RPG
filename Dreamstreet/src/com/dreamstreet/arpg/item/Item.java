package com.dreamstreet.arpg.item;

import com.dreamstreet.arpg.gfx.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 3/26/2015.
 */
public class Item {


    private boolean available = true;
    private boolean charging = false;
    private int cooldown = 30;
    private int tick = 0;


    private static final int MAX_POWER = 120;
    private float current_power = 0;
    private double speed = 4;
    private double base_radius = 20;

    private ArrayList<Fireball> fireballs = new ArrayList();
    private Fireball current = null;

    public Item() {

    }

    public int charge(Vector2 loc) {
        if (available) {
            current = new Fireball(loc.x +(base_radius), loc.y + (base_radius),0,0,base_radius);
            fireballs.add(current);
            current_power = 0;
            charging = true;
            available = false;


            return -1;
        }
        return tick;
    }

    public void use(Vector2 loc, Vector2 dest) {
        if (charging) {
            charging = false;
            double bonus_speed = current_power / 12;

            Direction dir = Util.findSlope(loc.x + 16, loc.y + 16, dest.x, dest.y);

            double dx = Util.findDX(speed + bonus_speed, dir.slope) * dir.xdir;
            double dy = dir.slope * dx;

            if (dir.slope == 200000 || dir.slope == -200000)
            {
                dy = speed * dir.slope / Math.abs(dir.slope);
            }

            current.setDx(dx);
            current.setDy(dy);
        }

    }

    public void tick() {
        if (!available && !charging) {
            tick++;
            System.out.println(tick);
            if (tick == cooldown) {
                tick = 0;
                available = true;
            }
        }

        if (charging && current_power < MAX_POWER) {
            current_power += 1;
            double bonus_radius = current_power / 2;
            current.setRadius(base_radius + bonus_radius);

            System.out.println(current_power);
        }

        for (int i = 0; i < fireballs.size(); i++) {
            fireballs.get(i).tick();
        }
    }

    public void draw(Graphics g, Camera camera) {
        for (int i = 0; i < fireballs.size(); i++) {
            fireballs.get(i).draw(g,camera);
        }
    }

}
