package com.dreamstreet.arpg.gfx;

import javax.swing.*;
import java.awt.*;

public class Textures {

    private static Image TILE_STONE,TILE_GRASS,TILE_WATER,TILE_DEAD_GRASS,TILE_WALL,TILE_DOOR,ISOTILE_GRASS;
    private static SpriteSheet tilesheet = new SpriteSheet("/tiles/tile_sheet.png");
    private static SpriteSheet isosheet = new SpriteSheet("/tiles/isotile.png");

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
            case (100):
                return getTileDoor();
            default:
                return getTileGrass();
        }
    }

    private static Image getTileGrass() {
        if (TILE_GRASS == null) {
            TILE_GRASS = new ImageIcon(tilesheet.getSprite(41,33,32,32)).getImage();//1
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
            ISOTILE_GRASS = new ImageIcon(isosheet.getSprite(0,0,88,43)).getImage();//6
        }
        return ISOTILE_GRASS;
    }


    private static Image getTileDoor() {
        if (TILE_DOOR == null) {
            TILE_DOOR = new ImageIcon(tilesheet.getSprite(272,33,32,32)).getImage();//100+
        }
        return TILE_DOOR;
    }

}
