package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;

import javax.print.attribute.standard.MediaSize;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TileMap {


    //private ArrayList<Tile> tiles;

    private static Tile[][] tiles;
	private static int columns;
    private static int rows;
    public static int currentx = 0;
    public static int currenty = 0;


    private int flicker_timer = 0;
    private int flicker_duration = 16;
    private double flicker_dist = 10;

    private final float DAY_CYCLE = 3600.0f;
    private final float HOUR = DAY_CYCLE/24;
    private final float MINUTE = HOUR / 60;
    private int current_time = (int)(HOUR * 15);
    private float max_darkness = 1f;


	public TileMap(String path){
		//tiles = new ArrayList();
		loadMap(path);
	}
	
	private void loadMap(String path){
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			String curLine;

			while((curLine = br.readLine())!=null) {
                if (curLine.isEmpty())
                    continue;
                columns = 0;
                String[] values = curLine.trim().split(" ");
                for (String val : values) {
                    if (!val.isEmpty()) {
                        columns++;
                    }
                }
                rows++;
            }

            tiles = new Tile[rows][columns];
            br = new BufferedReader(new FileReader(path));

            rows = 0;
            while((curLine = br.readLine())!=null) {
                if (curLine.isEmpty())
                    continue;

                columns = 0;
                String[] values = curLine.trim().split(" ");
                for (String val : values) {
                    if (!val.isEmpty()) {
                        int id = Integer.parseInt(val);
                        if (id == 0) {
                            tiles[rows][columns] = null;
                        }else if(id == 1){
                            tiles[rows][columns] = new Tile(columns, rows, 64,47,1, true);
                        }else if(id == 2){
                            tiles[rows][columns] = new Tile(columns, rows, 88,44,2, true);
                        }else if(id == 3){
                            tiles[rows][columns] = new Tile(columns, rows, 88,44,3, true);
                        }else if(id == 4){
                            tiles[rows][columns] = new Tile(columns, rows, 88,44,4, true);
                        }else if(id == 5){
                            tiles[rows][columns] = new Tile(columns, rows, 88,44,5, true);
                        }else if(id == 6){
                            tiles[rows][columns] = new Tile(columns, rows, 64,47,6, true);
                        }else if(id == 7){
                            tiles[rows][columns] = new Tile(columns, rows, 64,47,7, true);
                        }else if(id == 8){
                            tiles[rows][columns] = new Tile(columns, rows, 64,47,8, true);
                        }else if(id > 99){
                            tiles[rows][columns] = new Tile(columns, rows, 88,44,100, true);
                        }
                        columns++;
                    }
                }
                rows++;
            }
            System.out.println(columns + ", " + rows + " OK?>");

			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


    public void tick() {
        flicker_timer++;
        if (flicker_timer == flicker_duration) {
            flicker_timer = 0;
            flicker_dist = Math.random()*1.5+8.5;
            flicker_duration = (int)(Math.random()*6) + 8;
        }

        max_darkness = 0;
        current_time++;
        max_darkness = (float)((Math.cos(current_time/DAY_CYCLE * 3.14 * 2)));
        if (max_darkness < 0) {
            max_darkness = 0;
        }
        if (current_time == DAY_CYCLE) {
            current_time = 0;
            System.out.println("RESET");
        }
        System.out.println((int)(current_time/HOUR) + ":" + (int)((current_time % HOUR) / MINUTE) + " o'clock");
    }


	public void draw(Graphics2D g, Camera camera,Sprite charx){
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        Vector2 vvv = new Vector2(charx.getX() + charx.feet.x,charx.getY() + charx.feet.y);
        Tile player = getTile(vvv.x, vvv.y);

        for(int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] != null) {
                    Vector2 curr = IsoCalculator.twoDToIso(new Vector2(x * 32 - 16, y * 32 + 16));
                    g.drawImage(Textures.getTile(tiles[y][x].id),(int)((curr.x-xOffset)*scale),(int)((curr.y-yOffset)*scale),(int)(tiles[y][x].width*scale + 1),(int)(tiles[y][x].height*scale + 1),null);
                    float opacity = 1.0f;
                    if (player != null) {
                        double distance = Sprite.findDistance(x - player.x, y - player.y);
                        opacity = (float)(distance/flicker_dist);
                        if (opacity > max_darkness) {
                            opacity = max_darkness;
                        }
                    }
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                    g.drawImage(Textures.getTile(-1), (int) ((curr.x - xOffset) * scale), (int) ((curr.y - yOffset) * scale), (int) (tiles[y][x].width * scale + 1), (int) (tiles[y][x].height * scale + 1), null);
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

                    g.setColor(Color.white);
                    //g.drawRect((int)((curr.x-xOffset)*scale),(int)((curr.y-yOffset)*scale), 3,3);
                    //   g.setColor(Color.RED);
                }
            }
		}
	}

    public static Tile getTile(double x, double y) {
        double tilex = x / 32 ;
        double tiley = y / 32;

        if (tilex < 0 || tilex >= columns) {
            currentx = -1;
            return null;
        }else if (tiley <  0 || tiley >= rows) {
            currenty = -1;
            return null;
        }
        currentx = (int)tilex;
        currenty = (int)tiley;

        return tiles[(int)tiley][(int)tilex];
    }


}
