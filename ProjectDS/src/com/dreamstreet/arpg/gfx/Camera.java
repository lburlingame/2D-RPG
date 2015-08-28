package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.obj.Entity;

/**
 * Created on 3/22/2015.
 */
public class Camera {

    private static final double MAX_SCALE = 6;
    private static final double MIN_SCALE = .25;//1;
    private static final double panspeed = 25;

    private int SCREEN_CENTER_X;
    private int SCREEN_CENTER_Y;

    private double xOffset;
    private double yOffset;
    private double zOffset;

    private Vector2 viewport;

    private double dx;
    private double dy;

    private double scale;
    private double zoom;

    private Entity target;

    public Camera(Entity target) {
        this.target = target;

        SCREEN_CENTER_X = Game.WIDTH * Game.SCALE / 2;
        SCREEN_CENTER_Y = Game.HEIGHT  * Game.SCALE / 2;

        viewport = new Vector2(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

        scale = 2.25;
        zoom = .125;

        xOffset = 0;
        yOffset = 0;
        dx = 0;
        dy = 0;
    }

    public void zoomIn() {
        if (scale + zoom < MAX_SCALE) {
            scale += zoom;
        }
    }

    public void zoomOut() {
        if (scale - zoom > MIN_SCALE) {
            scale -= zoom;
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
        this.xOffset = target.getX() - SCREEN_CENTER_X  / scale;
        this.yOffset = target.getY() - (SCREEN_CENTER_Y + target.getHeight()/4 * scale) / scale;
    }

    public void setTarget(Entity target) {
        this.target = target;
        zOffset = 0;//-target.getHeight();
        centerCamera();
    }




    public Vector3 getOffset() {
        return new Vector3(xOffset,yOffset,zOffset);
    }


    public void setDy(double dy) {
        this.dy = dy;
    }
    public void setDx(double dx) {
        this.dx = dx;
    }


    public void setScale(double scale) {
        if (scale <= MAX_SCALE || scale >= MIN_SCALE) {
            this.scale = scale;
        }
    }

    public double getScale() {
        return scale;
    }


    public double getWidth() { return viewport.x / scale; }
    public double getHeight() { return viewport.y / scale; }
}
