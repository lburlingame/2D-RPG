package com.dreamstreet.arpg.obj;

import com.dreamstreet.arpg.gfx.Vector3;

/**
 * Created on 8/21/2015.
 */
public interface Collidable {

    HitCircle getHit();
    Vector3 getPosition();
    boolean collidesWith(Collidable other);

}
