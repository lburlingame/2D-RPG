package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 3/26/2015.
 */
public class InputHandler implements KeyListener, MouseInputListener, MouseWheelListener {

    private Game game;

    public InputHandler(Game game) {
        this.game = game;

        game.addKeyListener(this);
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
        game.addMouseWheelListener(this);
    }


    public class Key {
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(),true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(),false);
    }

    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_S) {
            s.toggle(isPressed);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point mLoc = MouseInfo.getPointerInfo().getLocation();
        Point frameLoc = this.getLocationOnScreen();
        mLoc.x -= frameLoc.x;
        mLoc.y -= frameLoc.y;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClicked = true;
        }else if (e.getButton() == MouseEvent.BUTTON3) {
            rightClicked = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClicked = false;
        }else if (e.getButton() == MouseEvent.BUTTON3) {
            rightClicked = false;
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
        wheelnotches = e.getWheelRotation();
    }

    public Point getLocationOnScreen() {
        return game.getLocationOnScreen();
    }
}
