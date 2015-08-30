package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.screen.MainMenuScreen;
import jdk.internal.util.xml.impl.Input;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 8/28/2015.
 */
public class MainMenuInput2 {

    private Game game;
    private InputHandler input;
    private MainMenuScreen screen;

    boolean button_clicked = false;


    public MainMenuInput2(Game game, MainMenuScreen screen) {
        this.game = game;
        input = game.getInput();
        this.screen = screen;


    }

    public void tick() {
        if (input.M1.isPressed() && !input.M1.getPrevious()) {
            boolean clicked = false;
            for (int i = 0; i < screen.buttons.size(); i++) {
                clicked = screen.buttons.get(i).collision(input.mLoc);
                if (clicked) {
                    break;
                }
            }
            button_clicked = clicked;
        }
        else if (!input.M1.isPressed()) {
            boolean clicked;
            for (int i = 0; i < screen.buttons.size(); i++) {
                clicked = screen.buttons.get(i).collision(input.mLoc);
                if (clicked && button_clicked) {
                    game.currentscreen = 1;
                }
            }
        }
    }

}
