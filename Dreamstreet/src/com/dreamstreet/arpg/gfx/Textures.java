package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Textures {

    private static BufferedImage TILE_STONE,TILE_GRASS,TILE_WATER,TILE_DEAD_GRASS,TILE_WALL,TILE_DOOR,ISOTILE_GRASS,ISOTILE_WHITE,ISOTILE_BLACK,ISOTILE_SHADOW,ISOTILE_SHADOWWATER;
    private static SpriteSheet tilesheet = new SpriteSheet("/tiles/tile_sheet.png");
    private static SpriteSheet isosheet = new SpriteSheet("/tiles/isotile643d2.png");
    private static SpriteSheet watersheet = new SpriteSheet("/tiles/isowatertile643d4.png");
    private static SpriteSheet grasssheet = new SpriteSheet("/tiles/isograsstile643d.png");
    private static SpriteSheet whitesheet = new SpriteSheet("/tiles/whiteiso64.png");
    private static SpriteSheet blacksheet = new SpriteSheet("/tiles/blackiso64.png");
    private static SpriteSheet shadowsheet = new SpriteSheet("/tiles/shadowtile64.png");
    private static SpriteSheet shadowwatersheet = new SpriteSheet("/tiles/shadowwatertile64.png");



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

    private static Image getTileGrass() {
        if (TILE_GRASS == null) {
            TILE_GRASS = Game.toCompatibleImage(grasssheet.getSprite(0,0,64,47));//1
        }
        return TILE_GRASS;
    }

    private static Image getTileStone() {
        if (TILE_STONE == null) {
            TILE_STONE = Game.toCompatibleImage(tilesheet.getSprite(173,0,32,32));//2
        }
        return TILE_STONE;
    }

    private static Image getTileWater() {
        if (TILE_WATER == null) {
            TILE_WATER = Game.toCompatibleImage(watersheet.getSprite(0,0,64,47));//3
        }
        return TILE_WATER;
    }

    private static Image getTileDeadGrass() {
        if (TILE_DEAD_GRASS == null) {
            TILE_DEAD_GRASS = Game.toCompatibleImage(tilesheet.getSprite(107,33,32,32));//4
        }
        return TILE_DEAD_GRASS;
    }

    private static Image getTileWall() {
        if (TILE_WALL == null) {
            TILE_WALL = tilesheet.getSprite(74,132,32,32);//5
        }
        return TILE_WALL;
    }

    private static Image getIsoTile() {
        if (ISOTILE_GRASS == null) {
            ISOTILE_GRASS = Game.toCompatibleImage(isosheet.getSprite(0,0,64,47));//6
        }
        return ISOTILE_GRASS;
    }

    private static Image getWhiteTile() {
        if (ISOTILE_WHITE == null) {
            ISOTILE_WHITE = whitesheet.getSprite(0,0,64,47);//7
        }
        return ISOTILE_WHITE;
    }

    private static Image getBlackTile() {
        if (ISOTILE_BLACK == null) {
            ISOTILE_BLACK = blacksheet.getSprite(0,0,64,47);//8
        }
        return ISOTILE_BLACK;
    }

    private static Image getShadowTile() {
        if (ISOTILE_SHADOW == null) {
            ISOTILE_SHADOW = Game.toCompatibleImage(shadowsheet.getSprite(0,0,64,47));//8
        }
        return ISOTILE_SHADOW;
    }

    private static Image getShadowWaterTile() {
        if (ISOTILE_SHADOWWATER == null) {
            ISOTILE_SHADOWWATER = Game.toCompatibleImage(shadowwatersheet.getSprite(0,0,64,47));//8
        }
        return ISOTILE_SHADOWWATER;
    }

    private static Image getTileDoor() {
        if (TILE_DOOR == null) {
            TILE_DOOR = tilesheet.getSprite(272,33,32,32);//100+
        }
        return TILE_DOOR;
    }

}
