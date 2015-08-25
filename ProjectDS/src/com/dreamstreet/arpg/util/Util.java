package com.dreamstreet.arpg.util;

import com.dreamstreet.arpg.gfx.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 4/18/2015.
 */
public class Util {


    public static double findDistance(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static Direction findSlope(double x, double y, double dest_x, double dest_y)
    {
        double rise = dest_y - y;
        double run = dest_x - x;

        byte xdir = 0;
        double slope = 0;
        if (run == 0 && rise > 0) {
            slope = 200000;
            xdir = 0;
        }else if (run == 0 && rise < 0) {
            slope = -200000;
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

    public static double findX(double hypotenuse, double slope) {
        return Math.sqrt(Math.pow(hypotenuse, 2) / (1 + Math.pow(slope, 2)));
    }





    // polynomial "cosine and sine" funct to speed up calcs
}
