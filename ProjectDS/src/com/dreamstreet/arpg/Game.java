package com.dreamstreet.arpg;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.dreamstreet.arpg.gfx.*;
import com.dreamstreet.arpg.gfx.particle.ParticleEmitter;
import com.dreamstreet.arpg.input.NPCInput;
import com.dreamstreet.arpg.input.PlayerInput;
import com.dreamstreet.arpg.input.WindowInput;
import com.dreamstreet.arpg.item.Fireball;
import com.dreamstreet.arpg.obj.Entity;
import com.dreamstreet.arpg.sfx.AudioPlayer;
import com.dreamstreet.arpg.ui.DayCycle;
import com.dreamstreet.arpg.ui.MessageBox;
import com.dreamstreet.arpg.ui.UI;

import java.util.ArrayList;
import java.util.Collections;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	//public static final int WIDTH = 640;  // 1920x1080
	public static final int WIDTH = 480; // 480
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE = 3;
    public static final Dimension dimension = new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
	public static final String NAME = "Dream Street";
    public static final String TICKS = "00";

    public JFrame frame;
    private Thread thread;

	private boolean running = false;
    public static boolean debug = false;

    public boolean pause = false;


	//map
	private TileMap map = new TileMap("res/levels/test_map.txt");

    private Camera camera = new Camera(null);
    private WindowInput win = new WindowInput(this, camera);

    int fps = 0;


    //spritesheets for map and character

    private Entity character = new Entity(1, new PlayerInput(this, camera), 1.0, new Vector3(50,50,0));
    private Entity skulltula = new Entity(1, new NPCInput(this), 2.0, new Vector3(170,170,0));
    private Entity character1 = new Entity(1, new NPCInput(this), 1.0, new Vector3(50,50,0));
    private Entity character2 = new Entity(1, new NPCInput(this), 1.0, new Vector3(180,20,0));
    private Entity character3 = new Entity(1, new NPCInput(this), 1.0, new Vector3(20,180,0));

    private ArrayList<Entity> chars = new ArrayList<>();
    private Entity SELECTED;

    private UI ui = new UI(character);
    private DayCycle dayCycle = new DayCycle(dimension.width - 96, 64, 48);

    public AudioPlayer music = new AudioPlayer("res/audio/clocktown-day1.wav");
    public boolean audioPlay = false;
    private int curr = 0;

    private MessageBox box1 = new MessageBox("This is wonderful! How wonderful!");
    public ParticleEmitter emitter = new ParticleEmitter();
    public Game() {
        chars.add(character);
        chars.add(skulltula);
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

	@Override
	public void run() {
		//limit fps approx. 60
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;

		int ticks = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime)/nsPerTick;
			lastTime = now;
			boolean shouldRender = false; // false here limits to 60 fps

			while(delta>=1){
				ticks++;
                tick();
				delta--;
				shouldRender = true;
			}

			try{
				Thread.sleep(2);
			}catch(InterruptedException e){
				e.printStackTrace();
			}

            if(shouldRender){
                frames++;
                render();
            }

			if(System.currentTimeMillis()-lastTimer >= 1000){
				lastTimer+=1000;
				fps = frames;
				frames = 0;
                frame.setTitle("" + ticks );
				ticks = 0;
			}
		}

	}


	public void tick() {
        if (!pause) {
            for (int i = 0; i < chars.size(); i++) {
                chars.get(i).tick();
            }

            for (int i = 0; i < chars.size()-1; i++) {
                ArrayList<Fireball> current = chars.get(i).fireball.getFireballs();

                for (int j = i+1; j < chars.size(); j++) {
                /*if (chars.get(i).collidesWith(chars.get(j))) {
                    emitter.bloodSpatter(new Vector3(chars.get(i).getX() - chars.get(i).getWidth() / 2, chars.get(i).getY() - chars.get(i).getHeight() / 2, chars.get(i).getZ() - 15), new Vector3(Math.random() * 12 - 6, Math.random() * 12 - 6, -Math.random() * 3));
                }*/

                    boolean alive = true;
//                    emitter.bloodSpatter(new Vector3(chars.get(j).getX(), chars.get(j).getY(), chars.get(i).getZ() - chars.get(j).getDimensions().z / 2), new Vector3(Math.random() * 12 - 6, Math.random() * 12 - 6, -Math.random() * 3));

                    for (int k = 0; k < current.size(); k++) {
                        if (chars.get(j).collidesWith(current.get(k)) && current.get(k).isActive()) {
                            emitter.bloodSpatter(new Vector3(chars.get(j).getX(), chars.get(j).getY(), chars.get(j).getZ() - chars.get(j).getDimensions().z / 2), new Vector3(Math.random() * 12 - 6, Math.random() * 12 - 6, -Math.random() * 3));
                            alive = chars.get(j).takeDamage(15);
                            if (!alive) {
                                break;
                            }
                        }
                    }

                    ArrayList<Fireball> other = chars.get(j).fireball.getFireballs();
                    for (int k = 0; k < other.size(); k++) {
                        if (chars.get(i).collidesWith(other.get(k)) && other.get(k).isActive()) {
                            emitter.bloodSpatter(new Vector3(chars.get(i).getX(), chars.get(i).getY(), chars.get(i).getZ() - chars.get(i).getDimensions().z / 2), new Vector3(Math.random() * 12 - 6, Math.random() * 12 - 6, -Math.random() * 3));
                            if (!chars.get(i).takeDamage(15)) {
                                chars.remove(i);
                                break;
                            }

                        }


                    }

                    if (!alive) {
                        chars.remove(j);
                    }

                }
            }
            emitter.tick();
            dayCycle.tick();

        }


        camera.tick();

        map.tick();

        ui.tick();

        box1.tick();
    }

	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
        Graphics2D g = (Graphics2D)bs.getDrawGraphics();

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0,0,WIDTH*SCALE+100,HEIGHT*SCALE+100);
		map.draw(g,camera,character, dayCycle);
        emitter.draw(g, camera);
        Collections.sort(chars);

        curr = chars.indexOf(SELECTED);

        for (int i = 0; i < chars.size(); i++) {
            chars.get(i).draw(g, camera);
        }


        /*
		kodama.draw(g,camera);
        kodama1.draw(g,camera);/]

        kodama2.draw(g, camera);
        kodama3.draw(g, camera);
        skeleton.draw(g,camera);
        noface.draw(g,camera);
        */
        ui.draw(g);
        dayCycle.draw(g);

        if (debug) {
            drawDebug(g);
        }


       // SELECTED.drawDebug(g, camera);

       // box1.draw(g);
		g.dispose();
		bs.show();
	}


	public void drawDebug(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.setColor(Color.WHITE);
        Vector3 curr = new Vector3(character.getX(), character.getY(), 0);

        g.drawString(fps + " ", 20, 40);

                //   g.drawString(TileMap.currentx + ", " + TileMap.currenty, 20, 100);

        /*
        g.drawString(character.getDest_x() + ", " + character.getDest_y(), 20, 100);
        g.drawString(curr.x + ", " + curr.y, 20, 130);*/
                // g.drawString(dayCycle.time, Game.WIDTH * Game.SCALE - 100, 40);

                // g.drawString(camera.getScale() + " ", 20, 160);
                g.drawLine(WIDTH / 12 * 5 * SCALE, HEIGHT / 2 * SCALE, WIDTH / 12 * 7 * SCALE, HEIGHT / 2 * SCALE);
        g.drawLine(WIDTH/2*SCALE,HEIGHT/12*5*SCALE,WIDTH/2*SCALE,HEIGHT/12*7*SCALE);


    }

    public static float getAngle(Vector2 center, Vector2 target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.y - center.y, target.x - center.x));

        if(angle < 0){
            angle += 0;
        }

        return angle;
    }


    public void debug(DebugLevel level, String msg) {
        switch (level) {
            default:
            case INFO:
                if (debug) {
                    System.out.println("[" + NAME + "] " + msg);
                }
                break;
            case WARNING:
                System.out.println("[" + NAME + "] " + msg);
                break;
            case SEVERE:
                System.out.println("[" + NAME + "] " + msg);
                this.stop();
                frame.dispose();
                break;
        }
    }




    public boolean isRunning() {
        return running;
    }


    public synchronized void start() {
        running = true;
        thread = new Thread(this, NAME + "_main");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static enum DebugLevel {
        INFO, WARNING, SEVERE
    }

    public void changeCharacter() {
        int prev = curr;
        curr++;
        curr = curr % chars.size();
        System.out.println(curr);
        camera.setTarget(chars.get(curr));
        SELECTED = chars.get(curr);

        chars.get(curr).setInput(chars.get(prev).getInput());
        chars.get(prev).setInput(new NPCInput(this));
    }


}
