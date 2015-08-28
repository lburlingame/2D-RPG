package com.dreamstreet.arpg.gfx.particle;

import com.dreamstreet.arpg.gfx.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created on 5/5/2015.
 */
public class BloodParticle extends Particle {


    private static ArrayList<BufferedImage> images;
    private static SpriteSheet part1sheet = new SpriteSheet("/effects/blood_spat1.png");
    private static SpriteSheet part2sheet = new SpriteSheet("/effects/blood_spat2.png");
    private static SpriteSheet part3sheet = new SpriteSheet("/effects/blood_spat3.png");
    private static SpriteSheet part4sheet = new SpriteSheet("/effects/blood_spat4.png");
    private static SpriteSheet part5sheet = new SpriteSheet("/effects/blood_spat5.png");
    private int id;
    // combine into 1 sheet

    public BloodParticle(Vector3 pos, Vector3 v) {
        super(pos, v);
        duration = (int)((Math.random() * 240) + 240);

        if (images == null) {
            images = new ArrayList<>();
            images.add(part1sheet.getSprite(0,0,16,16));
            images.add(part2sheet.getSprite(0,0,16,16));
            images.add(part3sheet.getSprite(0,0,16,16));
            images.add(part4sheet.getSprite(0,0,16,16));
            images.add(part5sheet.getSprite(0,0,16,16));
        }
       // img = images.get((int)(Math.random() * 5));
        id = (int)(Math.random() * images.size());
    }

    public void tick() {
        if (pos.z < 0 || v.z < 0) {
            v.z += TileMap.GRAVITY;
            pos.z += v.z;
        }
        if (pos.z > 0) {
            pos.z = 0;
        }

        if (pos.z != 0) {
            pos.x += v.x;
            pos.y += v.y;
        }

        if (duration > 0) {
           duration--;
        }

/*
        if (z < 0 || dz < 0) {
            dz += TileMap.GRAVITY;
            z += dz;
        }
        if (z > 0) {
            z = 0;
        }*/
    }

    @Override
    public void draw(Graphics g, Camera camera) {
        Vector3 offset = camera.getOffset();

        double scale = camera.getScale();
        // add dim variable in TileMap
        if (pos.x > -4 && pos.y > -8) {
            //g.drawImage(img, (int)((pos.x-offset.x)*scale), (int)((pos.y-offset.y+pos.z)*scale), (int)(16*scale), (int)(16*scale), null);
            g.drawImage(images.get(id), (int)((pos.x-offset.x)*scale), (int)((pos.y-offset.y+pos.z)*scale), (int)(16*scale), (int)(16*scale), null);

        }
    }


}
