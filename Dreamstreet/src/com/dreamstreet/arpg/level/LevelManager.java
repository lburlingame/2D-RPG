package com.dreamstreet.arpg.level;

import com.dreamstreet.arpg.gfx.TileMap;

import java.util.ArrayList;

/**
 * Created on 4/8/2015.
 */
public class LevelManager {

    private static int current_level = 0;
    private static ArrayList<Level> levels = new ArrayList<>();

    public LevelManager() {


    }

    public static TileMap getMap() {
        return levels.get(current_level).getTileMap();
    }

}
