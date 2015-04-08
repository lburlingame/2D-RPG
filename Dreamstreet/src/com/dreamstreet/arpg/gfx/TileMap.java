package com.dreamstreet.arpg.gfx;

import com.dreamstreet.arpg.Game;

import javax.print.attribute.standard.MediaSize;
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
                        }else if(id == 6){
                            tiles.add(new Tile(col * 44, row * 44, 88, 44, 6, false));
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
        Vector2 offset = camera.getIsoOffset();
        double xOffset = offset.x;
        double yOffset = offset.y;
        double scale = camera.getScale();

        for(int i =0;i<mapSize;i++) {
            Vector2 curr = IsoCalculator.twoDToIso(new Vector2(tiles.get(i).x, tiles.get(i).y));
            g.drawImage(Textures.getTile(tiles.get(i).id),(int)((curr.x-xOffset)*scale),(int)((curr.y-yOffset)*scale),(int)(tiles.get(i).width*scale),(int)(tiles.get(i).height*scale),null);
		}
	}


}
