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
                            tiles[rows][columns] = new Tile(88,44,1, true);
                        }else if(id == 2){
                            tiles[rows][columns] = new Tile(88,44,2, true);
                        }else if(id == 3){
                            tiles[rows][columns] = new Tile(88,44,3, true);
                        }else if(id == 4){
                            tiles[rows][columns] = new Tile(88,44,4, true);
                        }else if(id == 5){
                            tiles[rows][columns] = new Tile(88,44,5, true);
                        }else if(id == 6){
                            tiles[rows][columns] = new Tile(64,47,6, true);
                        }else if(id > 99){
                            tiles[rows][columns] = new Tile(88,44,100, true);
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




	public void draw(Graphics g, Camera camera){
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        for(int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] != null) {
                    Vector2 curr = IsoCalculator.twoDToIso(new Vector2(x * 32 - 16, y * 32 + 16));
                    g.drawImage(Textures.getTile(tiles[y][x].id),(int)((curr.x-xOffset)*scale),(int)((curr.y-yOffset)*scale),(int)(tiles[y][x].width*scale),(int)(tiles[y][x].height*scale),null);
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

       // System.out.println(x / 44  + ", " + y /44);

        if (tilex < 0 || tilex >= columns) {
            currentx = -1;
            return null;
        }else if (tiley <  0 || tiley >= rows) {
            currenty = -1;
            return null;
        }

        currentx = (int)tilex;
        currenty = (int)tiley;

     //   System.out.println(currentx + ", " + currenty);
      // return tiles[0][0];

        return tiles[(int)tiley][(int)tilex];
    }


}
