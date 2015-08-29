package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 8/28/2015.
 */
public class InputHandler implements KeyListener, MouseInputListener, MouseWheelListener {

    private Game game;

    private Image icon_unclicked;
    private Image icon_clicked;
    private Cursor cursor_unclicked;
    private Cursor cursor_clicked;

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

        game.setCursor(cursor_unclicked);
    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        game.setCursor(cursor_clicked);
    }

    public void mouseReleased(MouseEvent e) {
        game.setCursor(cursor_unclicked);
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

    }
}
