package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.obj.Entity;
import com.dreamstreet.arpg.ui.DayCycle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created on 8/22/2015.
 */
public class GraphicsComponent {

    private Entity character;

    private int frame = 0;
    private int animation_timer = 3;

    public GraphicsComponent(Entity character) {
        this.character = character;
        frame = 0;
    }

    public void tick() {
        if (character.getState() > 0) {
            animation_timer--;
            if (animation_timer == 0) {
                if (character.getState() == 1) {
                    animation_timer = 8;
                }else if (character.getState() == 2){
                    animation_timer = 3;
                }else if (character.getState() == 3) {
                    animation_timer = 2;
                }

                frame++;
                frame = frame % 8;  // limit here to sprites frame count, only change when sprite id is changed
            }
        }else{
            frame = 0;
        }
    }

    public void draw(Graphics2D g, Camera camera){
        Vector3 offset = camera.getOffset();

        Vector3 pos = character.getPosition();
        Vector3 dim = character.getDimensions();
        double scale = camera.getScale();

        BufferedImage img =  SpriteLoader.getSprite(character.getID(), frame);

        g.setColor(new Color(0, 0, 0, (int)(DayCycle.max_darkness * 110 + 40)));
        g.fillOval((int)((pos.x - dim.x/2 - offset.x) * scale),(int)((pos.y - offset.y - dim.z / 6 - offset.z)* scale),(int)(dim.x*scale),(int)(dim.z*scale)/2);



        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();

//        g.rotate(Game.getAngle(new Vector2(pos.x, pos.y), input.getScreenLoc())/57.32,Game.WIDTH * Game.SCALE / 2, Game.HEIGHT * Game.SCALE / 2);
        AffineTransform tx = new AffineTransform();
        //  tx.setToRotation(Game.getAngle(new Vector2(pos.x, pos.y), input.getScreenLoc())/57.32, 16,16);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g.drawImage(op.filter(img, null), (int) ((pos.x - dim.x / 2 - offset.x) * scale - .5), (int) ((pos.y - dim.z * .8 + pos.z - offset.y) * scale - .5), (int) (dim.x * scale - .5), (int) (dim.z * scale - .5), null);

    }


}
