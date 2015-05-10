package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.input.InputComponent;
import com.dreamstreet.arpg.item.Item;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {

    private double x;
    private double y;
    public double z;

    public double dz = 0;
    public Vector3 feet;

    private double velocity;

    private double dx;
    private double dy;

    private double dest_x;
    private double dest_y;

    private double width;
    private double height;

    private InputComponent input;
    public double imgscale;
    private int frame = 0;
    private int animation_timer = 3;

	public Item fireball = new Item();

	public Sprite(InputComponent input, double imgscale, Vector3 pos) {
        this.input = input;
        this.imgscale = imgscale;
        this.x = pos.x;
		this.y = pos.y;
        this.z = pos.z;
        this.velocity = 2;
        this.dx = 0;
        this.dy = 0;
        this.width = 32 * imgscale;
        this.height = 32 * imgscale;
        feet = new Vector3((width/2), (height/8*7), 0);

        input.setCharacter(this);
    }

    public void tick() {
        input.tick();


        if ((x + dx > dest_x && dx > 0) || (x + dx < dest_x && dx < 0)) {

            dx = 0;
            x = dest_x;
            frame = 0;
            animation_timer = 3;
        }
        if ((y + dy > dest_y && dy > 0) || (y + dy < dest_y && dy < 0)) {

            dy = 0;
            y = dest_y;
            frame = 0;
            animation_timer = 3;
        }

        if (z < 0 || dz < 0) {
            dz += TileMap.GRAVITY;
            z += dz;
        }
        if (z > 0) {
            z = 0;
        }

        Tile dest = TileMap.getTile(x + dx + feet.x, y + feet.y);
        if (dest != null && dest.walkable) {
            x += dx;
        }

        dest = TileMap.getTile(x + feet.x, y + dy + feet.y);
        if (dest != null && dest.walkable) {
            y += dy;
        }


        if (dx != 0 || dy != 0) {
            animation_timer--;
            if (animation_timer == 0) {
                animation_timer = 3;
                frame++;
                frame = frame % 8;
            }
        }

        fireball.tick();
    }
	
	public void draw(Graphics g, Camera camera){
        Vector2 offset = camera.getCartOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        g.setColor(new Color(0, 0, 0, (int)(TileMap.max_darkness * 110 + 40)));
        g.fillOval((int)((x - xOffset) * scale),(int)((y  + feet.y - 6 - yOffset-z)* scale),(int)(width*scale),(int)(height*scale));
        g.setColor(new Color(255, 255, 255, 255));
        g.drawOval((int) ((x - xOffset) * scale), (int) ((y - yOffset) * scale), (int) (width * scale), (int) (height * scale));

        fireball.draw(g, camera);
    }

    public void drawDebug(Graphics g, Camera camera) {
        Vector2 offset = camera.getCartOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();


        g.setColor(Color.green);
      //  g.drawRect((int) ((iso.x - xOffset) * scale), (int) ((iso.y - yOffset) * scale), (int) (width * imgscale * scale), (int) (height * imgscale * scale)); //draws rectangle around char
       // g.fillRect((int)((iso.x-xOffset + isofeet.x)*scale), (int)((iso.y - yOffset + isofeet.y)*scale),5,5);  // draws rect at character's "feet"
        if (Math.abs(dx) > 0 || Math.abs(dy) > 0) {
            g.drawOval((int)((dest_x + feet.x - xOffset)*scale), (int) ((dest_y + feet.y - yOffset) * scale),(int)(1.2*scale*2)+1,(int)(1.2*scale)+1); // draws rectangle at character's destination point
        }

     //   g.drawString(TileMap.currentx + ", " + TileMap.currenty, (int) ((iso.x - xOffset) * scale + (int) (width * imgscale * scale * 1.05)), (int) ((iso.y - yOffset) * scale) + 20);
    }


    public void move(double dest_x, double dest_y)
    {
        if (Util.findDistance(dest_x - x - feet.x, dest_y - feet.y - y) > velocity) {
            this.dest_x = dest_x - feet.x;
            this.dest_y = dest_y - feet.y;

            Direction dir = Util.findSlope(x, y, this.dest_x, this.dest_y);

            dx = Util.findX(velocity, dir.slope) * dir.xdir;
            dy = dir.slope * dx;

            if (dir.slope == 200000 || dir.slope == -200000)
            {
                dy = velocity * dir.slope / Math.abs(dir.slope);
            }
        }
    }



    public void stop() {
        frame = 0;
        animation_timer = 3;
        dest_x = x;
        dest_y = y;
        dx = 0;
        dy = 0;
    }

    public void jump() {
        dz = -4.5;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        dest_x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        dest_y = y;
    }

    public double getDy() {
        return dy;
    }

    public double getDx() {
        return dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDest_y() {
        return dest_y;
    }

    public double getDest_x() {
        return dest_x;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    public void setInput(InputComponent input) {
        this.input = input;
        input.setCharacter(this);
    }

    public InputComponent getInput() {
        return input;
    }

}
