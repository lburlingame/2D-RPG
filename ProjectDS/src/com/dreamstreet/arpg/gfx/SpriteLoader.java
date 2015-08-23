package com.dreamstreet.arpg.gfx;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteLoader {

    private static ArrayList<BufferedImage> TILE_GRASS;
    private static SpriteSheet tilesheet = new SpriteSheet("/tiles/grass_sheet.png");
    private static SpriteSheet spritesheet = new SpriteSheet("/sprites/bear_sheet.png");
    private static ArrayList<BufferedImage> spritechar;

  /*  private static  SpriteSheet skulltulasheet = new SpriteSheet("/sprites/skulltula_sprite.png");
    private static  SpriteSheet kodamasheet = new SpriteSheet("/sprites/kodama_sprite.png");
    private static  SpriteSheet skeletonsheet = new SpriteSheet("/sprites/skeleton_sprite.png");
    private static  SpriteSheet nofacesheet = new SpriteSheet("/sprites/noface_sprite.png");*/

 /*   private static  BufferedImage skulltulachar = skulltulasheet.getSprite(0,0,32,32);
    private static  BufferedImage kodamachar = kodamasheet.getSprite(0,0,32,32);
    private static  BufferedImage skeletonchar = skeletonsheet.getSprite(0,0,32,32);
    private static  BufferedImage nofacechar = nofacesheet.getSprite(0,0,32,32);*/


    public static Image getTile(int id) {
        switch (id) {
            default:
                return getTileGrass(id);
        }
    }



    private static BufferedImage getTileGrass(int id) {
        if (TILE_GRASS == null) {
            TILE_GRASS = new ArrayList<>();
            TILE_GRASS.add(tilesheet.getSprite(0,0,32, 32));//1
            TILE_GRASS.add(tilesheet.getSprite(32,0,32,32));//1
            TILE_GRASS.add(tilesheet.getSprite(64,0,32,32));//1
        }
        return TILE_GRASS.get(id - 1);
    }

    public static BufferedImage getSprite(int id, int frame) {
        switch (id) {
            case (1):
                return getBear(frame);
            default:
                return getBear(frame);
        }
    }

    private static BufferedImage getBear(int frame) {
        if (spritechar == null) {
            spritechar = new ArrayList<>();
            for (int i = 0; i < spritesheet.getWidth(); i+= 32) {
                spritechar.add(spritesheet.getSprite(i, 0, 32, 32));
            }
        }
        return spritechar.get(frame % spritechar.size());
    }



}
