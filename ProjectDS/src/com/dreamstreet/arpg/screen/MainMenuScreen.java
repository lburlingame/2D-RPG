package com.dreamstreet.arpg.screen;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Vector2;
import com.dreamstreet.arpg.input.MainMenuInput;
import com.dreamstreet.arpg.input.MainMenuInput2;
import com.dreamstreet.arpg.ui.MenuButton;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 8/28/2015.
 */
public class MainMenuScreen implements Screen {

    private Game game;
    private String NAME = "MAIN MENU";

    public static boolean debug = false;

    public ArrayList<MenuButton> buttons = new ArrayList<>();
    private MainMenuInput2 min;

    public MainMenuScreen(Game game) {
        this.game = game;
        min = new MainMenuInput2(game, this);

        int width = (int)Game.dimension.getWidth() / 6;
        int height = (int)(Game.dimension.getHeight() / 10);
        int x = (int)(Game.dimension.getWidth() / 2) - width/2;

        int y = (int)(Game.dimension.getHeight() / 10 * 4);

        buttons.add(new MenuButton(new Vector2(x, y), new Vector2(width, height)));

    }


    public void tick() {
        min.tick();
    }

    public void render(Graphics2D g){
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, (int) Game.dimension.getWidth() + 100, (int) Game.dimension.getHeight() + 100);

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(g);
        }



        if (debug) {
            drawDebug(g);
        }

    }


    public void drawDebug(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.setColor(Color.WHITE);

    }






}
