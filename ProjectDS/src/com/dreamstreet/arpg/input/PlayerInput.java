package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Camera;
import com.dreamstreet.arpg.gfx.Vector2;
import com.dreamstreet.arpg.gfx.Vector3;
import com.dreamstreet.arpg.obj.Entity;
import com.dreamstreet.arpg.screen.GameScreen;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 3/26/2015.
 */
public class PlayerInput extends InputComponent {

    private Camera camera;
    private Entity character;
    private InputHandler input;

    private boolean clicked = false;
    private boolean stopped = false;

    public PlayerInput(Game game, Camera camera) {
        super(game);
        this.input = game.getInput();
        this.camera = camera;
    }

    public void tick() {

        // TODO MAKE IT SO THE MOST RECENTLY ACTIVATED KEY GETS PRIORITY
        if (input.W.isPressed()) {
            character.setDy(-3);
        }else{
            if (input.S.isPressed()) {
                character.setDy(3);
            }else if (!input.S.isPressed()) {
                character.setDy(0);
            }
        }

        if (input.A.isPressed()) {
            character.setDx(-3);
        }else if (!input.A.isPressed() ) {
            if (input.D.isPressed()) {
                character.setDx(3);
            }else if (!input.D.isPressed()) {
                character.setDx(0);
            }
        }

        if (input.SPACE.isPressed() && !input.SPACE.getPrevious()) {
            character.jump();
        }

        if (input.SHIFT.isPressed()) {
            character.sprint();
        }else if (input.CTRL.isPressed()) {
            character.walk();
        }else{
            character.run();
        }



        Vector3 offset = camera.getOffset();

        if (input.M1.isPressed()) {
            character.fireball.use(new Vector2(character.getX(), character.getY()), new Vector2(input.mLoc.getX() / camera.getScale() + offset.x, input.mLoc.getY() / camera.getScale() + offset.y));
            character.fireball.charge(new Vector3(character.getX(), character.getY(), -character.getHeight() / 4));
        }

        if (input.M3.isPressed()) {
            character.fireball.charge(new Vector3(character.getX(), character.getY(), -character.getHeight() / 4));
        }else if (input.M3.getPrevious()) {
            character.fireball.use(new Vector2(character.getX(), character.getY()), new Vector2(input.mLoc.getX() / camera.getScale() + offset.x, input.mLoc.getY() / camera.getScale() + offset.y));
        }


        if (clicked && !stopped) {

        }
    }


    // why is this needed? shouldn't the superclass method be sufficient? why is it breaking when i dont have this?
    public void setCharacter(Entity character) {
        this.character = character;
    }

}



