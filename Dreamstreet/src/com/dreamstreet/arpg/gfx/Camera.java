package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;

/**
 * Created on 3/22/2015.
 */
public final class Camera {

    private static final double MAX_SCALE = 6;
    private static final double MIN_SCALE = .3;
    private static final double panspeed = 25;

    private int SCREEN_CENTER_X;
    private int SCREEN_CENTER_Y;

    private double xOffset;

    private double yOffset;
    private double dx;
    private double dy;

    private double scale;
    private double zoom;

    public Camera(double scale, double zoom) {
        SCREEN_CENTER_X = Game.WIDTH * Game.SCALE / 2;
        SCREEN_CENTER_Y = Game.HEIGHT  * Game.SCALE / 2;

        xOffset = -5;
        yOffset = -5;
        dx = 0;
        dy = 0;

        this.scale = scale;
        this.zoom = zoom;
    }

    public void zoomIn() {
        if (scale+zoom*scale < MAX_SCALE) {
            scale += zoom * scale;
        }else{
            scale = MAX_SCALE;
        }
    }

    public void zoomOut() {
        if (scale-zoom*scale > MIN_SCALE) {
            scale -= zoom * scale;
        }else{
            scale = MIN_SCALE;
        }
    }

    public void tick() {
        xOffset += dx;
        yOffset += dy;
    }

    public void centerCamera(double x, double y, double width, double height) {
        this.xOffset =  x - ((SCREEN_CENTER_X - width * scale) / scale);
        this.yOffset =  y;
    }

    public void stop() {
        dx = 0;
        dy = 0;
    }

    public Vector2 getIsoOffset() {
        return IsoCalculator.twoDToIso(new Vector2(xOffset,yOffset));
    }

    public Vector2 getCartOffset() {
        return new Vector2(xOffset,yOffset);
    }


    public void setDy(double dy) {
        this.dy = dy;
    }
    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getScale() {
        return scale;
    }
    public double getyOffset() {
        return yOffset;
    }

    public double getxOffset() {
        return xOffset;
    }
    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }
}
