package com.dreamstreet.arpg.obj;

import com.dreamstreet.arpg.gfx.Camera;

import java.awt.*;

/**
 * Created on 8/22/2015.
 */
public class EntityInfo {

    public int current_health;
    public int MAX_HEALTH;

    public int current_mana;
    public int MAX_MANA;

    private int level;       // coudl move all experience stuff into a class?

    public int current_exp;
    public int NEXT_LEVEL_EXP;

    public EntityInfo() {
        current_health = 999;
        MAX_HEALTH = 1000;
        current_mana = MAX_MANA = 1000;

    }


    public int getCurrHealth() {
        return current_health;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }


    public boolean isAlive() {
        return current_health > 0;
    }

    public int getLevel() {
        return level;
    }

    public boolean takeDamage(int damage) {
        current_health = current_health - damage;
        if (current_health < 0) current_health = 0;
        return isAlive();
    }

}
