package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.*;
import com.dreamstreet.arpg.obj.Entity;
import com.dreamstreet.arpg.screen.GameScreen;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 3/26/2015.
 */
public class PlayerInput extends InputComponent implements KeyListener, MouseInputListener, MouseWheelListener {

    GameScreen screen;

    private Camera camera;

    private boolean clicked = false;
    private boolean stopped = false;



    public PlayerInput(Game game, GameScreen screen, Camera camera) {
        super(game);

        this.screen = screen;
        this.camera = camera;

        game.addKeyListener(this);
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
        game.addMouseWheelListener(this);



    }

    public void tick() {
        if (clicked && !stopped) {
            Vector3 offset = camera.getOffset();

            Point mLoc = MouseInfo.getPointerInfo().getLocation();
            Point frameLoc = this.getLocationOnScreen();
            mLoc.x -= frameLoc.x;
            mLoc.y -= frameLoc.y;// - (camera.getzOffset() * camera.getScale());

           // character.move(new Vector2(mLoc.getX() / camera.getScale() + offset.x, mLoc.getY() / camera.getScale() + offset.y));

            character.fireball.charge(new Vector3(character.getX(),character.getY(), 0));
            character.fireball.use(new Vector2(character.getX(),character.getY()), new Vector2(mLoc.getX() / camera.getScale() + offset.x, mLoc.getY() / camera.getScale() + offset.y));

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_W:
                character.setDy(-3);
                break;
            case KeyEvent.VK_S:
                character.setDy(3);
                break;
            case KeyEvent.VK_A:
                character.setDx(-3);
                break;
            case KeyEvent.VK_D:
                character.setDx(3);
                break;
            case KeyEvent.VK_H:
                character.stop();
                stopped = true;
                break;
            case KeyEvent.VK_SHIFT:
                character.sprint();
                break;
            case KeyEvent.VK_CONTROL:
                character.walk();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_W:
                if (character.getDy() == -3)
                character.setDy(0);
                break;

            case KeyEvent.VK_S:
                if (character.getDy() == 3)
                    character.setDy(0);
                break;

            case KeyEvent.VK_A:
                if (character.getDx() == -3)
                    character.setDx(0);
                break;

            case KeyEvent.VK_D:
                if (character.getDx() == 3)
                    character.setDx(0);
                break;


            case KeyEvent.VK_Q:
                screen.emitter.bloodSpatter(new Vector3(character.getX(), character.getY(), character.getZ() - character.getDimensions().z / 2), new Vector3(Math.random() * 3 - 1.5, Math.random() * 3 - 1.5, -Math.random() * 3));
                break;
            case KeyEvent.VK_SHIFT:
                character.run();
                break;
            case KeyEvent.VK_CONTROL:
                character.run();
                break;
            case KeyEvent.VK_SPACE:
                character.jump();
                break;

            case KeyEvent.VK_G:
                character.changeSize();
                break;
            case KeyEvent.VK_J:
                character.takeDamage(50);
                break;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       /* Point mLoc = MouseInfo.getPointerInfo().getLocation();
        Point frameLoc = this.getLocationOnScreen();
        mLoc.x -= frameLoc.x;
        mLoc.y -= frameLoc.y;*/
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            //    character.move(e.getX() / camera.getScale() + camera.getXOffset(), e.getY() / camera.getScale() + camera.getYOffset());
            stopped = false;
            clicked = true;  // true for mouse movement
            Vector3 offset = camera.getOffset();

            Point mLoc = MouseInfo.getPointerInfo().getLocation();
            Point frameLoc = this.getLocationOnScreen();
            mLoc.x -= frameLoc.x;
            mLoc.y -= frameLoc.y;//- (camera.getzOffset() * camera.getScale());
            //mLoc = Iso.isoTo2D(mLoc);

            character.fireball.use(new Vector2(character.getX(),character.getY()), new Vector2(mLoc.getX() / camera.getScale() + offset.x, mLoc.getY() / camera.getScale() + offset.y));

        }else if (e.getButton() == MouseEvent.BUTTON3) {
            character.stop();
            stopped = true;
            character.fireball.charge(new Vector3(character.getX(),character.getY(), -character.getHeight()/4));
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clicked = false;
        }else if (e.getButton() == MouseEvent.BUTTON3) {
            stopped = false;
            Vector3 offset = camera.getOffset();

            Point mLoc = MouseInfo.getPointerInfo().getLocation();
            Point frameLoc = this.getLocationOnScreen();
            mLoc.x -= frameLoc.x;
            mLoc.y -= frameLoc.y;//- (camera.getzOffset() * camera.getScale());

            character.fireball.use(new Vector2(character.getX(), character.getY()), new Vector2(mLoc.getX() / camera.getScale() + offset.x, mLoc.getY() / camera.getScale() + offset.y));
        }

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public Point getLocationOnScreen() {
        return game.getLocationOnScreen();
    }

    public void setCharacter(Entity character) {
        this.character = character;
    }

    public Vector2 getScreenLoc() {
        Point mLoc = MouseInfo.getPointerInfo().getLocation();
        Point frameLoc = this.getLocationOnScreen();
        mLoc.x -= frameLoc.x;
        mLoc.y -= frameLoc.y;//- (camera.getzOffset() * camera.getScale());
        //mLoc = Iso.isoTo2D(mLoc);

        Vector2 target = new Vector2(mLoc.getX() / camera.getScale() + camera.getOffset().x,mLoc.getY() / camera.getScale() + camera.getOffset().y);

        return target;
    }
}



    /*public class Key {
        private boolean pressed = false;
        protected void toggle(boolean isPressed) {
            pressed = isPressed;
        }

        public boolean isPressed() {
            return pressed;
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key s = new Key();

    public boolean leftClicked = false;
    public boolean rightClicked = false;
    public int wheelnotches = 0;

    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_S) {
            s.toggle(isPressed);
        }
    }*/
