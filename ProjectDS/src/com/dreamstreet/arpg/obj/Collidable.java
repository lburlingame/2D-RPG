package com.dreamstreet.arpg.obj;

import com.dreamstreet.arpg.gfx.Vector2;
import com.dreamstreet.arpg.gfx.Vector3;
//turn into abstract class??
/**
 * Created on 8/21/2015.
 */
public abstract class Collidable {

    protected Vector3 pos;   //position
    protected Vector3 dim;   //dimensions
    protected double smult; // size multiplier
    protected HitCircle hit;
    protected Vector3 vel;

    public HitCircle getHit() {
        return hit;
    }
    public Vector3 getPosition() {
        return new Vector3(pos.x, pos.y, pos.z);
    }

    // all hit circles have an angle, if angle is 360, then it wont calculate cone stuff, if its less than 360, then it will calculate cone based collison, then there wont need to be an abstract collidewith method and it will be only in superclass
    public abstract boolean collidesWith(Collidable other);

}
