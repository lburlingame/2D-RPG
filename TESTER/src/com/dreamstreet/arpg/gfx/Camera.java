package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;

/**
 * Created on 3/22/2015.
 */
public class Camera {

    private static final double MAX_SCALE = 12;
    private static final double MIN_SCALE = .05;//1;
    private static final double panspeed = 25;

    private int SCREEN_CENTER_X;
    private int SCREEN_CENTER_Y;

    private double xOffset;

    private double yOffset;
    private double dx;
    private double dy;

    private double scale;
    private double zoom;

    private Sprite target;

    public Camera(Sprite target) {
        this.target = target;

        SCREEN_CENTER_X = Game.WIDTH * Game.SCALE / 2;
        SCREEN_CENTER_Y = Game.HEIGHT  * Game.SCALE / 2;

        scale = 2.5;
        zoom = .1;

        xOffset = 0;
        yOffset = 0;
        dx = 0;
        dy = 0;
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
        /*
        dx = target.getDx();
        dy = target.getDy();

        xOffset += dx;
        yOffset += dy;*/
        centerCamera();
    }

    public void centerCamera() {
        this.xOffset =  target.getX() - ((SCREEN_CENTER_X - target.getWidth()/2 * scale) / scale);
        this.yOffset =  target.getY() - ((SCREEN_CENTER_Y - target.getHeight()/2 * scale) / scale);
    }

    public void setTarget(Sprite target) {
        this.target = target;
    }



    public Vector2 getIsoOffset() {
        return IsoCalculator.twoDToIso(new Vector3(xOffset,yOffset, 0));
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
