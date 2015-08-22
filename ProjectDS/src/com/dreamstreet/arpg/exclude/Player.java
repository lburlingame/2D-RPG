package com.dreamstreet.arpg.exclude;

import com.dreamstreet.arpg.exclude.Mob;
import com.dreamstreet.arpg.gfx.Camera;
import com.dreamstreet.arpg.input.InputComponent;

import java.awt.*;

/**
 * Created on 3/24/2015.
 */
public class Player extends Mob {

    private InputComponent input;
    private Camera camera;

    private boolean stopped = false;

    public Player(int spriteid, String name, double x, double y, double width, double height, double imgscale, double velocity, InputComponent input) {
        super(spriteid, name, x, y, width, height, imgscale, velocity);
        this.input = input;
    }

/*
    @Override
    public void tick() {
        super.tick();
        if (input != null) {
            if (input.leftClicked && !stopped) {
                Point mLoc = MouseInfo.getPointerInfo().getLocation();
                Point frameLoc = input.getLocationOnScreen();
                mLoc.x -= frameLoc.x;
                mLoc.y -= frameLoc.y;
            //    move(mLoc.getX() / camera.getScale() + camera.getXOffset(), mLoc.getY() / camera.getScale() + camera.getYOffset());
            }

            if (input.wheelnotches < 0) {
                camera.zoomIn();
            }else if(input.wheelnotches > 0) {
                camera.zoomOut();
            }
        }
    }*/

    @Override
    public void draw(Graphics g) {

    }



    public void stop() {
        dest_x = x;
        dest_y = y;
        dx = 0;
        dy = 0;
    }
}
