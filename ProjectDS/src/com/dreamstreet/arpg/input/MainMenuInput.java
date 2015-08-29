package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Vector2;
import com.dreamstreet.arpg.gfx.Vector3;
import com.dreamstreet.arpg.screen.MainMenuScreen;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 8/28/2015.
 */
public class MainMenuInput implements KeyListener, MouseInputListener, MouseWheelListener {

    private Game game;
    private MainMenuScreen screen;

    boolean button_clicked = false;


    public MainMenuInput(Game game, MainMenuScreen screen) {

        this.game = game;
        this.screen = screen;

        game.addKeyListener(this);
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
        game.addMouseWheelListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {

            Point mLoc = MouseInfo.getPointerInfo().getLocation();
            Point frameLoc = game.getLocationOnScreen();
            mLoc.x -= frameLoc.x;
            mLoc.y -= frameLoc.y;

            boolean clicked = false;
            for (int i = 0; i < screen.buttons.size(); i++) {
                clicked = screen.buttons.get(i).collision(mLoc);
                if (clicked) {
                    break;
                }
            }
            button_clicked = clicked;

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {

            Point mLoc = MouseInfo.getPointerInfo().getLocation();
            Point frameLoc = game.getLocationOnScreen();
            mLoc.x -= frameLoc.x;
            mLoc.y -= frameLoc.y;

            boolean clicked;
            for (int i = 0; i < screen.buttons.size(); i++) {
                clicked = screen.buttons.get(i).collision(mLoc);
                if (clicked && button_clicked) {
                    game.currentscreen = 1;
                }
            }


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
}
