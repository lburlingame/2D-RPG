package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;
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
	private BufferedImage img;
    public double imgscale;
    private int frame = 0;
    private int animation_timer = 3;

    private Camera camera;
	public Item fireball = new Item();

	public Sprite(BufferedImage img, double imgscale, double x, double y) {
        this.img = img;
        this.imgscale = imgscale;
		this.x = x;
		this.y = y;
        this.z = 0;
        this.velocity = 2;
        this.dx = 0;
        this.dy = 0;
        this.width = 32;
        this.height = 32;
        feet = IsoCalculator.isoTo2D(new Vector2((width/2) * imgscale, (height/8*7) * imgscale));

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

        dz += TileMap.GRAVITY;
        z += dz;
        if (z > 0) {
            z = 0;
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

        fireball.tick();
    }
	
	public void draw(Graphics g, Camera camera){
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        img = Game.spritesheet.getSprite((int)(width * frame), 0, 32, 32);
        Vector2 iso = IsoCalculator.twoDToIso(new Vector3(x,y, z));
        Vector2 isofeet = IsoCalculator.twoDToIso(feet);

        g.setColor(new Color(0, 0, 0, (int)(TileMap.max_darkness * 110 + 40)));
        g.fillOval((int)((iso.x - xOffset) * scale),(int)((iso.y  + isofeet.y - 6 - yOffset-z)* scale),(int)(width*scale*imgscale),(int)(height*scale*imgscale)/2);
        g.drawImage(img, (int)((iso.x - xOffset)*scale), (int)((iso.y-yOffset)*scale),(int)(width*scale*imgscale),(int)(height*scale*imgscale), null);

        fireball.draw(g, camera);
        drawDebug(g,camera);
    }

    public void drawDebug(Graphics g, Camera camera) {
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        Vector2 iso = IsoCalculator.twoDToIso(new Vector3(x,y, 0));
        Vector2 isofeet = IsoCalculator.twoDToIso(feet);
        Vector2 isodest = IsoCalculator.twoDToIso(new Vector3(dest_x,dest_y, 0));

        g.setColor(Color.green);
      //  g.drawRect((int) ((iso.x - xOffset) * scale), (int) ((iso.y - yOffset) * scale), (int) (width * imgscale * scale), (int) (height * imgscale * scale)); //draws rectangle around char
       // g.fillRect((int)((iso.x-xOffset + isofeet.x)*scale), (int)((iso.y - yOffset + isofeet.y)*scale),5,5);  // draws rect at character's "feet"
        if (Math.abs(dx) > 0 || Math.abs(dy) > 0) {
            g.drawOval((int)((isodest.x + isofeet.x - xOffset)*scale), (int) ((isodest.y + isofeet.y - yOffset) * scale),(int)(1.2*scale*2)+1,(int)(1.2*scale)+1); // draws rectangle at character's destination point
        }

     //   g.drawString(TileMap.currentx + ", " + TileMap.currenty, (int) ((iso.x - xOffset) * scale + (int) (width * imgscale * scale * 1.05)), (int) ((iso.y - yOffset) * scale) + 20);
    }


    public void move(double dest_x, double dest_y)
    {
        if (Util.findDistance(dest_x - x - feet.x, dest_y - feet.y - y) > velocity) {
            this.dest_x = dest_x - feet.x;
            this.dest_y = dest_y - feet.y;

            Direction dir = Util.findSlope(x, y, this.dest_x, this.dest_y);

            dx = Util.findDX(velocity, dir.slope) * dir.xdir;
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
