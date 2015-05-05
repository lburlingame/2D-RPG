package com.dreamstreet.arpg;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.dreamstreet.arpg.gfx.*;
import com.dreamstreet.arpg.input.InputComponent;
import com.dreamstreet.arpg.input.NPCInput;
import com.dreamstreet.arpg.input.NullInput;
import com.dreamstreet.arpg.input.PlayerInput;
import com.dreamstreet.arpg.sfx.AudioPlayer;
import com.dreamstreet.arpg.ui.DayCycle;
import com.dreamstreet.arpg.ui.MessageBox;
import com.dreamstreet.arpg.ui.UI;
import java.util.Arrays;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	//public static final int WIDTH = 640;  // 1920x1080
	public static final int WIDTH = 480;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE = 3;
    public static final Dimension dimension = new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
	public static final String NAME = "00";
    public JFrame frame;
    private Thread thread;

	private boolean running = false;
    public boolean debug = false;

	public int tickCount = 0;

	//map
	private TileMap map = new TileMap("res/levels/isotest3_map.txt");

    private Camera camera = new Camera(null);

    int fps = 0;


    //spritesheets for map and character
    public static SpriteSheet spritesheet = new SpriteSheet("/sprites/bear_sheet.png");

    private SpriteSheet skulltulasheet = new SpriteSheet("/sprites/skulltula_sprite.png");
    private SpriteSheet kodamasheet = new SpriteSheet("/sprites/kodama_sprite.png");
    private SpriteSheet skeletonsheet = new SpriteSheet("/sprites/skeleton_sprite.png");
    private SpriteSheet nofacesheet = new SpriteSheet("/sprites/noface_sprite.png");


    //character image and sprite

    private BufferedImage spritechar = spritesheet.getSprite(0,0,32,32);
    private BufferedImage skulltulachar = skulltulasheet.getSprite(0,0,32,32);
    private BufferedImage kodamachar = kodamasheet.getSprite(0,0,32,32);
    private BufferedImage skeletonchar = skeletonsheet.getSprite(0,0,32,32);
    private BufferedImage nofacechar = nofacesheet.getSprite(0,0,32,32);

    private Sprite character = new Sprite(spritechar, new PlayerInput(this, camera), 1.0, new Vector3(0,0,0));
    private Sprite skulltula = new Sprite(spritechar, new NPCInput(this), 2.0, new Vector3(110,140,0));
    private Sprite character1 = new Sprite(spritechar, new NPCInput(this), 1.0, new Vector3(50,50,0));
    private Sprite character2 = new Sprite(spritechar, new NPCInput(this), 1.0, new Vector3(180,20,0));
    private Sprite character3 = new Sprite(spritechar, new NPCInput(this), 1.0, new Vector3(20,180,0));

    private Sprite[] chars = new Sprite[1];

    private UI ui = new UI();
    private DayCycle dayCycle = new DayCycle(dimension.width - 128, 128, 48);

    public AudioPlayer music = new AudioPlayer("res/audio/d2cave.wav");
    public boolean audioPlay = false;
    private int curr = 0;

    private MessageBox box1 = new MessageBox("This is a test of the message box system. Hopefully I can get this shit to work asap!");
    public Game() {
        chars[0] = character;
     //   chars[1] = skulltula;
      //  chars[2] = character1;
       // chars[3] = character2;
      //  chars[4] = character3;

        //  System.setProperty("sun.java2d.opengl","True");
        camera.setTarget(character);
        music.stop();
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

		/*	try{
				Thread.sleep(4);
			}catch(InterruptedException e){
				e.printStackTrace();
			}*/

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


	public void tick(){
        for (int i = 0; i < chars.length; i++) {
            chars[i].tick();
        }


        camera.tick();

        map.tick();

        /*
        kodama.tick();
        kodama1.tick();
        kodama2.tick();
        kodama3.tick();
        noface.tick();*/

        ui.tick();
        dayCycle.tick();

		//for (int i = 0 ; i < rays.length; i++) {
		//	rays[i].obstacle.x -= dx;
	//		rays[i].obstacle.y -= dy;
		//	rays[i].tick(character.getX()+50,character.getY()+25);
	//	}
	}

	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs==null){
			createBufferStrategy(3);
			return;
		}
        Graphics2D g = (Graphics2D)bs.getDrawGraphics();

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0,0,WIDTH*SCALE+100,HEIGHT*SCALE+100);
		map.draw(g,camera,character, dayCycle);

        Arrays.sort(chars);

        for (int i = 0; i < chars.length; i++) {
            chars[i].draw(g, camera);
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
	//	for (int i = 0; i < rays.length; i++) {
	//		rays[i].draw(g);
	//	}
        if (debug) {
            drawDebug(g);
        }

        box1.draw(g);
		g.dispose();
		bs.show();
	}


	public void drawDebug(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.setColor(Color.WHITE);
        Vector2 curr = IsoCalculator.twoDToIso(new Vector3(character.getX(), character.getY(), 0));

        g.drawString(fps + " ", 20, 40);


        g.drawString(character.getX() + character.feet.x + ", " + (character.getY() + character.feet.y), 20, 70);
        g.drawString(TileMap.currentx + ", " + TileMap.currenty, 20, 100);

        /*
        g.drawString(character.getDest_x() + ", " + character.getDest_y(), 20, 100);
        g.drawString(curr.x + ", " + curr.y, 20, 130);*/
        g.drawString(dayCycle.time, Game.WIDTH * Game.SCALE - 100, 40);

       // g.drawString(camera.getScale() + " ", 20, 160);
       // g.drawLine(0,HEIGHT/2*SCALE,WIDTH*SCALE, HEIGHT/2*SCALE);
        //g.drawLine(WIDTH/2*SCALE,0,WIDTH/2*SCALE,HEIGHT*SCALE);
        chars[this.curr].drawDebug(g, camera);


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
        curr = curr % chars.length;
        System.out.println(curr);
        camera.setTarget(chars[curr]);

        chars[curr].setInput(chars[prev].getInput());
        chars[prev].setInput(new NPCInput(this));
    }
}
