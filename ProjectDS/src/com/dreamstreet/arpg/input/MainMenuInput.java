package com.dreamstreet.arpg.input;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.screen.MainMenuScreen;

/**
 * Created on 8/28/2015.
 */
public class MainMenuInput {

    private Game game;
    private InputHandler input;
    private MainMenuScreen screen;

    int button_clicked = -1;

    public MainMenuInput(Game game, MainMenuScreen screen) {
        this.game = game;
        input = game.getInput();
        this.screen = screen;
    }

    public void tick() {
        if (input.M1.isPressed() && !input.M1.getPrevious()) {
            int clicked = -1;
            for (int i = 0; i < screen.buttons.size(); i++) {
                if (screen.buttons.get(i).clicked(input.mLoc)) {
                    clicked = i;
                    break;
                }
            }
            button_clicked = clicked;
        }else if (!input.M1.isPressed()) {
            if (button_clicked > -1) {
                for (int i = 0; i < screen.buttons.size(); i++) {
                    if (screen.buttons.get(i).clicked(input.mLoc) && i == button_clicked) {
                        if (i == 0) {
                            game.playGame();
                        }else if (i == 1) {
                            //screen.buttons.get(1).pop();
                            game.newGame();
                        }else if (i == 2) {
                            System.exit(1);
                        }
                    }
                }
            }
            button_clicked = -1;
        }
    }

}
