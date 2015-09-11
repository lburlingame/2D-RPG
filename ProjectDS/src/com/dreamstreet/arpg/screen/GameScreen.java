package com.dreamstreet.arpg.screen;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.gfx.Camera;
import com.dreamstreet.arpg.gfx.TileMap;
import com.dreamstreet.arpg.gfx.Vector3;
import com.dreamstreet.arpg.gfx.particle.ParticleEmitter;
import com.dreamstreet.arpg.input.*;
import com.dreamstreet.arpg.item.Fireball;
import com.dreamstreet.arpg.item.Item;
import com.dreamstreet.arpg.item.Nova;
import com.dreamstreet.arpg.obj.Coin;
import com.dreamstreet.arpg.obj.Entity;
import com.dreamstreet.arpg.obj.HealthGlobe;
import com.dreamstreet.arpg.sfx.AudioPlayer;
import com.dreamstreet.arpg.ui.DayCycle;
import com.dreamstreet.arpg.ui.MessageBox;
import com.dreamstreet.arpg.ui.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created on 8/28/2015.
 */
public class GameScreen implements Screen {

    private Game game;

    private String NAME = "GAME";

    public static boolean showhealth = true;

    public static boolean pause = false;


    //map
    private TileMap map = new TileMap("res/levels/test_map.txt");

    private Camera camera = new Camera(null);



    //spritesheets for map and character



    // this all goes into level
    private ArrayList<Entity> chars = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<HealthGlobe> globes = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();

    private Entity SELECTED;

    private float orbchance = .05f;

    private UI ui;
    private DayCycle dayCycle = new DayCycle(Game.dimension.width - 96, 64, 48);

    public AudioPlayer music = new AudioPlayer("res/audio/clocktown-day1.wav");
    public boolean audioPlay = false;
    private int selected_ix = 0;

    private MessageBox box1 = new MessageBox("This is wonderful! How wonderful!");
    public ParticleEmitter emitter = new ParticleEmitter();
    private WindowInput win;


    public GameScreen(Game game) {
        this.game = game;

        win = new WindowInput(game, this,  camera);
        Entity character = new Entity(1, new PlayerInput(game, camera), 1.0, new Vector3(50,50,0));
        ui = new UI(character);

        Entity skulltula = new Entity(1, new AIInput(game), 2.0, new Vector3(170,180,0));
        chars.add(character);
        chars.add(skulltula);
        for (int i = 0; i < 200; i++) {
            chars.add(new Entity(1, new AIInput(game), 1, new Vector3((double)((int)(Math.random()*300)), (double)((int)(Math.random() * 300)), 0)));
        }

        SELECTED = chars.get(0);
        //  chars[2] = character1;
        // chars[3] = character2;
        //  chars[4] = character3;

        //System.setProperty("sun.java2d.opengl","True");
        // System.setProperty("sun.java2d.pmoffscreen","False");
        //System.setProperty("sun.java2d.translaccel","True");

        camera.setTarget(character);
        if (audioPlay) {
            music.start();
        }else{
            music.stop();
        }

    }


    public void tick() {
        win.tick();
        if (!pause) {
            for (int i = 0; i < chars.size(); i++) {
                chars.get(i).tick();
            }
            Collections.sort(chars);
            
            for (int i = 0; i < coins.size(); i++) {
                coins.get(i).tick();
                if (coins.get(i).collidesWith(SELECTED)) {
                    SELECTED.grabGold(coins.get(i).getAmount());
                    coins.remove(i);
                }
            }

            for (int i = 0; i < globes.size(); i++) {
                globes.get(i).tick();
                if (globes.get(i).collidesWith(SELECTED)) {
                    SELECTED.heal(200);
                    globes.remove(i);
                }
            }

            for (int i = 0; i < chars.size()-1; i++) {
                ArrayList<Fireball> current = chars.get(i).fireball.getFireballs();
                ArrayList<Nova> currentn = chars.get(i).nova.getNovas();

                for (int j = i+1; j < chars.size(); j++) {
                    boolean alive = true;

                    for (int k = 0; k < current.size(); k++) {
                        if (chars.get(j).collidesWith(current.get(k)) && current.get(k).isActive()) {
                            emitter.bloodSpatter(new Vector3(chars.get(j).getX(), chars.get(j).getY(), chars.get(j).getZ() - chars.get(j).getDimensions().z / 2), new Vector3(Math.random() * 3 - 1.5, Math.random() * 3 - 1.5, -Math.random() * 3));
                            alive = chars.get(j).takeDamage(500);
                            if (!alive) {
                                break;
                            }
                        }
                    }

                    for (int k = 0; k < currentn.size(); k++) {
                        if (chars.get(j).collidesWith(currentn.get(k))) {
                            emitter.bloodSpatter(new Vector3(chars.get(j).getX(), chars.get(j).getY(), chars.get(j).getZ() - chars.get(j).getDimensions().z / 2), new Vector3(Math.random() * 3 - 1.5, Math.random() * 3 - 1.5, -Math.random() * 3));
                            alive = chars.get(j).takeDamage(1000);
                            if (!alive) {
                                break;
                            }
                        }
                    }

                    ArrayList<Fireball> other = chars.get(j).fireball.getFireballs();
                    ArrayList<Nova> othern = chars.get(j).nova.getNovas();
                    for (int k = 0; k < other.size(); k++) {
                        if (chars.get(i).collidesWith(other.get(k)) && other.get(k).isActive()) {
                            emitter.bloodSpatter(new Vector3(chars.get(i).getX(), chars.get(i).getY(), chars.get(i).getZ() - chars.get(i).getDimensions().z / 2), new Vector3(Math.random() * 3 - 1.5, Math.random() * 3 - 1.5, -Math.random() * 3));
                            if (!chars.get(i).takeDamage(500)) {
                                coins.add(new Coin(chars.get(i).getPosition(), new Vector3(Math.random() * 3 - 1.5,Math.random() * 3 - 1.5,-3.5), chars.get(i).getGold()));
                                if (Math.random() < orbchance) {
                                    globes.add(new HealthGlobe(chars.get(i).getPosition(), new Vector3(Math.random() * 3 - 1.5,Math.random() * 3 - 1.5,-3.5), 100));
                                }
                                chars.remove(i);
                                i--;
                                break;
                            }
                        }
                    }

                    for (int k = 0; k < othern.size(); k++) {
                        if (chars.get(i).collidesWith(othern.get(k))) {
                            emitter.bloodSpatter(new Vector3(chars.get(i).getX(), chars.get(i).getY(), chars.get(i).getZ() - chars.get(i).getDimensions().z / 2), new Vector3(Math.random() * 3 - 1.5, Math.random() * 3 - 1.5, -Math.random() * 3));
                            if (!chars.get(i).takeDamage(1000)) {
                                coins.add(new Coin(chars.get(i).getPosition(), new Vector3(Math.random() * 3 - 1.5,Math.random() * 3 - 1.5,-3.5), chars.get(i).getGold()));
                                if (Math.random() < orbchance) {
                                    globes.add(new HealthGlobe(chars.get(i).getPosition(), new Vector3(Math.random() * 3 - 1.5,Math.random() * 3 - 1.5,-3.5), 100));
                                }
                                chars.remove(i);

                                i--;
                                break;
                            }
                        }
                    }

                    if (!alive) {
                        coins.add(new Coin(chars.get(j).getPosition(), new Vector3(Math.random() * 3 - 1.5,Math.random() * 3 - 1.5,-3.5), chars.get(j).getGold()));
                        if (Math.random() < orbchance) {
                            globes.add(new HealthGlobe(chars.get(j).getPosition(), new Vector3(Math.random() * 3 - 1.5,Math.random() * 3 - 1.5,-3.5), 100));
                        }
                        chars.remove(j);
                        j--;

                    }
                }
            }
            emitter.tick();
            dayCycle.tick();

        }

        map.tick();
        camera.tick();

        ui.tick();
        // box1.tick();
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, (int) Game.dimension.getWidth(), (int) Game.dimension.getHeight());
        map.draw(g, camera, SELECTED, dayCycle);
        emitter.draw(g, camera);
        // right after sorting
        // a custom binary search that searches only based on y values, for the index of characters that are within the camera y values
        // so, it would search for index that is at camera.offset.y, then -- until it hits the first index that is within that bound,
        // then it would search index that is at camera.offset.y + camera.viewport.y, and ++ until it hits value that is within, then it would only draw within those bounds
        // inside the draw there would be a comparison to make sure it is still within the camera's x bounds

        selected_ix = chars.indexOf(SELECTED);

        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).draw(g, camera);
        }

        for (int i = 0; i < globes.size(); i++) {
            globes.get(i).draw(g, camera);
        }

        for (int i = 0; i < chars.size(); i++) {
            chars.get(i).draw(g, camera);
        }

        ui.draw(g);
        dayCycle.draw(g);



        if (Game.debug) {
            drawDebug(g);
        }



        // box1.draw(g);

    }


    public void drawDebug(Graphics g) {

        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.setColor(Color.WHITE);

        Vector3 curr = new Vector3(SELECTED.getX(), SELECTED.getY(), 0);


        //   g.drawString(TileMap.currentx + ", " + TileMap.currenty, 20, 100);

    /*
    g.drawString(character.getDest_x() + ", " + character.getDest_y(), 20, 100);
    g.drawString(selected_ix.x + ", " + selected_ix.y, 20, 130);*/
        // g.drawString(dayCycle.time, Game.WIDTH * Game.SCALE - 100, 40);

        // g.drawString(camera.getScale() + " ", 20, 160);
     /*   */


    }


    public void debug(DebugLevel level, String msg) {
        switch (level) {
            default:
            case INFO:
                if (Game.debug) {
                    System.out.println("[" + NAME + "] " + msg);
                }
                break;
            case WARNING:
                System.out.println("[" + NAME + "] " + msg);
                break;
            case SEVERE:
                System.out.println("[" + NAME + "] " + msg);
                game.stop();
                game.frame.dispose();
                System.exit(-1);
                break;
        }
    }


    public static enum DebugLevel {
        INFO, WARNING, SEVERE
    }

/*

    public void changeCharacter() {
        int prev = selected_ix;
        selected_ix++;
        selected_ix = selected_ix % chars.size();
        System.out.println(selected_ix);
        camera.setTarget(chars.get(selected_ix));
        SELECTED = chars.get(selected_ix);

        chars.get(selected_ix).setInput(chars.get(prev).getInput());
        chars.get(prev).setInput(new AIInput(this));
    }
*/



}
