package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.*;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 3/26/2015.
 */
public class PlayerInput extends InputComponent implements KeyListener, MouseInputListener, MouseWheelListener {

    private Camera camera;

    private boolean clicked = false;
    private boolean stopped = false;

    Toolkit toolkit;

    Image icon_unclicked;
    Image icon_clicked;

    Point hotSpot;

    Cursor cursor_unclicked;
    Cursor cursor_clicked;

    public PlayerInput(Game game, Camera camera) {
        super(game);
        this.camera = camera;

        game.addKeyListener(this);
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
        game.addMouseWheelListener(this);

        toolkit = Toolkit.getDefaultToolkit();

        icon_unclicked = toolkit.getImage("res/gui/d2_cursor.png");
        icon_clicked = toolkit.getImage("res/gui/d2_cursor_clicked.png");

        hotSpot = new Point(0,4);

        cursor_unclicked = toolkit.createCustomCursor(icon_unclicked, hotSpot, "Unclicked");
        cursor_clicked = toolkit.createCustomCursor(icon_clicked, hotSpot, "Clicked");

        game.setCursor(cursor_unclicked);
    }

    public void tick() {
        if (clicked && !stopped) {
            Vector3 offset = camera.getCartOffset();

            Point mLoc = MouseInfo.getPointerInfo().getLocation();
            Point frameLoc = this.getLocationOnScreen();
            mLoc.x -= frameLoc.x;
            mLoc.y -= frameLoc.y - (camera.getzOffset() * camera.getScale());
            mLoc = Iso.isoTo2D(mLoc);

            character.move(new Vector2(mLoc.getX() / camera.getScale() + offset.x, mLoc.getY() / camera.getScale() + offset.y));
/*
            character.fireball.charge(new Vector2(character.getX(),character.getY()));
            character.fireball.use(new Vector2(character.getX(),character.getY()), new Vector2(mLoc.getX() / camera.getScale() + offset.x, mLoc.getY() / camera.getScale() + offset.y));*/

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        /*toggleKey(e.getKeyCode(), true);*/
        if (e.getKeyCode() == KeyEvent.VK_S) {
            character.stop();
            stopped = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            game.emitter.bloodSpatter(new Vector3(character.getX() - character.getWidth()/2,character.getY() - character.getHeight()/2, character.getZ() - 15), new Vector3(Math.random() * 12 - 6, Math.random() * 12 - 6, -Math.random() * 3));
        }


        if (e.getKeyCode() == KeyEvent.VK_A) {
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
        }

        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
        }

        if (e.getKeyCode() == KeyEvent.VK_M) {
            if (game.audioPlay) {
                game.music.stop();
                game.audioPlay = false;
            }else{
                game.music.start();
                game.audioPlay = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            character.jump();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (game.isRunning()) {
                game.stop();
            }else{
                game.start();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_O) {
            game.changeCharacter();
        }
        if (e.getKeyCode() == KeyEvent.VK_I) {
            game.debug = !game.debug;
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
            clicked = true;
            Vector3 offset = camera.getCartOffset();

            Point mLoc = MouseInfo.getPointerInfo().getLocation();
            Point frameLoc = this.getLocationOnScreen();
            mLoc.x -= frameLoc.x;
            mLoc.y -= frameLoc.y - (camera.getzOffset() * camera.getScale());
            mLoc = Iso.isoTo2D(mLoc);

            character.fireball.use(new Vector2(character.getX(),character.getY()), new Vector2(mLoc.getX() / camera.getScale() + offset.x, mLoc.getY() / camera.getScale() + offset.y));

        }else if (e.getButton() == MouseEvent.BUTTON3) {
            character.stop();
            stopped = true;
            character.fireball.charge(new Vector2(character.getX(),character.getY()));
        }

        game.setCursor(cursor_clicked);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clicked = false;
        }else if (e.getButton() == MouseEvent.BUTTON3) {
            stopped = false;
            Vector3 offset = camera.getCartOffset();

            Point mLoc = MouseInfo.getPointerInfo().getLocation();
            Point frameLoc = this.getLocationOnScreen();
            mLoc.x -= frameLoc.x;
            mLoc.y -= frameLoc.y - (camera.getzOffset() * camera.getScale());
            mLoc = Iso.isoTo2D(mLoc);

            character.fireball.use(new Vector2(character.getX(),character.getY()), new Vector2(mLoc.getX() / camera.getScale() + offset.x, mLoc.getY() / camera.getScale() + offset.y));
        }

        game.setCursor(cursor_unclicked);
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
        int notches = e.getWheelRotation();
        if (notches < 0) {
            camera.zoomIn();
        } else {
            camera.zoomOut();
        }
        camera.centerCamera();
    }

    public Point getLocationOnScreen() {
        return game.getLocationOnScreen();
    }

    public void setCharacter(Sprite character) {
        this.character = character;
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
