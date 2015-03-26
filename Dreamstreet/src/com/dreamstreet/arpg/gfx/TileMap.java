package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TileMap {

	private ArrayList<Tile> tiles;
	private int mapSize;
	//Tile images

	public TileMap(String path, SpriteSheet map){
		tiles = new ArrayList();
		loadMap(path);
	}
	
	private void loadMap(String path){
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			String curLine;
			
			int row = 0;
			while((curLine = br.readLine())!=null){
				if(curLine.isEmpty())
					continue;
				
				String[] values = curLine.trim().split(" ");
				int col = 0;
				for(String val : values){
					if(!val.isEmpty()){
						int id = Integer.parseInt(val);
                        if(id == 1){
                            tiles.add(new Tile(col*32,row*32,32,32,1, true));
                        }else if(id == 2){
                            tiles.add(new Tile(col*32,row*32,32,32,2, true));
                        }else if(id == 3){
                            tiles.add(new Tile(col*32,row*32,32,32,3, false));
                        }else if(id == 4){
                            tiles.add(new Tile(col*32,row*32,32,32,4, true));
                        }else if(id == 5){
                            tiles.add(new Tile(col * 32, row * 32, 32, 32, 5, false));
                        }else if(id > 99){
                            tiles.add(new Tile(col * 32, row * 32, 32, 32, 100, true));
                        }
						col++;
					}
				}
				mapSize = col;
				row++;
			}
			mapSize *= row;
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void draw(Graphics g, Camera camera){
        double xOffset = camera.getXOffset();
        double yOffset = camera.getYOffset();
        double scale = camera.getScale();

        for(int i =0;i<mapSize;i++) {
			// only draws the tile if its in the bounds of the screen
			if ((tiles.get(i).x-xOffset)*scale > (0 - tiles.get(i).height*scale * 1.1)  &&  (tiles.get(i).x-xOffset)*scale < Game.WIDTH*Game.SCALE * 1.1
			&& (tiles.get(i).y-yOffset)*scale > (0 - tiles.get(i).width * scale * 1.1) && (tiles.get(i).y-yOffset)*scale <  (Game.HEIGHT * Game.SCALE * 1.1))
			{
				g.drawImage(Textures.getTile(tiles.get(i).id),(int)((tiles.get(i).x-xOffset)*scale),(int)((tiles.get(i).y-yOffset)*scale),(int)(tiles.get(i).height*scale)+1,(int)(tiles.get(i).width*scale)+1,null);
			}

		}
	}


}
