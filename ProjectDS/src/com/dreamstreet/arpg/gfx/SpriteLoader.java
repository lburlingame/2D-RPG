package com.dreamstreet.arpg.gfx;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteLoader {

    private static BufferedImage TILE_STONE,TILE_GRASS,TILE_WATER,TILE_DEAD_GRASS,TILE_WALL,TILE_DOOR,ISOTILE_GRASS,ISOTILE_WHITE,ISOTILE_BLACK,ISOTILE_SHADOW,ISOTILE_SHADOWWATER;
    private static SpriteSheet tilesheet = new SpriteSheet("/tiles/squaregrass.png");
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
            case (1):
                return getTileGrass();
            case (2):
                return getTileStone();
            case (3):
                return getTileWater();
            case (4):
                return getTileDeadGrass();
            case (5):
                return getTileWall();
            case (6):
                return getIsoTile();
            case (7):
                return getWhiteTile();
            case (8):
                return getBlackTile();
            case (-1):
                return getShadowTile();
            case (-2):
                return getShadowWaterTile();
            case (100):
                return getTileDoor();
            default:
                return getShadowWaterTile();
        }
    }



    private static BufferedImage getTileGrass() {
        if (TILE_GRASS == null) {
            TILE_GRASS = tilesheet.getSprite(0,0,32,32);//1
        }
        return TILE_GRASS;
    }

    private static BufferedImage getTileStone() {
        if (TILE_STONE == null) {
            TILE_STONE = tilesheet.getSprite(173,0,32,32);//2
        }
        return TILE_STONE;
    }

    private static BufferedImage getTileWater() {
        if (TILE_WATER == null) {
            TILE_WATER = tilesheet.getSprite(0,0,64,47);//3
        }
        return TILE_WATER;
    }

    private static BufferedImage getTileDeadGrass() {
        if (TILE_DEAD_GRASS == null) {
            TILE_DEAD_GRASS = tilesheet.getSprite(107,33,32,32);//4
        }
        return TILE_DEAD_GRASS;
    }

    private static BufferedImage getTileWall() {
        if (TILE_WALL == null) {
            TILE_WALL = tilesheet.getSprite(74,132,32,32);//5
        }
        return TILE_WALL;
    }

    private static BufferedImage getIsoTile() {
        if (ISOTILE_GRASS == null) {
            ISOTILE_GRASS = tilesheet.getSprite(0,0,64,47);//6
        }
        return ISOTILE_GRASS;
    }

    private static BufferedImage getWhiteTile() {
        if (ISOTILE_WHITE == null) {
            ISOTILE_WHITE = tilesheet.getSprite(0,0,64,47);//7
        }
        return ISOTILE_WHITE;
    }

    private static BufferedImage getBlackTile() {
        if (ISOTILE_BLACK == null) {
            ISOTILE_BLACK = tilesheet.getSprite(0,0,64,47);//8
        }
        return ISOTILE_BLACK;
    }

    private static BufferedImage getShadowTile() {
        if (ISOTILE_SHADOW == null) {
            ISOTILE_SHADOW = tilesheet.getSprite(0,0,64,47);//8
        }
        return ISOTILE_SHADOW;
    }

    private static BufferedImage getShadowWaterTile() {
        if (ISOTILE_SHADOWWATER == null) {
            ISOTILE_SHADOWWATER = tilesheet.getSprite(0,0,64,47);//8
        }
        return ISOTILE_SHADOWWATER;
    }

    private static BufferedImage getTileDoor() {
        if (TILE_DOOR == null) {
            TILE_DOOR = tilesheet.getSprite(272,33,32,32);//100+
        }
        return TILE_DOOR;
    }

    public static BufferedImage getSprite(int id, int frame) {
        switch (id) {
            case (1):
                return getBear(frame);
            default:
                return getShadowWaterTile();
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
