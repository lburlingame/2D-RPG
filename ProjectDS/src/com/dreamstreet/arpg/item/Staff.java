package com.dreamstreet.arpg.item;

import com.dreamstreet.arpg.gfx.*;
import com.dreamstreet.arpg.util.Util;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 3/26/2015.
 */
public class Staff {

    // item graphic, item icon graphic
    private boolean available = true;
    private boolean charging = false;
    private int cooldown = 10;
    private int tick = 0;

    private static final int MAX_POWER = 4;
    private float current_power = 0;
    private double speed = 10;//0
    private double base_radius = 1;  // 1

    private ArrayList<Nova> novas = new ArrayList();
    private Nova current = null;

    public Staff() {

    }

    public int use(Vector3 loc) {
        if (available) {
            current = new Nova(new Vector3(loc.x +(base_radius), loc.y + (base_radius), loc.z),0,0,base_radius, 18);
            novas.add(current);
            current_power = 0;
            available = false;
            return -1;
        }
        return tick;
    }

    public void tick() {
        if (!available && !charging) {
            tick++;
            if (tick == cooldown) {
                tick = 0;
                available = true;
            }
        }

        if (charging && current_power < MAX_POWER) {
            current_power += .1;
            double bonus_radius = current_power * 10 * 2;// / 2;
            current.setRadius(base_radius + bonus_radius);

        }

        for (int i = 0; i < novas.size(); i++) {
            if (novas.get(i).getDuration() <= 0 && novas.get(i).isActive()) {
                novas.set(i, null);
                novas.remove(i);
                i--;
            }else{
                novas.get(i).tick();
            }
        }
    }

    public void draw(Graphics2D g, Camera camera) {
        for (int i = 0; i < novas.size(); i++) {
            novas.get(i).draw(g,camera);
        }
    }

    public ArrayList<Nova> getNovas() {
        return novas;
    }
}
