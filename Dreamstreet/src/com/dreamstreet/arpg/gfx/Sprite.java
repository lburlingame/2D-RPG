package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {

    private double x;
    private double y;

    public Vector2 feet;

    private double velocity;

    private double dx;
    private double dy;

    private double dest_x;
    private double dest_y;

    private double width;
    private double height;
	private BufferedImage img;
    public double imgscale;
    private int frame = 0;
    private int animation_timer = 3;

    private Camera camera;
	
	public Sprite(BufferedImage img, double imgscale, double x, double y) {
        this.img = img;
        this.imgscale = imgscale;
		this.x = x;
		this.y = y;
        this.velocity = 3;
        this.dx = 0;
        this.dy = 0;
        this.width = 32;
        this.height = 32;
        feet = IsoCalculator.isoTo2D(new Vector2((width - 16) * imgscale, (height - 4) * imgscale));

    }

    public void tick() {
        if ((x + dx > dest_x && dx > 0) || (x + dx < dest_x && dx < 0)) {
            if (camera!=null) {
                camera.setDx(0);
                camera.setxOffset(camera.getxOffset() + dest_x - x);
            }
            dx = 0;
            x = dest_x;
            frame = 0;
            animation_timer = 3;
        }
        if ((y + dy > dest_y && dy > 0) || (y + dy < dest_y && dy < 0)) {
            if (camera != null) {
                camera.setDy(0);
                camera.setyOffset(camera.getyOffset() + dest_y - y);
            }
            dy = 0;
            y = dest_y;
            frame = 0;
            animation_timer = 3;
        }

        Tile dest = TileMap.getTile(x + dx + feet.x, y + feet.y);
        if (dest != null && dest.walkable) {
            x += dx;
            if (camera!=null) camera.setDx(dx);
        }else{
            if (camera!=null) camera.setDx(0);
        }
        dest = TileMap.getTile(x + feet.x, y + dy + feet.y);
        if (dest != null && dest.walkable) {
            y += dy;
            if (camera!=null) camera.setDy(dy);
        }else{
            if (camera!=null) camera.setDy(0);
        }

        if (dx != 0 || dy != 0) {
            animation_timer--;
            if (animation_timer == 0) {
                animation_timer = 3;
                frame++;
                frame = frame % 8;
            }
        }
    }
	
	public void draw(Graphics g, Camera camera){
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        img = Game.spritesheet.getSprite((int)(width * frame), 0, 32, 32);
        Vector2 iso = IsoCalculator.twoDToIso(new Vector2(x,y));
        Vector2 isofeet = IsoCalculator.twoDToIso(feet);

        g.setColor(new Color(0, 0, 0, (int)(TileMap.max_darkness * 110 + 40)));
        g.fillOval((int)((iso.x - xOffset) * scale),(int)((iso.y  + isofeet.y - 6 - yOffset)* scale),(int)(width*scale*imgscale),(int)(height*scale*imgscale)/2);
        g.drawImage(img, (int)((iso.x - xOffset)*scale), (int)((iso.y-yOffset)*scale),(int)(32*scale*imgscale),(int)(32*scale*imgscale), null);

        drawDebug(g,camera);
    }

    public void drawDebug(Graphics g, Camera camera) {
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        Vector2 iso = IsoCalculator.twoDToIso(new Vector2(x,y));
        Vector2 isofeet = IsoCalculator.twoDToIso(feet);
        Vector2 isodest = IsoCalculator.twoDToIso(new Vector2(dest_x,dest_y));

        g.setColor(Color.green);
      //  g.drawRect((int) ((iso.x - xOffset) * scale), (int) ((iso.y - yOffset) * scale), (int) (width * imgscale * scale), (int) (height * imgscale * scale)); //draws rectangle around char
       // g.fillRect((int)((iso.x-xOffset + isofeet.x)*scale), (int)((iso.y - yOffset + isofeet.y)*scale),5,5);  // draws rect at character's "feet"
        if (Math.abs(dx) > 0 || Math.abs(dy) > 0) {
            g.drawOval((int)((isodest.x + isofeet.x - xOffset)*scale), (int) ((isodest.y + isofeet.y - yOffset) * scale),(int)(1.2*scale*2)+1,(int)(1.2*scale)+1); // draws rectangle at character's destination point
        }

     //   g.drawString(TileMap.currentx + ", " + TileMap.currenty, (int) ((iso.x - xOffset) * scale + (int) (width * imgscale * scale * 1.05)), (int) ((iso.y - yOffset) * scale) + 20);
    }

    private Direction findSlope()
    {
        double rise = dest_y - y;
        double run = dest_x - x;

        int xdir = 0;
        double slope = 0;
        if (run == 0 && rise > 0) {
            slope = 2000;
            xdir = 0;
        }else if (run == 0 && rise < 0) {
            slope = -2000;
            xdir = 0;
        }else if (run != 0) {
            slope = rise / run;
            if (run > 0) {
                xdir = 1;
            }else{
                xdir = -1;
            }
        }

        return new Direction(xdir, slope);
    }

    public double findDX(double slope) {
        return Math.sqrt(Math.pow(velocity, 2) / (1 + Math.pow(slope, 2)));
    }

    public static double findDistance(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void move(double dest_x, double dest_y)
    {
        if (findDistance(dest_x - x - feet.x, dest_y - feet.y - y) > velocity) {
            this.dest_x = dest_x - feet.x;
            this.dest_y = dest_y - feet.y;

            Direction dir = findSlope();

            dx = findDX(dir.slope) * dir.xdir;
            dy = dir.slope * dx;


            if (dir.slope == 2000 || dir.slope == -2000)
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
    public void setCamera(Camera camera) {
        this.camera = camera;
    }



}
