package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.input.InputComponent;
import com.dreamstreet.arpg.item.Fireball;
import com.dreamstreet.arpg.item.Item;
import com.dreamstreet.arpg.obj.HitCircle;
import com.dreamstreet.arpg.ui.DayCycle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite implements Comparable<Sprite>{

    private Vector3 pos;   //position
    private Vector2 dest;  //destination
    private Vector3 vel;   //velocity
    private Vector3 dim;   //dimensions

    private HitCircle hit;

    private float MAX_VELOCITY;


    private BufferedImage img;
    private InputComponent input;
    public double imgscale;
    private int frame = 0;
    private int animation_timer = 3;

	public Item fireball = new Item();

	public Sprite(BufferedImage img, InputComponent input, double imgscale, Vector3 pos) {
        input.setCharacter(this);

        this.img = img;
        this.input = input;
        this.imgscale = imgscale;

        this.pos = pos;
        this.dest = new Vector2(pos.x, pos.y);

        this.MAX_VELOCITY = 2;
        this.vel = new Vector3(0,0,0);

        this.dim = new Vector3(img.getWidth() * imgscale, img.getWidth() * imgscale, img.getHeight() * imgscale);

        this.hit = new HitCircle(new Vector2(0,0), dim.x / 2);
    }

    public void tick() {
        input.tick();

        if ((pos.x + vel.x > dest.x && vel.x > 0) || (pos.x + vel.x < dest.x && vel.x < 0)) {

            vel.x = 0;
            pos.x = dest.x;
            frame = 0;
            animation_timer = 3;
        }
        if ((pos.y + vel.y > dest.y && vel.y > 0) || (pos.y + vel.y < dest.y && vel.y < 0)) {

            vel.y = 0;
            pos.y = dest.y;
            frame = 0;
            animation_timer = 3;
        }

        if (pos.z < 0 || vel.z < 0) {
            vel.z += TileMap.GRAVITY;
            pos.z += vel.z;
        }
        if (pos.z > 0) {
            pos.z = 0;
            vel.z = 0;
        }

        Tile curr = TileMap.getTile(pos.x + vel.x, pos.y);
        if (curr != null && curr.walkable) {
            pos.x += vel.x;
        }

        curr = TileMap.getTile(pos.x, pos.y + vel.y);
        if (curr != null && curr.walkable) {
            pos.y += vel.y;
        }


        if (vel.x != 0 || vel.y != 0) {
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

        img = Game.spritesheet.getSprite((int)(dim.x/imgscale * frame), 0, 32, 32);
        Vector2 iso = IsoCalculator.twoDToIso(pos);

        g.setColor(new Color(0, 0, 0, (int)(DayCycle.max_darkness * 110 + 40)));
       // g.fillOval((int)((iso.x - xOffset) * scale),(int)((iso.y  + isofeet.y - 6 - yOffset-pos.z)* scale),(int)(width*scale),(int)(height*scale)/2);
        g.drawImage(img, (int)((iso.x - xOffset)*scale - .5), (int)((iso.y - dim.z - yOffset)*scale - .5),(int)(dim.x*scale - .5),(int)(dim.z*scale - .5), null);

        fireball.draw(g, camera);
    }

    public void drawDebug(Graphics g, Camera camera) {
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        Vector2 iso = IsoCalculator.twoDToIso(pos);
        Vector2 isoxy = IsoCalculator.twoDToIso(new Vector3(pos.x,pos.y,0));
        Vector2 isodest = IsoCalculator.twoDToIso(new Vector3(dest.x,dest.y, 0));

        g.setColor(Color.green);
      //  g.drawRect((int) ((iso.x - xOffset) * scale), (int) ((iso.y - yOffset) * scale), (int) (width * imgscale * scale), (int) (height * imgscale * scale)); //draws rectangle around char
        g.fillRect((int)((isoxy.x-xOffset)*scale), (int)((isoxy.y - yOffset)*scale),5,5);  // draws rect at character's "feet"
        if (Math.abs(vel.x) > 0 || Math.abs(vel.y) > 0) {
            g.drawOval((int)((isodest.x - xOffset - 1.2)*scale), (int) ((isodest.y - yOffset - .6) * scale),(int)(1.2*scale*2)+1,(int)(1.2*scale)+1); // draws rectangle at character's destination point
        }

        //g.drawOval((int)((pos.x + hit.getCenter().x - hit.getRadius()) * scale), (int)((pos.y + hit.getCenter().y  - hit.getRadius()) * scale), (int)(hit.getRadius() * 2 * scale), (int)(hit.getRadius() * 2 * scale));
        g.setColor(Color.red);
        g.drawOval((int)((isoxy.x + hit.getCenter().x - hit.getRadius() * 7 / 5 - xOffset) * scale), (int)((isoxy.y + hit.getCenter().y  - hit.getRadius() * 7 / 10 - yOffset) * scale), (int)(hit.getRadius() * 14 / 5 * scale), (int)(hit.getRadius() * 7 / 5 * scale));

     //   g.drawString(TileMap.currentx + ", " + TileMap.currenty, (int) ((iso.x - xOffset) * scale + (int) (width * imgscale * scale * 1.05)), (int) ((iso.y - yOffset) * scale) + 20);
    }


    public void move(Vector2 dest)
    {
        if (Util.findDistance(dest.x - pos.x, dest.y - pos.y) > MAX_VELOCITY * 1.1) {
            this.dest.x = dest.x;
            this.dest.y = dest.y;

            Direction dir = Util.findSlope(pos.x, pos.y, this.dest.x, this.dest.y);

            vel.x = Util.findX(MAX_VELOCITY, dir.slope) * dir.xdir;
            vel.y = dir.slope * vel.x;

            if (dir.slope == 200000 || dir.slope == -200000)
            {
                vel.y = MAX_VELOCITY * dir.slope / Math.abs(dir.slope);
            }
        }
    }



    public void stop() {
        frame = 0;
        animation_timer = 3;
        dest.x = pos.x;
        dest.y = pos.y;
        vel.x = 0;
        vel.y = 0;
    }

    public void jump() {
        vel.z = -4.5;
    }

    public double getX() {
        return pos.x;
    }

    public void setX(double x) {
        this.pos.x = x;
        dest.x = x;
    }

    public double getY() {
        return pos.y;
    }

    public void setY(double y) {
        this.pos.y = y;
        dest.y = y;
    }

    public double getZ() {
        return pos.z;
    }

    public double getDy() {
        return vel.y;
    }

    public double getDx() {
        return vel.x;
    }

    public void setDy(double dy) {
        this.vel.y = dy;
    }

    public void setDx(double dx) {
        this.vel.x = dx;
    }

    public double getDest_y() {
        return dest.y;
    }

    public double getDest_x() {
        return dest.x;
    }

    public double getWidth() {
        return dim.x;
    }

    public double getHeight() {
        return dim.z;
    }

    public void setInput(InputComponent input) {
        this.input = input;
        input.setCharacter(this);
    }

    public InputComponent getInput() {
        return input;
    }

    @Override
    public int compareTo(Sprite o) {
        if (this.pos.x + this.pos.y < o.getX() + o.getY()) {
            return -1;
        }else if (this.pos.x + this.pos.y > o.getX() + o.getY()) {
            return 1;
        }
        return 0;
    }

    public boolean collidesWith(Sprite other) {


        Vector2 hitCenter = hit.getCenter();
        Vector2 thistemp = new Vector2(pos.x + hitCenter.x, pos.y + hitCenter.y);

        HitCircle o = other.getHit();
        Vector2 oCenter = o.getCenter();
        Vector2 otemp = new Vector2(other.getX() + oCenter.x, other.getY() + oCenter.y);
       // System.out.println(Util.findDistance(thistemp.x - otemp.x, thistemp.y - otemp.y));

        if (Util.findDistance(thistemp.x - otemp.x, thistemp.y - otemp.y) <= (hit.getRadius() + o.getRadius())) {

            return true;
        }
        return false;
    }

    public boolean collidesWith(Fireball other) {


        Vector2 hitCenter = hit.getCenter();
        Vector2 thistemp = new Vector2(pos.x + hitCenter.x, pos.y + hitCenter.y);

        HitCircle o = other.getHit();
        Vector2 oCenter = o.getCenter();
        Vector2 otemp = new Vector2(other.getX() + oCenter.x, other.getY() + oCenter.y);
        // System.out.println(Util.findDistance(thistemp.x - otemp.x, thistemp.y - otemp.y));

        if (Util.findDistance(thistemp.x - otemp.x, thistemp.y - otemp.y) <= (hit.getRadius() + o.getRadius())) {
            return true;
        }
        return false;
    }

    public HitCircle getHit() {
        return hit;
    }
}



/*
 public Vector3 feet;
    feet = IsoCalculator.isoTo2D(new Vector2((dim.x/2), (dim.z/8*7)));

 */
