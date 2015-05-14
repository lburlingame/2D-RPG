package com.dreamstreet.arpg.ui;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.SpriteSheet;
import com.dreamstreet.arpg.util.TextUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 5/5/2015.
 */
public class MessageBox {


    private static BufferedImage BOX;
    private static SpriteSheet boxsheet = new SpriteSheet("/gui/message_box.png");

    private static BufferedImage NEXT;
    private static SpriteSheet nextsheet = new SpriteSheet("/gui/next_message_icon.png");
    private static BufferedImage END;
    private static SpriteSheet endsheet = new SpriteSheet("/gui/end_message_icon.png");

    private static TextUtil textUtil = new TextUtil();
    private String message = "";
    private String curr_message = "";

    private int animation_timer;
    private int curr_tick;

    private int str_len;
    private int curr_ix;

    private int icon_size = 12;
    private boolean incrementing = false;

    private float x;
    private float y;
    private float width;
    private float height;

    public MessageBox(String message) {
        if (BOX == null) {
            BOX = boxsheet.getSprite(0,0,256,128);
        }
        if (NEXT == null) {
            NEXT = nextsheet.getSprite(0,0,32,32);
        }
        if (END == null) {
            END = endsheet.getSprite(0,0,32,32);
        }
        this.message = message;

        animation_timer = 4;
        curr_tick = 0;

        str_len = message.length();
        curr_ix = 0;

        width = Game.dimension.width / 4;
        height = width / 3;

        x = Game.dimension.width / 2 - width /2;
        y = Game.dimension.height / 10 * 1;


    }

    public void tick() {
        curr_tick++;
        if (curr_tick == animation_timer) {
            curr_tick = 0;
            if (incrementing) {
                icon_size++;
                if (icon_size == 14) {
                    incrementing = false;
                }
            }else{
                icon_size--;
                if (icon_size == 6) {
                    incrementing = true;
                }
            }
            if (curr_ix != str_len) {
                curr_message += message.charAt(curr_ix);
                curr_ix++;
            }
        }


    }

    public void draw(Graphics g) {
        g.drawImage(BOX, (int)x, (int)y, (int)width, (int)height, null);
        g.drawImage(NEXT, (int)(x + width / 2  - NEXT.getWidth()/2 + (NEXT.getWidth() - icon_size) / 2), (int)(y + height - NEXT.getHeight()/2 + (NEXT.getHeight() - icon_size) /2), icon_size, icon_size, null);

        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(curr_message, (int)x + 25, (int) y + 25);
    }
}
