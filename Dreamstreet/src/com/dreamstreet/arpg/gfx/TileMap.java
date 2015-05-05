package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;
import com.dreamstreet.arpg.ui.DayCycle;

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


    public static final double GRAVITY = .3;


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
                        }else if(id < 100){
                            boolean walkable = true;
                            if (id == 3) {
                                walkable = false;
                            }
                            tiles[rows][columns] = new Tile(columns, rows, 0, 64,47,id, walkable);
                        }else if(id >= 100){
                            tiles[rows][columns] = new Tile(columns, rows, -15, 64,47,1, true);
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

    }


	public void draw(Graphics2D g, Camera camera,Sprite charx, DayCycle cycle){
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        Vector2 vvv = new Vector2(charx.getX() + charx.feet.x,charx.getY() + charx.feet.y);
        Tile player = getTile(vvv.x, vvv.y);

        for(int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] != null) {
                    Vector2 curr = IsoCalculator.twoDToIso(new Vector3(x * 32 - 16, y * 32 + 16, tiles[y][x].z));
                    g.drawImage(Textures.getTile(tiles[y][x].id),(int)((curr.x-xOffset)*scale),(int)((curr.y-yOffset)*scale),(int)(tiles[y][x].width*scale + 1),(int)(tiles[y][x].height*scale + 1),null);
                    float opacity = 1.0f;
                  //  if (player != null) {
                        double distance = Util.findDistance(x - player.x, y - player.y);
                        opacity = (float)(distance/cycle.getLightDist());
                        if (opacity > cycle.getMaxDarkness()) {
                            opacity = cycle.getMaxDarkness();
                        }
                  //  }
                    if (opacity != 0) {
                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                        //clean up shadow picking system?
                        if (tiles[y][x].id != 3) {
                            g.drawImage(Textures.getTile(-1), (int) ((curr.x - xOffset) * scale), (int) ((curr.y - yOffset) * scale), (int) (tiles[y][x].width * scale + 1), (int) (tiles[y][x].height * scale + 1), null);
                        }else{
                            g.drawImage(Textures.getTile(-2), (int) ((curr.x - xOffset) * scale), (int) ((curr.y - yOffset) * scale), (int) (tiles[y][x].width * scale + 1), (int) (tiles[y][x].height * scale + 1), null);
                        }
                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

                    }

                    //g.setColor(Color.white);
                    //g.drawRect((int)((curr.x-xOffset)*scale),(int)((curr.y-yOffset)*scale), 3,3);
                    //   g.setColor(Color.RED);
                }
            }
		}
	}


    //0.033283424259455074, 3.195376574578727             :             -1.0650695763025624, 102.25205038651926
    public static Tile getTile(double x, double y) {
        double tilex = x / 32;
        double tiley = y / 32;

        System.out.println(tilex + ", " + tiley + "             :             " + x + ", " + y);

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

    public static Tile[][] getTiles() {
        return tiles;
    }
}












/*

  flicker_timer++;
        if (flicker_timer == flicker_duration) {
            flicker_timer = 0;
            flicker_dist = Math.random()*1.5+8.5;
            flicker_duration = (int)(Math.random()*6) + 8;
        }
 */
