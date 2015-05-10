package com.dreamstreet.arpg;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

/**
 * Created on 3/26/2015.
 */
public class GameLauncher extends Applet {

    private static Game game = new Game();

    @Override
    public void init() {
        setLayout(new BorderLayout());
        add(game, BorderLayout.CENTER);
        setMinimumSize(Game.dimension);
        setMaximumSize(Game.dimension);
        setPreferredSize(Game.dimension);
    }

    @Override
    public void start() {
        game.start();
    }

    @Override
    public void stop() {
        game.stop();
    }

    public static void main(String[] args){
        game.debug = true;

        game.setMinimumSize(Game.dimension);
        game.setMaximumSize(Game.dimension);
        game.setPreferredSize(Game.dimension);

        game.frame = new JFrame(Game.NAME);

        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLayout(new BorderLayout());
        game.frame.add(game, BorderLayout.CENTER);
        game.frame.setUndecorated(true); //makes borderless window
        game.frame.pack();

        game.frame.setResizable(false);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        game.start();
        game.requestFocus();


        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Image image = toolkit.getImage("res/gui/d2_cursor.png");

        Point hotSpot = new Point(0,0);

        Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "Cursor");
        game.setCursor(cursor);
    }


}
