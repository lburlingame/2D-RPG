package com.dreamstreet.arpg.gfx;

import javax.swing.*;
import java.awt.*;

public class Textures {

    private static Image TILE_STONE,TILE_GRASS,TILE_WATER,TILE_DEAD_GRASS,TILE_WALL,TILE_DOOR,ISOTILE_GRASS,ISOTILE_WHITE,ISOTILE_BLACK,ISOTILE_SHADOW;
    private static SpriteSheet tilesheet = new SpriteSheet("/tiles/tile_sheet.png");
    private static SpriteSheet isosheet = new SpriteSheet("/tiles/isotile643d2.png");
    private static SpriteSheet grasssheet = new SpriteSheet("/tiles/isograsstile643d.png");
    private static SpriteSheet whitesheet = new SpriteSheet("/tiles/whiteiso64.png");
    private static SpriteSheet blacksheet = new SpriteSheet("/tiles/blackiso64.png");
    private static SpriteSheet shadowsheet = new SpriteSheet("/tiles/shadowtile64.png");


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
            case (100):
                return getTileDoor();
            default:
                return getTileGrass();
        }
    }

    private static Image getTileGrass() {
        if (TILE_GRASS == null) {
            TILE_GRASS = new ImageIcon(grasssheet.getSprite(0,0,64,47)).getImage();//1
        }
        return TILE_GRASS;
    }

    private static Image getTileStone() {
        if (TILE_STONE == null) {
            TILE_STONE = new ImageIcon(tilesheet.getSprite(173,0,32,32)).getImage();//2
        }
        return TILE_STONE;
    }

    private static Image getTileWater() {
        if (TILE_WATER == null) {
            TILE_WATER = new ImageIcon(tilesheet.getSprite(305,165,32,32)).getImage();//3
        }
        return TILE_WATER;
    }

    private static Image getTileDeadGrass() {
        if (TILE_DEAD_GRASS == null) {
            TILE_DEAD_GRASS = new ImageIcon(tilesheet.getSprite(107,33,32,32)).getImage();//4
        }
        return TILE_DEAD_GRASS;
    }

    private static Image getTileWall() {
        if (TILE_WALL == null) {
            TILE_WALL = new ImageIcon(tilesheet.getSprite(74,132,32,32)).getImage();//5
        }
        return TILE_WALL;
    }

    private static Image getIsoTile() {
        if (ISOTILE_GRASS == null) {
            ISOTILE_GRASS = new ImageIcon(isosheet.getSprite(0,0,64,47)).getImage();//6
        }
        return ISOTILE_GRASS;
    }

    private static Image getWhiteTile() {
        if (ISOTILE_WHITE == null) {
            ISOTILE_WHITE = new ImageIcon(whitesheet.getSprite(0,0,64,47)).getImage();//7
        }
        return ISOTILE_WHITE;
    }

    private static Image getBlackTile() {
        if (ISOTILE_BLACK == null) {
            ISOTILE_BLACK = new ImageIcon(blacksheet.getSprite(0,0,64,47)).getImage();//8
        }
        return ISOTILE_BLACK;
    }

    private static Image getShadowTile() {
        if (ISOTILE_SHADOW == null) {
            ISOTILE_SHADOW = new ImageIcon(shadowsheet.getSprite(0,0,64,47)).getImage();//8
        }
        return ISOTILE_SHADOW;
    }


    private static Image getTileDoor() {
        if (TILE_DOOR == null) {
            TILE_DOOR = new ImageIcon(tilesheet.getSprite(272,33,32,32)).getImage();//100+
        }
        return TILE_DOOR;
    }

}
