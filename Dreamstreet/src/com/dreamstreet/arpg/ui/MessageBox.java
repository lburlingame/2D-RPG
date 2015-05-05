package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 5/5/2015.
 */
public class MessageBox {


    private static BufferedImage BOX;
    private static SpriteSheet boxsheet = new SpriteSheet("/gui/messagebox.png");
    private String message = "";

    private int animation_timer;
    private int curr_tick;

    private int str_len;
    private int curr_ix;

    public MessageBox(String message) {
        if (BOX == null) {
            BOX = boxsheet.getSprite(0,0,256,128);
        }
        this.message = message;

        animation_timer = 15;
        curr_tick = 0;

        str_len = message.length();
        curr_ix = 0;
    }

    public void tick() {
        if (curr_ix != str_len) {
            curr_tick++;
            if (curr_tick == animation_timer) {
                curr_ix++;
                curr_tick = 0;
            }
        }
    }

    public void draw(Graphics g){
        g.drawImage(BOX, 550, 75, 384, 192, null);
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 16));

        g.drawString(message, 370, 270);
    }
}
