package com.dreamstreet.arpg.gfx;


import java.awt.*;

/**
 * Created on 4/7/2015.
 */
public class IsoCalculator {


    public static Vector2 twoDToIso(Vector3 cart){
        Vector2 temp = new Vector2(0,0);
        temp.x = cart.x - cart.y;
        temp.y = (cart.x + cart.y) / 2 + cart.z;
        return temp;
    }

    public static Vector2 isoTo2D(Vector2 iso) {
        Vector2 temp = new Vector2(0, 0);
        temp.x = (2 * iso.y + iso.x) / 2;
        temp.y = (2 * iso.y - iso.x) / 2;
        return temp;
    }

    public static Point isoTo2D(Point iso) {
        Point temp = new Point(0, 0);
        temp.x = (2 * iso.y + iso.x) / 2;
        temp.y = (2 * iso.y - iso.x) / 2;
        return temp;
    }
}


/*

package com.dreamstreet.arpg.gfx;


import java.awt.*;


public class IsoCalculator {


    public static Vector2 twoDToIso(Vector2 cart){
        Vector2 temp = new Vector2(0,0);
        temp.x = cart.x - cart.y;
        temp.y = (cart.x + cart.y) / 2;
        return temp;
    }

    public static Vector2 isoTo2D(Vector2 iso) {
        Vector2 temp = new Vector2(0, 0);
        temp.x = (2 * iso.y + iso.x) / 2;
        temp.y = (2 * iso.y - iso.x) / 2;
        return temp;
    }

    public static Point isoTo2D(Point iso) {
        Point temp = new Point(0, 0);
        temp.x = (2 * iso.y + iso.x) / 2;
        temp.y = (2 * iso.y - iso.x) / 2;
        return temp;
    }
}

 */