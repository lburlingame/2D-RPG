package com.dreamstreet.arpg.screen;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.SpriteSheet;
import com.dreamstreet.arpg.input.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 8/28/2015.
 */
public class SplashScreen implements Screen {

    // make cursor transparent on splash
    private Game game;
    private InputHandler input;

    private int duration;
    private int current;
    private float fade;
    private float fadeduration;

    private static SpriteSheet sheet = new SpriteSheet("/other/fbtest.png");
    private static BufferedImage logo = sheet.getSprite(0,0,300,300);
    int x;
    int y;
    int width;

    int height;

    public SplashScreen(Game game) {
        this.game = game;
        input = game.getInput();
        input.hideCursor();
        duration = 60;//300;
        current = 0;
        fade = 0;
        fadeduration = 180;
        width = 300;
        height = 300;
        x = (int)(Game.dimension.getWidth() / 2) - width/2;

        y = (int)(Game.dimension.getHeight() / 2) - height/2;
    }

    public void tick() {
        current++;
        if (fade < fadeduration && current > 30)
            fade++;
        if (current >= duration) {
            input.showCursor();
            game.openMenu();
        }
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, (int) Game.dimension.getWidth() + 100, (int) Game.dimension.getHeight() + 100);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));//(fade / fadeduration)));

        g.drawImage(logo, x, y, width, height, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        g.setColor(Color.black);

    }
}
