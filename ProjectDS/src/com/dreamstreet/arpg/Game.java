package com.dreamstreet.arpg;

import java.awt.*;
import java.awt.image.BufferStrategy;

import javax.swing.*;

import com.dreamstreet.arpg.input.InputHandler;
import com.dreamstreet.arpg.screen.GameScreen;
import com.dreamstreet.arpg.screen.MainMenuScreen;
import com.dreamstreet.arpg.screen.SplashScreen;
import com.dreamstreet.arpg.screen.Screen;

import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640;  // 1920x1080
	//public static final int WIDTH = 480; // 480
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE = 3;
    public static final Dimension dimension = new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
	public static final String NAME = "Dream Street";
    public static final String TICKS = "00";

    public JFrame frame;
    private Thread thread;

	private boolean running = false;
    public static boolean debug = true;
    public static boolean unlimit = true;

    int fps = 0;

    private ArrayList<Screen> screens;
    private SplashScreen ss;
    private MainMenuScreen mms;
    private GameScreen gs;

    public int currentscreen = 0;

    private InputHandler input;

    public Game() {
        input = new InputHandler(this);
        screens = new ArrayList<>();
        ss = new SplashScreen(this);
        screens.add(ss);
        mms = new MainMenuScreen(this);
        screens.add(mms);
        gs = new GameScreen(this);
        screens.add(gs);
    }


    public void playGame() {
        if (gs == null) {
            gs = new GameScreen(this);
            screens.add(gs);
        }
        currentscreen = screens.indexOf(gs);
    }

    // need way to dispose of screen
    public void newGame() {
        if (gs != null) {
            screens.remove(gs);
            gs = null;
        }
        gs = new GameScreen(this);
        screens.add(gs);
        currentscreen = screens.indexOf(gs);
    }

    public void openMenu() {
        currentscreen = screens.indexOf(mms);
    }

	@Override
	public void run() {
		//limit fps approx. 60
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int ticks = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime)/nsPerTick;
			lastTime = now;
			boolean shouldRender = unlimit; // false here limits to 60 fps

			while(delta >= 1){
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

			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				fps = frames;
				frames = 0;
                frame.setTitle("" + ticks );
				ticks = 0;
			}
		}

	}


	public void tick() {
        input.tick();
        screens.get(currentscreen).tick();
        input.setPrevious();
    }

	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
        Graphics2D g = (Graphics2D)bs.getDrawGraphics();

        screens.get(currentscreen).render(g);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString(fps + " ", 20, 40);

        if (debug) {
            drawDebug(g);
        }

        g.dispose();
		bs.show();

	}


	public void drawDebug(Graphics g) {
      //  g.drawLine(WIDTH / 12 * 5 * SCALE, HEIGHT / 2 * SCALE, WIDTH / 12 * 7 * SCALE, HEIGHT / 2 * SCALE);
        //g.drawLine(WIDTH / 2 * SCALE, HEIGHT / 12 * 5 * SCALE, WIDTH / 2 * SCALE, HEIGHT / 12 * 7 * SCALE);

        //g.drawString(input.M1.isPressed() + " ", 20, 200);
       // g.drawString(input.M1.getPrevious() + " ", 20, 220);

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


    public InputHandler getInput() {
        return input;
    }


    public enum DebugLevel {
        INFO, WARNING, SEVERE
    }


}
