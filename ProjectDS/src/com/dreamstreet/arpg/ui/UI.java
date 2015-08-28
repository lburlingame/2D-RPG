package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.SpriteSheet;
import com.dreamstreet.arpg.obj.Entity;
import com.dreamstreet.arpg.obj.EntityInfo;

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


    private SpriteSheet health_sheet = new SpriteSheet("/gui/health_globe.png");
    private BufferedImage healthglobe = health_sheet.getSprite(0,0,128,128);

    private SpriteSheet mana_sheet = new SpriteSheet("/gui/mana_globe2.png");
    private BufferedImage managlobe = mana_sheet.getSprite(0,0,128,128);

    private SpriteSheet globe_sheet = new SpriteSheet("/gui/globe_base.png");
    private BufferedImage baseglobe = globe_sheet.getSprite(0,0,128,128);

    private SpriteSheet coin_sheet = new SpriteSheet("/gui/coin_icon.png");
    private BufferedImage coin = coin_sheet.getSprite(0,0,32,32);

    private ActionBar ab = new ActionBar();

    private Entity character;

    public int x;
    public int x2;
    public int y;
    public double currhealth;
    public double maxhealth;

    public double currmana;
    public double maxmana;

    public UI(Entity character) {
        this.character = character;

        x = (int)Game.dimension.getWidth()/2 - 68;
        y = (int)Game.dimension.getHeight();

        x2 = (int)Game.dimension.getWidth()/2 + 48*3;
        currhealth = 1000;
        maxhealth = 1000;

        currmana = 1000;
        maxmana = 1000;
    }


    public void tick() {

    }

    public void draw(Graphics g) {
        EntityInfo info = character.getInfo();

        g.drawImage(healthglobe,x,y-(int)((double)info.getCurrHealth()/info.getMaxHealth()*128),x+128,y,0,128-(int)((double)info.getCurrHealth()/info.getMaxHealth()*128),128,128,null);
        g.drawImage(managlobe,x,y-(int)(currmana/maxmana*128),x+128,y,0,128-(int)(currmana/maxmana*128),128,128,null);
        g.drawImage(baseglobe,x,y-128,128,128,null);

        g.drawImage(coin,(int)(Game.dimension.getWidth() - 128), (int)(Game.dimension.getHeight() - 48), 32, 32,null);
        g.setColor(Color.yellow);
        g.drawString(character.getGold() + " ", (int)(Game.dimension.getWidth() - 96), (int)(Game.dimension.getHeight() - 28));
        ab.draw(g);
    }



}
    /*


     */
