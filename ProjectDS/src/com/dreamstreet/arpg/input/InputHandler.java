package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created on 8/28/2015.
 * TODO - reads keybinds from a cfg file, how? no clue
 */
public class InputHandler implements KeyListener, MouseInputListener, MouseWheelListener {

    /*
     * KEY DEFINITION
     */

    public class Key {
        private boolean pressed = false;
        private boolean previous = false;

        protected void toggle(boolean isPressed) {
            pressed = isPressed;
        }

        public void setPrevious() {
            previous = pressed;
        }

        public boolean isPressed() {
            return pressed;
        }

        public boolean getPrevious() {
            return previous;
        }
    }

    /*
     *  INPUTHANDLER
     */

    private Game game;

    private Image icon_unclicked;
    private Image icon_clicked;
    private Cursor cursor_unclicked;
    private Cursor cursor_clicked;
    private Cursor cursor_hidden;
    private boolean hidden_cursor = false;

    public Point mLoc = new Point(0,0);
    public int wheelnotches = 0;

    public Key W = new Key();
    public Key S = new Key();
    public Key A = new Key();
    public Key D = new Key();
    public Key SPACE = new Key();
    public Key SHIFT = new Key();
    public Key CTRL = new Key();

    public Key ONE = new Key();
    public Key TWO = new Key();
    public Key THREE = new Key();
    public Key FOUR = new Key();
    public Key M1 = new Key();
    public Key M2 = new Key();
    public Key M3 = new Key();

    public Key ESC = new Key();
    public Key U = new Key();
    public Key I = new Key();
    public Key O = new Key();
    public Key P = new Key();
    public Key J = new Key();
    public Key L = new Key();

    public InputHandler(Game game) {
        this.game = game;

        game.addKeyListener(this);
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
        game.addMouseWheelListener(this);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotSpot = new Point(0,4);
        icon_unclicked = toolkit.getImage("res/gui/d2_cursor.png");
        icon_clicked = toolkit.getImage("res/gui/d2_cursor_clicked.png");

        cursor_unclicked = toolkit.createCustomCursor(icon_unclicked, hotSpot, "Unclicked");
        cursor_clicked = toolkit.createCustomCursor(icon_clicked, hotSpot, "Clicked");
        cursor_hidden = toolkit.createCustomCursor(toolkit.getImage(""), hotSpot, "Hidden");

        game.setCursor(cursor_unclicked);
    }


    public void tick() {
        mLoc = MouseInfo.getPointerInfo().getLocation();
        Point frameLoc = game.getLocationOnScreen();
        mLoc.x -= frameLoc.x;
        mLoc.y -= frameLoc.y;

    }


    public void setPrevious() {
        wheelnotches = 0;

        W.setPrevious();
        S.setPrevious();
        A.setPrevious();
        D.setPrevious();
        SPACE.setPrevious();
        SHIFT.setPrevious();
        CTRL.setPrevious();

        ONE.setPrevious();
        TWO.setPrevious();
        THREE.setPrevious();
        FOUR.setPrevious();
        M1.setPrevious();
        M2.setPrevious();
        M3.setPrevious();

        ESC.setPrevious();
        U.setPrevious();
        I.setPrevious();
        O.setPrevious();
        P.setPrevious();
        J.setPrevious();
        L.setPrevious();
    }

    public void toggleKey(int keyCode, boolean isPressed) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                W.toggle(isPressed);
                break;
            case KeyEvent.VK_S:
                S.toggle(isPressed);
                break;
            case KeyEvent.VK_A:
                A.toggle(isPressed);
                break;
            case KeyEvent.VK_D:
                D.toggle(isPressed);
                break;
            case KeyEvent.VK_SPACE:
                SPACE.toggle(isPressed);
                break;
            case KeyEvent.VK_SHIFT:
                SHIFT.toggle(isPressed);
                break;
            case KeyEvent.VK_CONTROL:
                CTRL.toggle(isPressed);
                break;

            case KeyEvent.VK_1:
                ONE.toggle(isPressed);
                break;
            case KeyEvent.VK_2:
                TWO.toggle(isPressed);
                break;
            case KeyEvent.VK_3:
                THREE.toggle(isPressed);
                break;
            case KeyEvent.VK_4:
                FOUR.toggle(isPressed);
                break;

            case MouseEvent.BUTTON1:
                M1.toggle(isPressed);
                break;
            case MouseEvent.BUTTON2:
                M2.toggle(isPressed);
                break;
            case MouseEvent.BUTTON3:
                M3.toggle(isPressed);
                break;


            case KeyEvent.VK_ESCAPE:
                ESC.toggle(isPressed);
                break;
            case KeyEvent.VK_U:
                U.toggle(isPressed);
                break;
            case KeyEvent.VK_I:
                I.toggle(isPressed);
                break;
            case KeyEvent.VK_O:
                O.toggle(isPressed);
                break;
            case KeyEvent.VK_P:
                P.toggle(isPressed);
                break;
            case KeyEvent.VK_J:
                J.toggle(isPressed);
                break;
            case KeyEvent.VK_L:
                L.toggle(isPressed);
                break;

        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (!hidden_cursor) {
            game.setCursor(cursor_clicked);
        }
        toggleKey(e.getButton(), true);
    }

    public void mouseReleased(MouseEvent e) {
        if (!hidden_cursor) {
            game.setCursor(cursor_unclicked);
        }
        toggleKey(e.getButton(), false);
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        wheelnotches = e.getWheelRotation();
    }

    public void hideCursor() {
        game.setCursor(cursor_hidden);
        hidden_cursor = true;

    }

    public void showCursor() {
        game.setCursor(cursor_unclicked);
        hidden_cursor = false;
    }
}

