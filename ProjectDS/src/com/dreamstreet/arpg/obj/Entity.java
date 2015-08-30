package com.dreamstreet.arpg.obj;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.*;
import com.dreamstreet.arpg.input.InputComponent;
import com.dreamstreet.arpg.input.PlayerInput;
import com.dreamstreet.arpg.item.Item;
import com.dreamstreet.arpg.screen.GameScreen;
import com.dreamstreet.arpg.util.Util;

import java.awt.*;

import static com.dreamstreet.arpg.gfx.TileMap.*;

// enemy aggro could be cone based on vision, and circular based on noise, your noise level depends on whether you are running or walking or sprinting, and maybe even have a talent tree that reduces noise and stuff
// vision is reduced in poorly lit areas

//only calculates lighting on tiles that are either on screen, or in between the player and an enemy that is in aggro range
public class Entity extends Collidable implements Comparable<Entity>{

    public static final int STOPPED = 0;
    public static final int WALKING = 1;
    public static final int RUNNING = 2;
    public static final int SPRINTING = 3;
    public static final int DASHING =  4;
    public static final int JUMPING = -1;
    private int STATE = 0;
    private int PREV_STATE = 0; // ??


    private int id;

    private Vector2 dest;  //destination
    private Vector3 dim;   //dimensions
    private double smult; // size multiplier

    private HitCircle hit;
    private float BASE_VELOCITY;


    private float vmult = 1;  // velocity multiplier
    private GraphicsComponent gfx;


    private InputComponent input;


    public Item fireball = new Item();
    private EntityInfo info;



    private int gold;

    public Entity(int id, InputComponent input, double smult, Vector3 pos) {
        input.setCharacter(this);
        gfx = new GraphicsComponent(this);

        info = new EntityInfo();

        this.id = id;

        this.input = input;
        this.smult = smult;

        this.pos = pos;
        this.dest = new Vector2(pos.x, pos.y);
        STATE = STOPPED;
        this.BASE_VELOCITY = 3;
        this.vel = new Vector3(0,0,0);

        this.dim = new Vector3(32 * smult, 32 * smult, 32 * smult);

        this.hit = new HitCircle(new Vector3(0, -dim.z/8, 0), dim.x / 3);
        gold = (int)(Math.random() * 58) + 5;
    }

    public void changeSize() {
        smult *= 1.03;
        dim.x = 32 * smult;
        dim.y = 32 * smult;
        dim.z = 32 * smult;
        hit = new HitCircle(new Vector3(0, -dim.z/8, 0), dim.x / 3);
    }

    public void tick() {
        input.tick();
/*
        if ((pos.x + (vel.x * vmult) > dest.x && vel.x > 0) || (pos.x + (vel.x  * vmult) < dest.x && vel.x < 0)) {
            vel.x = 0;
            pos.x = dest.x;
        }
        if ((pos.y + (vel.y * vmult) > dest.y && vel.y > 0) || (pos.y + (vel.y * vmult) < dest.y && vel.y < 0)) {
            vel.y = 0;
            pos.y = dest.y;
        }*/

        if (pos.z < 0 || vel.z < 0) {
            vel.z += GRAVITY;
            pos.z += vel.z;
        }

        if (pos.z > 0) {
            pos.z = 0;
            vel.z = 0;
            STATE = PREV_STATE;
        }

        Tile curr = getTile(pos.x + (vel.x * vmult), pos.y);
        if (curr != null && curr.walkable) {
            pos.x += (vel.x * vmult);
        }

        curr = getTile(pos.x, pos.y + (vel.y * vmult));
        if (curr != null && curr.walkable) {
            pos.y += (vel.y * vmult);
        }


        if ((vel.x != 0 || vel.y != 0) && STATE != JUMPING) {
            if (vmult < .75f) {
                STATE = WALKING;
            }else if (vmult >= .75f && vmult <= 1.25f) {
                STATE = RUNNING;
            }else if (vmult > 1.25f) {
                STATE = SPRINTING;
            }
        }else if (STATE != JUMPING){
            STATE = STOPPED;
        }

        gfx.tick();
        fireball.tick();
    }

    public void draw(Graphics2D g, Camera camera){
        Vector3 offset = camera.getOffset();
        double scale = camera.getScale();

        gfx.draw(g, camera);
        fireball.draw(g, camera);

        if (Game.debug || input instanceof PlayerInput) {

            if (Game.debug)
            {
                drawDebug(g, camera);
            }
            if (Math.abs(vel.x) > 0 || Math.abs(vel.y) > 0) {
          //      g.setColor(Color.green);
         //       g.drawOval((int) ((dest.x - offset.x - 1.2) * scale), (int) ((dest.y - offset.y - .6) * scale), (int) (1.2 * scale * 2) + 1, (int) (1.2 * scale) + 1);
            }
            if (input instanceof PlayerInput) {
                g.setColor(Color.white);
               // Vector2 point = ((PlayerInput) input).getScreenLoc();
                //g.drawString(point.x + ", " + point.y, 20, 70);
               // float angle = Util.getAngle(new Vector2(pos.x, pos.y), point);
                //g.drawString(angle + " ", 20, 100);
                g.drawString(STATE + " ", 20, 130);
            }
        }


        if (GameScreen.showhealth == true) {
            g.setColor(Color.red);
            g.fillRect((int) ((pos.x - offset.x - dim.x / 2) * scale), (int) ((pos.y - offset.y - dim.y + pos.z + (dim.y * scale * .05)) * scale), (int) (dim.x* scale), (int) (4 * scale));  // draws rect at character's "feet"

            g.setColor(Color.green);
            g.fillRect((int) ((pos.x - offset.x - dim.x / 2) * scale), (int) ((pos.y - offset.y - dim.y + pos.z + (dim.y * scale * .05)) * scale), (int) ((double) info.getCurrHealth() / info.getMaxHealth() * dim.x * scale), (int) (4 * scale));  // draws rect at character's "feet"
        }
    }

    public void drawDebug(Graphics g, Camera camera) {
        Vector3 offset = camera.getOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double zOffset = offset.z;
        double scale = camera.getScale();

        g.setColor(Color.green);
        //  g.drawRect((int) ((iso.x - xOffset) * scale), (int) ((iso.y - yOffset) * scale), (int) (width * smult * scale), (int) (height * smult * scale)); //draws rectangle around char
        g.fillRect((int) ((pos.x - xOffset) * scale), (int) ((pos.y - yOffset) * scale), 5, 5);  // draws rect at character's "feet"


        //g.drawOval((int)((pos.x + hit.getCenter().x - hit.getRadius()) * scale), (int)((pos.y + hit.getCenter().y  - hit.getRadius()) * scale), (int)(hit.getRadius() * 2 * scale), (int)(hit.getRadius() * 2 * scale));
        g.setColor(Color.red);
        g.drawOval((int) ((pos.x + hit.getCenter().x - hit.getRadius() - xOffset) * scale), (int) ((pos.y + hit.getCenter().y - hit.getRadius() - yOffset) * scale), (int) (hit.getRadius() * 2 * scale), (int) (hit.getRadius() * 2 * scale));

        //   g.drawString(TileMap.currentx + ", " + TileMap.currenty, (int) ((iso.x - xOffset) * scale + (int) (width * smult * scale * 1.05)), (int) ((iso.y - yOffset) * scale) + 20);
    }


    public void move(Vector2 dest)
    {
        if (Util.findDistance(dest.x - pos.x, dest.y - pos.y) > BASE_VELOCITY * 1.1) {
            this.dest.x = dest.x;
            this.dest.y = dest.y;

            Direction dir = Util.findSlope(pos.x, pos.y, this.dest.x, this.dest.y);

            vel.x = Util.findX(BASE_VELOCITY, dir.slope) * dir.xdir;
            vel.y = dir.slope * vel.x;

            if (dir.slope == 200000 || dir.slope == -200000)
            {
                vel.y = BASE_VELOCITY * dir.slope / Math.abs(dir.slope);
            }
        }
    }


    public void walk() {
        vmult = .1f;//.5f;
    }

    public void run() {
        vmult = 1.0f;
    }

    public void sprint() {
        vmult = 1.5f;//1.5f;
    }

    public void stop() {
        dest.x = pos.x;
        dest.y = pos.y;
        vel.x = 0;
        vel.y = 0;
    }

    public void jump() {
        if (STATE != JUMPING) {
            vel.z = -4.5;
            PREV_STATE = STATE;
            STATE = JUMPING;
        }
    }

    public Vector3 getPosition() {
        return new Vector3(pos.x,pos.y,pos.z);
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
    public int compareTo(Entity o) {
        if (this.pos.y < o.getY()) {
            return -1;
        }else if (this.pos.y >  o.getY()) {
            return 1;
        }
        return 0;
    }

    public boolean collidesWith(Collidable other) {
        Vector3 hitCenter = hit.getCenter();

        Vector3 opos = other.getPosition();
        HitCircle o = other.getHit();
        Vector3 oCenter = o.getCenter();

        if (Util.findDistance((pos.x + hitCenter.x) - (opos.x + oCenter.x), (pos.y + hitCenter.y) - (opos.y + oCenter.y)) <= (hit.getRadius() + o.getRadius())) {
            return true;
        }

        return false;
    }

    public boolean takeDamage(int damage) {
        return info.takeDamage(damage);
    }

    public void heal(int amount) {
        info.heal(amount);
    }

    public HitCircle getHit() {
        return hit;
    }

    public Vector3 getDimensions() {
        return new Vector3(dim.x, dim.y, dim.z);
    }

    public int getState() {
        return STATE;
    }

    public int getID() {
        return id;
    }

    public EntityInfo getInfo() {
        return info;
    }

    public int getGold() {
        return gold;
    }

    public void grabGold(int gold) {
        this.gold += gold;
    }
}


/* attack(Entity target)

    target.takeDamage(AttackReport rep)

    sprite implements collidable
    fireball implements collidable

 */


/*
 public Vector3 feet;
    feet = IsoCalculator.isoTo2D(new Vector2((dim.x/2), (dim.z/8*7)));

 */
