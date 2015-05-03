package com.dreamstreet.arpg.gfx;

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

    public static BufferedImage toCompatibleImage(BufferedImage image)
    {
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

	/*
	 * if image is already compatible and optimized for current system
	 * settings, simply return it
	 */
        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }



    // polynomial "cosine and sine" funct to speed up calcs
}
