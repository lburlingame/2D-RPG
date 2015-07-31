package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 5/5/2015.
 */
public class DayCycle {


    private static BufferedImage SUN;
    private static SpriteSheet sunsheet = new SpriteSheet("/gui/sun_icon.png");

    private static BufferedImage MOON;
    private static SpriteSheet moonsheet = new SpriteSheet("/gui/moon_icon.png");

    private int flicker_timer = 0;
    private int flicker_duration = 16;
    private double light_distance = 10;

    private final float DAY_CYCLE = 2*24*60.0f; //*3
    private final float HOUR = DAY_CYCLE/24;
    private final float MINUTE = HOUR / 60;
    private int current_time = (int)(HOUR * 8);
    public static float max_darkness = 1f;
    public static final float pi = 3.14159f;

    private int radius;
    private float origin_x;
    private float origin_y;

    private float sun_angle = -(current_time/DAY_CYCLE * 2 * pi);
    private float moon_angle = -((current_time/DAY_CYCLE * 2 * pi) - pi);


    public String time = "";

    public DayCycle(int origin_x, int origin_y, int radius) {
        this.origin_x = origin_x;
        this.origin_y = origin_y;
        this.radius = radius;
        SUN = sunsheet.getSprite(0,0,32,32);
        MOON = moonsheet.getSprite(0,0,32,32);
    }

    public void tick() {
        flicker_timer++;
        if (flicker_timer == flicker_duration) {
            flicker_timer = 0;
            light_distance = Math.random()*1.5+6.5;
            flicker_duration = (int)(Math.random()*6) + 8;
        }

        current_time++;
        max_darkness = (float)((Math.cos(current_time/DAY_CYCLE * pi * 2))*1.5);
        if (max_darkness < 0) {
            max_darkness = 0;
        }else if (max_darkness > 1) {
            max_darkness = 1;
        }
        if (current_time == DAY_CYCLE) {
            current_time = 0;
        }
        time = (int)(current_time/HOUR) + ":";
        if ((int)((current_time % HOUR) / MINUTE) < 10) {
            time = time + "0";
        }
        time = time + (int)((current_time % HOUR) / MINUTE);

        sun_angle = -(current_time/DAY_CYCLE * 2 * pi);// - pi / 10;
        moon_angle = -(current_time/DAY_CYCLE * 2 * pi) - pi;//* 9 / 10);
    }

    public void draw(Graphics g) {
        int sun_x = (int)(origin_x + Math.sin(sun_angle) * radius);
        int sun_y = (int)(origin_y + Math.cos(sun_angle) * radius);

        g.drawImage(SUN, sun_x, sun_y, 32, 32, null);

        int moon_x = (int)(origin_x + Math.sin(moon_angle) * radius);
        int moon_y = (int)(origin_y + Math.cos(moon_angle) * radius);

        g.drawImage(MOON, moon_x, moon_y, 32, 32, null);
        g.setColor(Color.white);
        g.fillOval((int)(origin_x+16),(int)(origin_y+16),2,2);
    }

    public double getLightDist() {
        return light_distance;
    }

    public float getMaxDarkness() {
        return max_darkness;
    }
}
