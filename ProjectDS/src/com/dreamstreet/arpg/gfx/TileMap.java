package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.obj.Entity;
import com.dreamstreet.arpg.ui.DayCycle;
import com.dreamstreet.arpg.util.Util;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

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
                          /*  if (id == 3) {
                                walkable = false;
                            }*/
                            if (id == 1)
                            {
                                tiles[rows][columns] = new Tile(columns, rows, 0, 32,32,(int)(Math.random()*5)+1, walkable);
                            }
                        }else if(id >= 100){
                            tiles[rows][columns] = new Tile(columns, rows, -15, 32,32,1, true);
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


	public void draw(Graphics2D g, Camera camera, Entity charx, DayCycle cycle){
        Vector3 offset = camera.getOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double zOffset = offset.z;
        double scale = camera.getScale();

        Vector2 vvv = new Vector2(charx.getX(),charx.getY());
        Tile player = getTile(vvv.x, vvv.y);

        int startX = (int)xOffset / 32;
        if (startX < 0) startX = 0;

        int startY = (int)(yOffset + zOffset) / 32;
        if (startY < 0) startY = 0;

        int endX = (int)(xOffset + camera.getWidth()) / 32 + 2;
        if (endX > tiles[0].length) endX = tiles[0].length;

        int endY = (int) (yOffset + zOffset + camera.getHeight()) / 32 + 2;
        if (endY > tiles.length) endY = tiles.length;


/*  old
   for(int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {*/
         for(int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                if (tiles[y][x] != null) {
                    Vector3 curr = new Vector3(x * 32, y * 32, tiles[y][x].z);
                    g.drawImage(SpriteLoader.getTile(tiles[y][x].id),(int)((curr.x-xOffset)*scale),(int)((curr.y-yOffset-zOffset)*scale),(int)(tiles[y][x].width*scale + 1),(int)(tiles[y][x].height*scale + 1),null);
                    float opacity = 1.0f;
                  //  if (player != null) {
                        double distance = Util.findDistance(x - player.x, y - player.y);
                        opacity = (float)(distance/cycle.getLightDist());
                        if (opacity > cycle.getMaxDarkness()) {
                            opacity = cycle.getMaxDarkness();
                        }
                  //  }
                    if (opacity != 0) {
                        //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                        //clean up shadow picking system?
                        if (tiles[y][x].id != 3) {
                        //    g.drawImage(Textures.getTile(-1), (int) ((curr.x - xOffset) * scale), (int) ((curr.y - yOffset) * scale), (int) (tiles[y][x].width * scale + 1), (int) (tiles[y][x].height * scale + 1), null);
                        }else{
                        //    g.drawImage(Textures.getTile(-2), (int) ((curr.x - xOffset) * scale), (int) ((curr.y - yOffset) * scale), (int) (tiles[y][x].width * scale + 1), (int) (tiles[y][x].height * scale + 1), null);
                        }
                        //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

                    }

                    //g.setColor(Color.white);
                    //g.drawRect((int)((curr.x-xOffset)*scale),(int)((curr.y-yOffset)*scale), 3,3);
                    //   g.setColor(Color.RED);
                }
            }
		}
	}


    //0.033283424259455074, 3.195376574578727             :             -1.0650695763025624, 102.25205038651926
    //10.001505482530465, -0.0038539967582997825             :             320.0481754409749, -0.12332789626559304

    public static Tile getTile(double x, double y) {
        double tilex = x / 32;
        double tiley = y / 32;

       // System.out.println(tilex + ", " + tiley + "             :             " + x + ", " + y);

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
