package com.dreamstreet.arpg;

import java.awt.*;
import java.awt.image.BufferStrategy;

import javax.swing.*;

import com.dreamstreet.arpg.gfx.*;
import com.dreamstreet.arpg.gfx.particle.ParticleEmitter;
import com.dreamstreet.arpg.input.NPCInput;
import com.dreamstreet.arpg.input.PlayerInput;
import com.dreamstreet.arpg.input.WindowInput;
import com.dreamstreet.arpg.item.Fireball;
import com.dreamstreet.arpg.item.Item;
import com.dreamstreet.arpg.obj.Coin;
import com.dreamstreet.arpg.obj.Entity;
import com.dreamstreet.arpg.obj.HealthGlobe;
import com.dreamstreet.arpg.screen.GameScreen;
import com.dreamstreet.arpg.screen.MainMenuScreen;
import com.dreamstreet.arpg.screen.Screen;
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
    public static boolean showhealth;

    public static boolean pause = false;

    int fps = 0;

    private ArrayList<Screen> screens;
    public int currentscreen;

    public Game() {
        screens = new ArrayList<>();
        screens.add(new MainMenuScreen(this));
        screens.add(new GameScreen(this));


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
			boolean shouldRender = true; // false here limits to 60 fps

			while(delta>=1){
				ticks++;
                tick();
				delta--;
				shouldRender = true;
			}

			try{
				Thread.sleep(1);
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
        screens.get(0).tick();
    }

	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(2);
			return;
		}
        Graphics2D g = (Graphics2D)bs.getDrawGraphics();

        screens.get(0).render(g);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.setColor(Color.WHITE);
        g.drawString(fps + " ", 20, 40);

        if (debug) {
            drawDebug(g);
        }

        g.dispose();
		bs.show();

	}


	public void drawDebug(Graphics g) {
        g.drawLine(WIDTH / 12 * 5 * SCALE, HEIGHT / 2 * SCALE, WIDTH / 12 * 7 * SCALE, HEIGHT / 2 * SCALE);
        g.drawLine(WIDTH/2*SCALE,HEIGHT/12*5*SCALE,WIDTH/2*SCALE,HEIGHT/12*7*SCALE);
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


    public enum DebugLevel {
        INFO, WARNING, SEVERE
    }


}
