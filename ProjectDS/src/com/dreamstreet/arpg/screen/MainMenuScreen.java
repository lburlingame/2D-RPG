package com.dreamstreet.arpg.screen;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Vector2;
import com.dreamstreet.arpg.input.MainMenuInput;
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
    private MainMenuInput min;

    public MainMenuScreen(Game game) {
        this.game = game;
        min = new MainMenuInput(game, this);

        int width = (int)300;
        int height = (int)(75);
        int x = (int)(Game.dimension.getWidth() / 2) - width/2;
        int y = (int)(Game.dimension.getHeight() / 2 - height * 3 * 1.3/ 2  );

        buttons.add(new MenuButton(new Vector2(x, y), new Vector2(width, height), "/menu/play.png"));
        buttons.add(new MenuButton(new Vector2(x, y + height * 1.3), new Vector2(width, height), "/menu/newgame.png"));
        buttons.add(new MenuButton(new Vector2(x, y + height * 2.6), new Vector2(width, height), "/menu/exit.png"));

    }


    public void tick() {
        min.tick();
    }

    public void render(Graphics2D g){
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, (int) Game.dimension.getWidth(), (int) Game.dimension.getHeight());

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
