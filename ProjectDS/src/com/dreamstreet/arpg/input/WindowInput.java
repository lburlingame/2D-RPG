package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Camera;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 8/24/2015.
 */
public class WindowInput implements KeyListener, MouseInputListener, MouseWheelListener {

    private Game game;
    private Camera camera;

    Toolkit toolkit;

    Image icon_unclicked;
    Image icon_clicked;

    Point hotSpot;

    Cursor cursor_unclicked;
    Cursor cursor_clicked;

    public WindowInput(Game game, Camera camera) {
        this.game = game;
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


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_M:
                if (game.audioPlay) {
                    game.music.stop();
                }else{
                    game.music.start();
                }
                game.audioPlay = !game.audioPlay;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                break;
            case KeyEvent.VK_P:
                game.pause = !game.pause;
                break;
            case KeyEvent.VK_I:
                game.debug = !game.debug;
                break;
        }
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
        int notches = e.getWheelRotation();
        if (notches < 0) {
            camera.zoomIn();
        } else {
            camera.zoomOut();
        }
        camera.centerCamera();
    }
}
