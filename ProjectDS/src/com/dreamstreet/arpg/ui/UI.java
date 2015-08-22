package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 3/23/2015.
 */
public class UI {

    private SpriteSheet healthsheet = new SpriteSheet("/gui/health_orb.png");
    private BufferedImage healthorb = healthsheet.getSprite(0,0,64,64);

    private SpriteSheet manasheet = new SpriteSheet("/gui/mana_orb.png");
    private BufferedImage manaorb = manasheet.getSprite(0,0,64,64);

    private SpriteSheet orbsheet = new SpriteSheet("/gui/orb_base.png");
    private BufferedImage baseorb = orbsheet.getSprite(0,0,64,64);

    private ActionBar ab = new ActionBar();

    public int x;
    public int x2;
    public int y;
    public double currhealth;
    public double maxhealth;

    public double currmana;
    public double maxmana;

    public UI() {
        x = (int)Game.dimension.getWidth()/2 - 48*3-128;
        y = (int)Game.dimension.getHeight();

        x2 = (int)Game.dimension.getWidth()/2 + 48*3;
        currhealth = 1000;
        maxhealth = 1000;

        currmana = 1000;
        maxmana = 1000;
    }


    public void tick() {
        if (currhealth > 0){
            currhealth -= 2;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(healthorb,x,y-(int)(currhealth/maxhealth*128),x+128,y,0,64-(int)(currhealth/maxhealth*64),64,64,null);
        g.drawImage(baseorb,x-1,y-129,130,130,null);

        g.drawImage(manaorb,x2,y-(int)(currmana/maxmana*128),x2+129,y,0,64-(int)(currmana/maxmana*64),64,64,null);
        g.drawImage(baseorb,x2,y-129,130,130,null);

        ab.draw(g);
    }


}
