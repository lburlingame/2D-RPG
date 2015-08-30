package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.screen.MainMenuScreen;

/**
 * Created on 8/28/2015.
 */
public class MainMenuInput2 {

    private Game game;
    private InputHandler input;
    private MainMenuScreen screen;

    int button_clicked = -1;


    public MainMenuInput2(Game game, MainMenuScreen screen) {
        this.game = game;
        input = game.getInput();
        this.screen = screen;


    }

    public void tick() {
        if (input.M1.isPressed()) {
            int clicked = -1;
            for (int i = 0; i < screen.buttons.size(); i++) {
                if (screen.buttons.get(i).clicked(input.mLoc)) {
                    clicked = i;
                    break;
                }
            }
            button_clicked = clicked;
        }
        else if (!input.M1.isPressed()) {
            for (int i = 0; i < screen.buttons.size(); i++) {
                if (screen.buttons.get(i).clicked(input.mLoc) && i == button_clicked) {
                    game.currentscreen = 1;
                }
            }
        }
    }

}
